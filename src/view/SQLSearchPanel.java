package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.file.IUDBFile;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Entity;

public class SQLSearchPanel extends JPanel {

	
	private JButton btnTrazi;
	private JPanel listPanel;
	private Entity entity;
	
	public SQLSearchPanel(Entity entity) {
		super();
		setLayout(new BorderLayout());
		this.entity = entity;
		
		listPanel = new JPanel();
		
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		
		SQLSearchEelement prvi = (new SQLSearchEelement(entity, this));
		prvi.getBtnDelete().setEnabled(false);
		
		listPanel.add(prvi);
		
		btnTrazi = new JButton("trazi");
		//btnTrazi.setSize(40, 20);
		JPanel p =  new JPanel();
		p.add(btnTrazi);
		add(p, BorderLayout.SOUTH);
		
		btnTrazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				trazi();
			}

			
		});
		this.add(listPanel, BorderLayout.CENTER);
		

	}
	private void trazi() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("SELECT * from %s where",entity.getName()));
		
		List<String> fields = new ArrayList<>();
		
		
		for(int i=0;i<listPanel.getComponentCount();i++) {
			
			SQLSearchEelement line = (SQLSearchEelement)listPanel.getComponent(i);
			
			StringBuilder field = new StringBuilder();
			String part = line.prepare(field);
			fields.add(field.toString());
			
			System.out.println("FIELD: "+field);
			
			if(part == null) {
				errorMsg("linija "+i+" neispravna");
				return;
			}
			s.append(" ");
			s.append(part);
			
		}

			try {
				((IUDBFile)entity).ﬁndFilterRecord(s.toString(),fields);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		System.out.println(s.toString());
		
	}
	
	public void addNext(SQLSearchEelement element) {
		if(listPanel.getComponent(listPanel.getComponentCount()-1) == element)
			listPanel.add(new SQLSearchEelement(entity, this));

		this.revalidate();
		this.repaint();
	}
	public void obrisi(SQLSearchEelement element) {
		listPanel.remove(element);

		this.revalidate();
		this.repaint();
	}


	private void errorMsg(String s) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), s);
	}

	
	
	
}
