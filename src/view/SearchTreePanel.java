package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.Entity;
import model.Entry;
import model.IzmenaItem;
import model.file.AbstractFile;
import model.tree.Node;
import model.tree.NodeElement;
import model.tree.Tree;

public class SearchTreePanel extends JPanel {

	
	public SearchTreePanel(Entry slog) {
		super();
		
		DetailsPanel d = new DetailsPanel(slog,true);
		
		d.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.getMinimumSize().height));
	
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setLayout(new BorderLayout());
		
		JPanel dugmici = new JPanel();
		dugmici.setLayout(new BoxLayout(dugmici, BoxLayout.X_AXIS));
		
		JButton trazi = new JButton("Trazi");
		trazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Entry res = d.getDataPk();
				
				if(res!= null)
				{

					Entity entitet = res.getEntitet();
					Node root = entitet.getStablo().getRootElement();
					
					NodeElement newNode = new NodeElement(res);
					
					long adresa = root.find(newNode);
					System.out.println("adresa "+adresa);
					
					long findPointer = adresa* ((AbstractFile)entitet).getRECORD_SIZE();
					
					((AbstractFile)entitet).setFILE_POINTER(findPointer);
					((AbstractFile)entitet).setBLOCK_SIZE(20);
					
					
					try {
						((AbstractFile)entitet).fetchNextBlock();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
					
					MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());
					
					
					String[][] data = ((AbstractFile)entitet).getData();
					tab.setData(data);
					
					List<Attribute> atributi = entitet.getAttributes();
					int n = atributi.size();
					
					
					int pos =-1;
					for(int j=0;j<data.length;j++)
					{
						pos = j;
						for(int i=0;i<n;i++) {
							if(atributi.get(i).isPrimaryKey()) {
								if(!data[j][i].trim().equalsIgnoreCase(res.getFields().get(atributi.get(i)))) {
									pos =-1;
								}
							}
							else break;
							
						}
						if(pos != -1)
							break;
					}
					
					if(pos !=-1) {
						
						tab.getTabela().addRowSelectionInterval(pos, pos);
					}
					
					
				}
			}
		});
		
		
		
		add(d);
		
		dugmici.setBorder(new EmptyBorder(10, 10, 10, 10));
		dugmici.add(Box.createHorizontalGlue());
		dugmici.add(trazi);

		add(Box.createVerticalGlue());
		add(dugmici);
		
	}
	
	
}
