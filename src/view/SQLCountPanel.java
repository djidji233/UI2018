package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Attribute;
import model.Entity;
import model.file.IUDBFile;

public class SQLCountPanel extends JPanel{
	
	private Entity entity;
	
	private JButton btnTrazi;
	private JPanel listPanel;
	private JComboBox<Attribute> cbCount;
	private JPanel box;
	private JButton btnDodaj;
	
	public SQLCountPanel(Entity entity) throws HeadlessException {
		super();
		
		this.entity = entity;
		
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		
		setLayout(new BorderLayout());
		
		cbCount = new JComboBox<Attribute>();
		cbCount.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbCount.getMinimumSize().height));
		for(Attribute attribute:entity.getAttributes())
			cbCount.addItem(attribute);
		
		box.add(new JLabel("count"));
		box.add(cbCount);
		box.add(new JLabel("Group by"));
		
		listPanel = new JPanel();	
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		SQLCountElement prvi = new SQLCountElement(entity, this);
		listPanel.add(prvi);
		box.add(listPanel);
		
		

		
		btnTrazi = new JButton("trazi");
		btnDodaj = new JButton("Dodaj");
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			  addNext();
				
			}
		});
		btnTrazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				trazi();
			}

			
		});
		JPanel p =  new JPanel();
		p.add(btnDodaj);
		p.add(btnTrazi);
		add(p, BorderLayout.SOUTH);
		
		this.add(box, BorderLayout.CENTER);
		
		
	}

	private void trazi() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("SELECT COUNT(%s)", ((Attribute)cbCount.getSelectedItem()).getName()));
		
		List<String> fields = new ArrayList<>();
		
		
		for(int i=0;i<listPanel.getComponentCount();i++) {
			
			SQLCountElement line = (SQLCountElement)listPanel.getComponent(i);
			
			String part = line.toString();
		
			s.append(", ");
			s.append(part);
			
		}
		s.append(" FROM ");
		s.append(entity.getName());
		s.append(" GROUP BY ");
		
		List<Attribute> atributi = new ArrayList<>();
		Attribute at = new Attribute();
		at.setName("Count("+((Attribute)cbCount.getSelectedItem()).getName()+")");
		atributi.add(at);
		
		for(int i=0;i<listPanel.getComponentCount();i++) {
			
			SQLCountElement line = (SQLCountElement)listPanel.getComponent(i);
			atributi.add(line.getSelected());
			
			String part = line.toString();
			s.append(part);
			if(i != listPanel.getComponentCount()-1)
				s.append(", ");
				
			
		}
		
		
		

		try {
			((IUDBFile)entity).ﬁndFilterRecord(s.toString(),fields,atributi);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		
		System.out.println(s.toString());

		
	}
	
	public void addNext() {
			listPanel.add(new SQLCountElement(entity, this));

		this.revalidate();
		this.repaint();
	}
	public void obrisi(SQLCountElement element) {
		listPanel.remove(element);

		this.revalidate();
		this.repaint();
	}


	private void errorMsg(String s) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), s);
	}

	
}
