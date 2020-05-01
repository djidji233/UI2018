package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.Entity;

public class SQLCountElement extends JPanel{
	
	private SQLCountPanel parent;
	private Entity entity;
	private JComboBox<Attribute> cbGroup;
	private JButton btnDelete;
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public Attribute getSelected() {
		
		return (Attribute) cbGroup.getSelectedItem();
	}

	public SQLCountElement(Entity entity, SQLCountPanel parent) {
		super();
		this.parent = parent;
		this.entity = entity;
		init();
		setLayout(new GridLayout(1, 2,5,5));
	}

	private void init() {
		cbGroup = new JComboBox<Attribute>();
		for(Attribute attribute:entity.getAttributes())
			cbGroup.addItem(attribute);
		
	btnDelete = new JButton("x");
	
	btnDelete.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			parent.obrisi(SQLCountElement.this);
		}
	});
	
	cbGroup.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbGroup.getMinimumSize().height));
	btnDelete.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnDelete.getMinimumSize().height));
	add(cbGroup);
	add(btnDelete);

	setBorder(new EmptyBorder(5, 5, 5, 5));
	setMaximumSize(new Dimension(Integer.MAX_VALUE, btnDelete.getMinimumSize().height+5));
	
			
	
	}
	public String toString() {
		return cbGroup.getSelectedItem().toString();
	}


	

}
