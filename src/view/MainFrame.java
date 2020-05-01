package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import actions.ActionManager;
import model.MainFrameModel;
import model.NodeTreeModel;
import model.Workspace;
import state.StateManager;

public class MainFrame extends JFrame {

	private static MainFrame instance = null;
	private NodeTree nodeTree;
	private ActionManager actionManager;
	private MainFrameModel model;
	private DesnoGore panelTop;
	private JPanel panelBot;

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
			instance.initialise();
		}
		return instance;
	}

	private void initialise() {
		actionManager = new ActionManager();
		model = new MainFrameModel();
		initialiseGui();
	}

	public MainFrameModel getModel() {
		return model;
	}

	private void initialiseGui() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		setSize(1200, 900);
		setTitle("InfView");
		ImageIcon img = new ImageIcon("images/InfViewLogo3.png");
		setIconImage(img.getImage());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		MyMenuBar myMenuBar = new MyMenuBar();
		this.setJMenuBar(myMenuBar);

		MyToolbar toolbar = new MyToolbar();
		this.add(toolbar, BorderLayout.NORTH);

		nodeTree = new NodeTree();
		Workspace workspace = new Workspace("Workspace");
		NodeTreeModel nodeTreeModel = new NodeTreeModel(workspace);
		nodeTree.setModel(nodeTreeModel);

		JPanel panelTree = new JPanel(new BorderLayout());
		panelTree.setMinimumSize(new Dimension(200, 100));
		panelTree.add(nodeTree, BorderLayout.CENTER);
		JScrollPane scrollpTree = new JScrollPane(nodeTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTree.add(scrollpTree, BorderLayout.CENTER);

		panelTop = new DesnoGore();

		panelBot = new JPanel(new BorderLayout());
		// panelBot = new DesnoDole();

		JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTop, panelBot);
		splitPane1.setDividerLocation(320);
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelTree, splitPane1);

		this.add(splitPane2, BorderLayout.CENTER);
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public NodeTree getNodeTree() {
		return nodeTree;
	}

	public DesnoGore getPanelTop() {
		return panelTop;
	}

	public JPanel getPanelBot() {
		return panelBot;
	}

	public void setPanelBot(JPanel panelBot) {
		this.panelBot = panelBot;
	}
	
}
