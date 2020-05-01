package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.Entry;
import model.IzmenaItem;
import model.file.IUDBFile;
import model.file.SEKFile;

public class AddRowPanel extends JPanel{

	public AddRowPanel(Entry slog) {
		super();
		
		DetailsPanel d = new DetailsPanel(slog);
		d.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.getMinimumSize().height));
	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setLayout(new BorderLayout());
		
		JPanel dugmici = new JPanel();
		dugmici.setLayout(new BoxLayout(dugmici, BoxLayout.X_AXIS));
		
		JButton dodaj = new JButton("dodaj");
		//dodaj.setBorder(new EmptyBorder(10, 10, 10, 10));
		dodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Entry res = d.getData();
				
				if(res.getEntitet()  instanceof IUDBFile)
					((IUDBFile)res.getEntitet()).setLastAdded(res);

				if(res!= null)
				{
					if(slog.getEntitet() instanceof SEKFile)
						((SEKFile)slog.getEntitet()).dodajIzmenu(new IzmenaItem(res, IzmenaItem.DODAVANJE));
					
					else if(slog.getEntitet() instanceof IUDBFile);
					{
						
						IUDBFile file = ((IUDBFile)slog.getEntitet());
						ArrayList<String> attrs = new ArrayList<>();
						ArrayList<String> fields = new ArrayList<>();
						
						for (Map.Entry<Attribute, String>entry : res.getFields().entrySet()) {
							
						    attrs.add(entry.getKey().getName());
						    fields.add(entry.getValue());
						   // System.out.println(entry.getKey());
						   // System.out.println(entry.getValue());
						}
						try {
							file.addRecord(attrs,fields);
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		JButton sinh = new JButton("sinhronizuj");
		//sinh.setBorder(new EmptyBorder(10, 10, 10, 10));
		sinh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				((SEKFile)slog.getEntitet()).sinhronizuj();
				
				
			}
		});
		add(d);
		
		dugmici.setBorder(new EmptyBorder(10, 10, 10, 10));
		dugmici.add(Box.createHorizontalGlue());
		if(slog.getEntitet() instanceof SEKFile)
			dugmici.add(sinh);
		dugmici.add(Box.createRigidArea(new Dimension(5, 0)));
		dugmici.add(dodaj);

		add(Box.createVerticalGlue());
		add(dugmici);
		
		
	}

	
	
	

}
