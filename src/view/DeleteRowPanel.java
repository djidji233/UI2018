package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Entry;
import model.IzmenaItem;
import model.file.SEKFile;

public class DeleteRowPanel extends JPanel{

	public DeleteRowPanel(Entry slog) {
		super();
		
		DetailsPanel d = new DetailsPanel(slog);
		d.disable();
		d.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.getMinimumSize().height));
	
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setLayout(new BorderLayout());
		
		JPanel dugmici = new JPanel();
		dugmici.setLayout(new BoxLayout(dugmici, BoxLayout.X_AXIS));
		
		JButton brisi = new JButton("obrisi");
		//dodaj.setBorder(new EmptyBorder(10, 10, 10, 10));
		brisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Entry res = d.getData();
				if(res!= null)
				{
					((SEKFile)slog.getEntitet()).dodajIzmenu(new IzmenaItem(res, IzmenaItem.BRISANJE));
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
		dugmici.add(sinh);
		dugmici.add(Box.createRigidArea(new Dimension(5, 0)));
		dugmici.add(brisi);

		add(Box.createVerticalGlue());
		add(dugmici);
		
	}
	
	
}
