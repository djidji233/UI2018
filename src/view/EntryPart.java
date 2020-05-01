package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Attribute;

public class EntryPart extends JPanel{
	
	private JLabel label;
	private JTextField tf;
	
	Attribute attribute;
	
	public JLabel getLabel() {
		return label;
	}

	public JTextField getTf() {
		return tf;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public EntryPart(Attribute a, String s) {
		super();
		
		 label = new JLabel();
		 tf = new JTextField();
		 
		 attribute = a;
		 
		 label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getMinimumSize().height));
		 tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, tf.getMinimumSize().height));
		 
		 this.setMaximumSize(new Dimension(Integer.MAX_VALUE, tf.getMinimumSize().height));
		 
		 setBorder(new EmptyBorder(5, 5, 5, 5));
		 

		setLayout(new GridLayout(1,2,5,5));
		
		add(label);
		add(tf);
		
		label.setText(a.getName());
		if(s!=null)
			tf.setText(s.trim());
		
		
	}
	
	public boolean check() {
		
		if(tf.getText().length()<=attribute.getLength())
			return true;
		return false;
	}
	
	public String getText() {
		
		return tf.getText().length() == 0 ? null:tf.getText();
	}
	
}
