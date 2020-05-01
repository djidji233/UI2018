package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreeNode;

import model.Database;
import model.Entity;
import model.NodeMeta;
import model.Relation;
import model.Workspace;

public class DesnoDole extends JPanel {
	private JTabbedPane tabPane;
	//private List<Relation> relacije = new ArrayList<>();

	public DesnoDole() {
		setLayout(new BorderLayout());
		tabPane = new JTabbedPane();
		add(tabPane, BorderLayout.CENTER);
	}
//
//	public void dodajTabove(Entity selektovani, Node node) {
//		tabPane.removeAll();
//		//relacije.clear();
//		// Database db = MainFrame.getInstance().getModel().getActiveDatabase();
//		Node parent = (Node) node.getParent().getParent();
//		
//		if (parent.getData() instanceof Database) {
//			Database db = (Database) parent.getData();
//			relacije.addAll(db.getRelations());
//		} else {
//			System.out.println("Nisam uhvatio Database");
//		}
//
//		for (int i = 0; i < relacije.size(); i++) {
//			if (selektovani.equals((relacije.get(i)).getSourceEntity())) {
//				Entity tmp = relacije.get(i).getTargetEntity();
//				Tab t = new Tab(tmp);
//				tabPane.addTab(tmp.getName(), t);
//			} else if (selektovani.equals((relacije.get(i)).getTargetEntity())) {
//				Entity tmp = relacije.get(i).getSourceEntity();
//				Tab t = new Tab(tmp);
//				tabPane.addTab(tmp.getName(), t);
//			}
//		}
//	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}
}
