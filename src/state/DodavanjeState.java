package state;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Attribute;
import model.AttributeType;
import model.Entity;
import model.Entry;
import model.file.AbstractFile;
import model.file.IUDBFile;
import model.file.SEKFile;
import model.file.SERFile;
import view.AddRowPanel;
import view.MainFrame;
import view.MainTab;
import view.MyJPanel;
import view.RelationViewPanel;
import view.Tab;

public class DodavanjeState implements IState {

	public DodavanjeState() {

	}

	@Override
	public JPanel bottomPanel() {
		MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());
		// JPanel panel = MainFrame.getInstance().getPanelBot();
		// panel.removeAll();
		if (tab != null) {
			Entity entity = tab.getEntity();

			if (entity.getType().equalsIgnoreCase("ser")) {
				List<MyJPanel> myPanels = new ArrayList<>();
				JPanel jPanel = new JPanel();
				JPanel jp = new JPanel(new GridLayout(entity.getAttributes().size(), 5));
				jPanel.setLayout(new BorderLayout());
				for (int i = 0; i < entity.getAttributes().size(); i++) {
					MyJPanel myjp = new MyJPanel(entity.getAttributes().get(i));
					myPanels.add(myjp);
					jp.add(myjp);
				}
				JButton buttonSave = new JButton("Add");
				buttonSave.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int j = 0;
						for (int i = 0; i < myPanels.size(); i++) {
							MyJPanel myPanel = myPanels.get(i);
							if (myPanel.getJtf().getText().length() <= entity.getAttributes().get(i).getLength()) {
								if (entity.getAttributes().get(i).isMandatory()) {
									if (!myPanel.getJtf().getText().equals("")) {
										if (entity.getAttributes().get(i).getType()
												.equals(AttributeType.TYPE_NUMERIC)) {
											if (myPanel.getJtf().getText().matches("[0-9]+")) {
											} else {
												JOptionPane.showMessageDialog(null,
														"Bad value in " + entity.getAttributes().get(i), "Error",
														JOptionPane.INFORMATION_MESSAGE);
												System.out.println("Greska");
												break;
											}
											j++;
											// System.out.println("Mand Numeric " + entity.getAttributes().get(i));
											continue;
										} else {
											j++;
											// System.out.println("Mand Ostalo " + entity.getAttributes().get(i));
											continue;
										}
									}
								} else {
									if (entity.getAttributes().get(i).getType().equals(AttributeType.TYPE_NUMERIC)) {
										if (myPanel.getJtf().getText().matches("[0-9]+")) {
											j++;
											// System.out.println("Numeric " + entity.getAttributes().get(i));
											continue;
										} else {
											JOptionPane.showMessageDialog(null,
													"Bad value in " + entity.getAttributes().get(i), "Error",
													JOptionPane.INFORMATION_MESSAGE);
											System.out.println("Greska");
											break;
										}
									} else {
										j++;
										// System.out.println("Ostalo " + entity.getAttributes().get(i));
										continue;
									}
								}
							} else {
								JOptionPane.showMessageDialog(null, "Bad value in " + entity.getAttributes().get(i),
										"Error", JOptionPane.INFORMATION_MESSAGE);
								System.out.println("Greska " + entity.getAttributes().get(i));
								break;
							}
						}
						if (j == myPanels.size())
							callForAdd(myPanels, entity, tab);
					}
				});
				jp.add(buttonSave);
				jPanel.add(jp, BorderLayout.NORTH);
				// panel.add(jPanel);
				// panel.revalidate();
				// panel.repaint();
				return jPanel;
			} else if (tab != null && (tab.getEntity() instanceof SEKFile || tab.getEntity()  instanceof IUDBFile)) {

				Entry slog = new Entry(tab.getEntity());
				
				
				AddRowPanel addPanel = new AddRowPanel(slog);
				JPanel bpanel = new JPanel(new BorderLayout());
				bpanel.add(addPanel, BorderLayout.CENTER);

				return bpanel;
			}
		

		}
		return null;
	}

	private void callForAdd(List<MyJPanel> myPanels, Entity entity, MainTab tab) {
		ArrayList<String> lines = new ArrayList<>();
		String line;
		for (int i = 0; i < myPanels.size(); i++) {
			line = myPanels.get(i).getJtf().getText();
			for (int j = line.length(); j < entity.getAttributes().get(i).getLength(); j++) {
				line = line + " ";
			}
			lines.add(line);
		}
		try {
			((AbstractFile)entity).addRecord(lines);
			tab.refreshTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
