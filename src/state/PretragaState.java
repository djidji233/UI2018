package state;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javafx.scene.layout.Border;
import model.Attribute;
import model.Entity;
import model.Entry;
import model.TableModel;
import model.file.IUDBFile;
import model.file.SEKFile;
import model.file.SERFile;
import view.ChangeRowPanel;

import view.LinearnaPretragaPanel;
import view.MainFrame;
import view.MainTab;
import view.MyJPanel;
import view.MySQLSearch;
import view.SQLCountPanel;
import view.SQLSearchPanel;
import view.SearchTreePanel;
import view.Tab;

public class PretragaState implements IState {

	public PretragaState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JPanel bottomPanel() {
		MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());

		if (tab != null) {
			Entity entity = tab.getEntity();
			// ===============SerLinearSearch=====================================================================================
			if (entity.getType().equalsIgnoreCase("ser")) {
				List<MyJPanel> myPanels = new ArrayList<>();
				JPanel jPanel = new JPanel();
				JPanel jp = new JPanel(new GridLayout(entity.getAttributes().size() - 1, 4));
				jp.setMaximumSize(new Dimension(200, 200));
				jPanel.setLayout(new GridLayout(2, 1, 10, 10));
				for (int i = 0; i < entity.getAttributes().size(); i++) {
					MyJPanel myjp = new MyJPanel(entity.getAttributes().get(i));
					myPanels.add(myjp);
					jp.add(myjp);
				}
				JButton buttonSearch = new JButton("Search");
				jp.add(buttonSearch);
				jPanel.add(new JScrollPane(jp), BorderLayout.NORTH);

				buttonSearch.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> lines = new ArrayList<>();
						for (int i = 0; i < myPanels.size(); i++) {
							lines.add(myPanels.get(i).getJtf().getText());
						}
						SERFile sf = (SERFile) entity;
						ArrayList<String> found = sf.findSer(lines);
						System.out.println(found);
						String[][] data = new String[sf.getBLOCK_SIZE()][entity.getAttributes().size()];
						for (int i = 0; i < found.size(); i++) {
							String line = found.get(i);
							int k = 0;
							String field = null;
							for (int j = 0; j < entity.getAttributes().size(); j++) {
								field = line.substring(k, k + entity.getAttributes().get(j).getLength());
								data[i][j] = field;
								k = k + entity.getAttributes().get(j).getLength();
							}
						}
						JTable tabela = new JTable(new TableModel(entity.getAttributes(), data));
						tabela.setPreferredScrollableViewportSize(new Dimension(150, 100));
						if (1 < jPanel.getComponentCount())
							jPanel.remove(jPanel.getComponentCount() - 1);
						jPanel.add(new JScrollPane(tabela), BorderLayout.PAGE_END);
						jPanel.revalidate();
					}
				});
				return jPanel;
				// ==============================================================================================================
			}
			if (entity.getType().equalsIgnoreCase("sek")) { // SEK
				JPanel primaryPanel = new JPanel(new GridLayout(2, 1, 5, 5)); // ovo neka je glavni panel koji vracamo

				// ============BinarySearch========================================================================================
				JPanel jPanelBinary = new JPanel(new GridLayout(4, 4));
				JButton buttonBinarno = new JButton("Binarno Pretrazivanje");
				JLabel binaryLabel = new JLabel("Binary Search Result");
				List<MyJPanel> myPanels = new ArrayList<>();
				for (int i = 0; i < entity.getAttributes().size(); i++) {
					if (entity.getAttributes().get(i).isPrimaryKey()) {
						MyJPanel mjp = new MyJPanel(entity.getAttributes().get(i));
						myPanels.add(mjp);
						jPanelBinary.add(mjp);
					}
				}

				buttonBinarno.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SEKFile sf = (SEKFile) entity;
						ArrayList<String> strings = new ArrayList<>();
						for (int i = 0; i < myPanels.size(); i++) {
							strings.add(myPanels.get(i).getJtf().getText());
						}
						int n = 0;
						String res = "";
						for (int j = 0; j < entity.getAttributes().size(); j++) {
							if (entity.getAttributes().get(j).isPrimaryKey()) {
								String s = strings.get(j);
								for (int i = strings.get(j).length(); i < entity.getAttributes().get(j)
										.getLength(); i++) {
									s = s + " ";
								}
								res = res + s;
							}
						}
						try {
							String result = sf.binarySearch(res);
							if (result == null) {
								binaryLabel.setText("Can't find element");
								jPanelBinary.revalidate();
							} else {
								binaryLabel.setText(result);
								jPanelBinary.revalidate();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				jPanelBinary.add(buttonBinarno);
				jPanelBinary.add(binaryLabel);
				// ===== Linear Search
				// =======================================================================================

				JPanel jPanelLinear = new JPanel();
				jPanelLinear.add(new LinearnaPretragaPanel(entity));
				// ==============================================================================================================

				jPanelBinary.add(buttonBinarno);
				jPanelBinary.add(binaryLabel);

				// JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jPanelBinary,
				// jPanelLinear);
				primaryPanel.add(new JScrollPane(jPanelBinary));
				primaryPanel.add(new JScrollPane(jPanelLinear));
				// primaryPanel.add(sp);
				// panel.add(primaryPanel);
				// panel.revalidate();
				// panel.repaint();
				return primaryPanel;
			} else if (entity.getType().equalsIgnoreCase("ind")) {

				Entry slog = new Entry(tab.getEntity());

				SearchTreePanel searchPanel = new SearchTreePanel(slog);
				JPanel bpanel = new JPanel(new BorderLayout());
				bpanel.add(searchPanel, BorderLayout.CENTER);

				JButton btnSQLSearch = new JButton("SQLSearch");
				btnSQLSearch.setPreferredSize(new Dimension(30, 30));
				btnSQLSearch.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						MySQLSearch msqls = new MySQLSearch(tab);
					}
				});
				bpanel.add(btnSQLSearch, BorderLayout.SOUTH);

				return bpanel;

			} else if (entity instanceof IUDBFile) {

				JPanel sqlPanel = new JPanel();
				sqlPanel.setLayout(new GridLayout(1, 2, 5, 5));
				sqlPanel.add(new SQLCountPanel(entity));
				sqlPanel.add(new SQLSearchPanel(entity));

				sqlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

				return sqlPanel;

			}
		}
		return null;
	}

}
