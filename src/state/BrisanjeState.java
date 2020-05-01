package state;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Entity;
import model.Entry;
import model.TableModel;
import model.file.AbstractFile;
import model.file.SERFile;
import view.ChangeRowPanel;
import view.DeleteRowPanel;
import view.MainFrame;
import view.MainTab;
import view.MyJPanel;
import view.Tab;

public class BrisanjeState implements IState {

	public BrisanjeState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JPanel bottomPanel() {
		MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());
		// JPanel panel = MainFrame.getInstance().getPanelBot();
		// panel.removeAll();
		if (tab != null) {
			Entity entity = tab.getEntity();
			if (entity.getType().equalsIgnoreCase("ser")) {
				JPanel jPanel = new JPanel();
				JPanel jp = new JPanel(new GridLayout(0, 1));
				JButton buttonDelete = new JButton("Delete Selected");
				buttonDelete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int[] position = tab.getTabela().getSelectedRows();
						TableModel tbm = (TableModel) tab.getTabela().getModel();
						String[] line;
						for (int j = 0; j < position.length; j++) {
							ArrayList<String> delete = new ArrayList<>();
							line = tbm.getRowValue(position[j]);
							for (int i = 0; i < line.length; i++) {
								delete.add(line[i]);
							}
							try {
								((AbstractFile)entity).deleteRecord(delete);
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (ClassNotFoundException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
						}
						tab.refreshTable();
					}
				});
				JButton buttonRecycleBin = new JButton("Recycle Bin");
				buttonRecycleBin.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SERFile sf = (SERFile) entity;
						ArrayList<String> deleted = sf.recycleBin();
						System.out.println("deleted:");
						System.out.println(deleted);
						List<MyJPanel> panels = new ArrayList<>();
						JFrame jframe = new JFrame("Recycle Bin");
						jframe.setSize(400, 400);
						jframe.setLocationRelativeTo(null);
						JPanel jpanel = new JPanel(new GridLayout(0, 1));
						JScrollPane scrollpTree = new JScrollPane(jpanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						JButton recylce = new JButton("Bring them back");
						if (deleted.size() == 0) {
							JOptionPane.showMessageDialog(null, "No elements deleted.", "Notification",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							MyJPanel newPanel;
							for (int i = 0; i < deleted.size(); i++) {
								newPanel = new MyJPanel(deleted.get(i));
								panels.add(newPanel);
								jpanel.add(newPanel);
							}
							jframe.add(scrollpTree, BorderLayout.CENTER);
							jframe.add(recylce, BorderLayout.SOUTH);
						}
						recylce.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								for (int i = 0; i < panels.size(); i++) {
									MyJPanel panel = panels.get(i);
									if (panel.getJcb().isSelected()) {
										ArrayList<String> recover = new ArrayList<>();
										int k = 0;
										for (int j = 0; j < entity.getAttributes().size(); j++) {
											String field = null;
											field = deleted.get(i).substring(k,
													k + entity.getAttributes().get(j).getLength());
											recover.add(field);
											// System.out.println(field);
											k = k + entity.getAttributes().get(j).getLength();
										}
										sf.returnFromRecycleBin(recover);
										tab.refreshTable();
									}
								}
								jframe.dispose();
							}
						});
						jframe.pack();
						jframe.setVisible(true);
					}
				});
				jp.add(buttonRecycleBin);
				jp.add(buttonDelete);
				jPanel.add(jp, BorderLayout.CENTER);
				// panel.add(jPanel);
				// panel.revalidate();
				// panel.repaint();
				return jPanel;
				
			} else if (tab != null && tab.getEntity().getType().toLowerCase().equals("sek")) {

				Entry slog = tab.getSelectedEntry();
				if (slog != null) {
					DeleteRowPanel delPanel = new DeleteRowPanel(slog);
					JPanel bpanel = new JPanel(new BorderLayout());
					bpanel.add(delPanel, BorderLayout.CENTER);

					return bpanel;
				}

			}

		}

		return null;

	}

}
