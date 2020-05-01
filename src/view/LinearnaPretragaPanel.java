package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Entity;
import model.TableModel;
import model.file.SEKFile;
import model.file.SERFile;

public class LinearnaPretragaPanel extends JPanel {

	private JButton btnPretraga;
	private JPanel atributiPanel;
	private JPanel detaljiPanel;
	private JPanel rezultatPanel;
	private List<MyJPanel> myPanels;
	private JComboBox<String> cbbPointer;
	private int cbPointerFlag = 0;
	private JComboBox<String> cbbLocation;
	private int cbLocationFlag = 0;
	private JCheckBox chbStopAtFirst;
	private Entity entity;

	public LinearnaPretragaPanel(Entity e) {
		entity = e;
		this.setLayout(new GridLayout(1, 3, 5, 5));

		atributiPanel = new JPanel(new GridLayout(entity.getAttributes().size(), 1));
		myPanels = new ArrayList<>();
		for (int i = 0; i < entity.getAttributes().size(); i++) {
			MyJPanel mjp = new MyJPanel(entity.getAttributes().get(i));
			myPanels.add(mjp);
			atributiPanel.add(mjp);
		}
		detaljiPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		cbbPointer = new JComboBox<>();
		cbbPointer.addItem("od pocetka");
		cbbPointer.addItem("od trenutnog");
		cbbLocation = new JComboBox<>();
		cbbLocation.addItem("radna memorija");
		cbbLocation.addItem("nova datoteka");
		chbStopAtFirst = new JCheckBox("stop at first found");
		btnPretraga = new JButton("Linearno Pretrazivanje");

		this.add(atributiPanel);
		detaljiPanel.add(cbbPointer);
		detaljiPanel.add(cbbLocation);
		detaljiPanel.add(chbStopAtFirst);
		detaljiPanel.add(btnPretraga);
		this.add(detaljiPanel);

		rezultatPanel = new JPanel();
		this.add(rezultatPanel);

		btnPretraga.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("LINEARNA PRETRAGA - SEK");
				String cbPointerString = (String) cbbPointer.getSelectedItem();
				if (cbPointerString.equals("od pocetka")) // za podesavanje file pointera
					cbPointerFlag = 0;
				else
					cbPointerFlag = 1;

				String cbLocationString = (String) cbbLocation.getSelectedItem();
				if (cbLocationString.equals("radna memorija")) // za ispis
					cbLocationFlag = 0;
				else
					cbLocationFlag = 1;

				ArrayList<String> lines = new ArrayList<>();
				for (int i = 0; i < myPanels.size(); i++) {
					lines.add(myPanels.get(i).getJtf().getText());
				}

				SEKFile sf = (SEKFile) entity;
				ArrayList<String> found = new ArrayList<>();
				String[][] data = null;

				if (chbStopAtFirst.isSelected()) { // prvi sledeci - od pocetka / od trenutnog
					found = sf.findOne(lines, cbPointerFlag);
					//System.out.println(found);
					data = new String[sf.getBLOCK_SIZE()][entity.getAttributes().size()];
					for (int i = 0; i < found.size(); i++) {
						String line = found.get(i);
						int k = 0;
						String field = null;
						for (int j = 0; j < entity.getAttributes().size(); j++) {
							if (i > 0) {
								data[i][j] = "";
							} else {
								field = line.substring(k, k + entity.getAttributes().get(j).getLength());
								data[i][j] = field;
								k = k + entity.getAttributes().get(j).getLength();
							}

						}
					}
				} else {
					// found = sf.findMultiple(lines, cbPointerFlag);
					data = sf.findMultiple(lines, cbPointerFlag);
					// data = new String[sf.getBLOCK_SIZE()][entity.getAttributes().size()];
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
				}

				// ISPIS

				if (cbLocationFlag == 0) { // u radnu memoriju

					JTable tabela = new JTable(new TableModel(entity.getAttributes(), data));
					
					tabela.setPreferredScrollableViewportSize(new Dimension(400,200));
					if (0 < rezultatPanel.getComponentCount())
						rezultatPanel.remove(rezultatPanel.getComponentCount() - 1);
					rezultatPanel.add(new JScrollPane(tabela), BorderLayout.PAGE_END);
					rezultatPanel.revalidate();
					rezultatPanel.repaint();

				} else { // u datoteku
					try {
						// upis
						PrintWriter pw = new PrintWriter("LinearnaPretraga.txt", "UTF-8");
						for(int i=0; i<data.length; i++) {
							for(int j=0; j<entity.getAttributes().size(); j++) {
								pw.print(data[i][j].trim());
								// fensi ispis - kao u original fajlu
								for(int k=0; k<(entity.getAttributes().get(j).getLength() - data[i][j].trim().length()); k++) {
									pw.print(" ");
								} 	
							}
							pw.println();
						}
						pw.close();
						
						// otvaranje fajla
						Desktop d = null;
						File file = new File("LinearnaPretraga.txt");
						if(Desktop.isDesktopSupported()) {
							d = Desktop.getDesktop();
						}
						d.open(file);
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
