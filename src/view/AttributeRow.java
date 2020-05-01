package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import model.Attribute;
import model.AttributeType;
import model.Entity;
import model.Observer;

public class AttributeRow extends JPanel implements Observer{
	
	private Attribute attribute;
	
	private JCheckBox pkBox;
	private JTextField attributeName;
	private JComboBox<AttributeType> typeBox;
	private JCheckBox mandBox;
	private JTextField defaultVal;
	private JTextField len;
	private JButton del;
	
	
	
	public AttributeRow(Attribute attrubute,Entity entity, JPanel parrent) {
		JPanel row = this;
		
		this.attribute = attrubute;
		setLayout(new GridLayout(1, 7,5,5));
		
		init();
		
		add(pkBox);
		add(attributeName);
		add(typeBox);
		add(mandBox);
		add(defaultVal);
		add(len);
		add(del);
		
		
		pkBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean state = e.getStateChange()==ItemEvent.SELECTED;
				attrubute.setPrimaryKey(state);
				
			}
		});
		mandBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean state = e.getStateChange()==ItemEvent.SELECTED;
				attrubute.setMandatory(state);
				
			}
		});
		attributeName.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String text =attributeName.getText();
				 System.out.println("Text=" + text);
				if(text != null && text.trim().length() >0)
					attribute.setName(text);
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		defaultVal.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String text =defaultVal.getText();
				if(text != null && text.trim().length() >0)
					attrubute.setDefValue(text);
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		len.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String text =len.getText();
				if(text != null && text.trim().length() >0)
					attrubute.setLength(Integer.parseInt(text));
				
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		typeBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && typeBox.getSelectedIndex() != -1) {
					attrubute.setType((AttributeType) e.getItem());
				}
			}
		});
		del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				entity.getAttributes().remove(attrubute);
				parrent.remove(row);
				parrent.revalidate();
			}
		});
	}



	public void sacuvajStanje() {
		attribute.setType((AttributeType) typeBox.getSelectedItem());
		attribute.setPrimaryKey(pkBox.isSelected());
		attribute.setMandatory(mandBox.isSelected());
		
		String text;
		text = attributeName.getText();
		if(text !=null && text.trim().length() >0)
			attribute.setName(attributeName.getText());
		
		text = defaultVal.getText();
		if(text !=null && text.trim().length() >0)
			attribute.setDefValue(attributeName.getText());
		
		text = len.getText();
		if(text !=null && text.trim().length() >0)
			attribute.setLength(Integer.parseInt(attributeName.getText()));
		
	}
	private void init() {
		pkBox =  new JCheckBox();
		pkBox.setSelected(attribute.isPrimaryKey());	
		
		attributeName = new JTextField();
		attributeName.setText(attribute.getName());

		
		mandBox = new JCheckBox();
		mandBox.setSelected(attribute.isMandatory());
		
		typeBox = new JComboBox<>(AttributeType.values());
		typeBox.setSelectedItem(attribute.getType());
		
		defaultVal = new JTextField();
		if(attribute.getDefValue() != null)
		defaultVal.setText(attribute.getDefValue().toString());
		
		del = new JButton("x");
		
		
		len = new JTextField();
		len.setText("" + attribute.getLength());
		
	}



	@Override
	public void update() {
		sacuvajStanje();
		
	}




}
