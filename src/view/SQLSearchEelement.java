package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.GapContent;

import model.Attribute;
import model.AttributeType;
import model.Entity;

public class SQLSearchEelement extends JPanel{

	private SQLSearchPanel parent;
	
	private Entity entity;
	private JComboBox<Attribute> cbAtribute;
	private JComboBox<String> cbOperation;
	private JTextField tfValue;
	private JButton btnDelete;
	
	public JButton getBtnDelete() {
		return btnDelete;
	}

	JComboBox<String> cbAndOr;
	
	
	public SQLSearchEelement(Entity entity, SQLSearchPanel parent) {	
		
		
		this.parent = parent;
		this.entity = entity;
		init();
		setLayout(new GridLayout(1, 5,5,5));
		
		
	}


	private void init() {
	
		cbAtribute = new JComboBox<Attribute>();
		for(Attribute attribute:entity.getAttributes())
			cbAtribute.addItem(attribute);
		cbAtribute.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				changeAttributeType((Attribute)cbAtribute.getSelectedItem());
				
			}
			
		});
		
		cbOperation = new JComboBox<>();
		changeAttributeType((Attribute) cbAtribute.getSelectedItem());
		tfValue = new JTextField();
		
		cbAndOr = new JComboBox<>();
		
		cbAndOr.addItem("");
		cbAndOr.addItem("AND");
		cbAndOr.addItem("OR");
		cbAndOr.setSelectedIndex(0);
		
		cbAndOr.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(cbAndOr.getSelectedIndex() != -1)
					parent.addNext(SQLSearchEelement.this);
			}
		});
		
		btnDelete = new JButton("x");
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.obrisi(SQLSearchEelement.this);
			}
		});
		
		cbAtribute.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbAtribute.getMinimumSize().height));
		cbOperation.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbOperation.getMinimumSize().height));
		tfValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, tfValue.getMinimumSize().height));
		cbAndOr.setMaximumSize(new Dimension(Integer.MAX_VALUE, cbAndOr.getMinimumSize().height));
		btnDelete.setMaximumSize(new Dimension(Integer.MAX_VALUE, btnDelete.getMinimumSize().height));
		
		add(cbAtribute);
		add(cbOperation);
		add(tfValue);
		add(cbAndOr);
		add(btnDelete);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setMaximumSize(new Dimension(Integer.MAX_VALUE, cbAndOr.getMinimumSize().height+5));

	}
	
	public String prepare(StringBuilder field) {
		
		if(cbAtribute.getSelectedIndex() == -1)
			return null;
		if(cbOperation.getSelectedIndex() == -1)
			return null;
		if(tfValue.getText().equals(""))
			return null;
		
		field.append(tfValue.getText());
		
		return  ((Attribute)cbAtribute.getSelectedItem()).getName() + 
				" " + cbOperation.getSelectedItem().toString() +
				" ? " + cbAndOr.getSelectedItem().toString();
		
		
		
	}
	
	public String toString() {
		if(cbAtribute.getSelectedIndex() == -1)
			return null;
		if(cbOperation.getSelectedIndex() == -1)
			return null;
		if(tfValue.getText().equals(""))
			return null;
		
		return  ((Attribute)cbAtribute.getSelectedItem()).getName() + 
				" " + cbOperation.getSelectedItem().toString() +
				" '" + tfValue.getText() + "' " + cbAndOr.getSelectedItem().toString();
		
		
	}
	
	private void changeAttributeType(Attribute attribute) {
		 	if(attribute == null)
		 		return;

		   cbOperation.removeAllItems();
		if (attribute.getType().equals(AttributeType.TYPE_NUMERIC)) {
			cbOperation.addItem(">");
			cbOperation.addItem("<");
			cbOperation.addItem("=");
			cbOperation.addItem(">=");
			cbOperation.addItem("<=");
		} else {
			cbOperation.addItem("LIKE");
			cbOperation.addItem("=");
		}
		
		this.revalidate();
		this.repaint();
	}

	

	
}
