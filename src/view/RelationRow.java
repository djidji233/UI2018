package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.Entity;
import model.Relation;

public class RelationRow extends JPanel{
	
	
	public RelationRow(int index, Relation relation, Attribute source, Entity targetEntity, Attribute target) {
		
		ArrayList<Attribute> targets = new ArrayList<>();
		
		for(Attribute a:targetEntity.getAttributes()) {
			if(a.getType() == source.getType())
				targets.add(a);
		}
		
		JLabel sourceLabel = new JLabel(source.getName());
		sourceLabel.setBorder(new EmptyBorder(5, 5, 5, 35));
		sourceLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, sourceLabel.getMinimumSize().height));
		
		JComboBox<Attribute> targetBox = new JComboBox<>();
		targetBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		targetBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, targetBox.getMinimumSize().height));
		targetBox.setModel(new DefaultComboBoxModel(targets.toArray()));
		//targetBox.setSelectedIndex(-1);
		targetBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if( targetBox.getSelectedIndex() != -1) {
				Attribute element = (Attribute)e.getItem();
				relation.getTargetKeys().set(index, element);
				}
				
			}
		});
		
		if(target != null) {
			int i = targets.indexOf(target);
			targetBox.setSelectedItem(target);
		}

		setLayout(new GridLayout(1, 2));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, targetBox.getMinimumSize().height));
		add(targetBox);
		add(sourceLabel);
		revalidate();

	}
	
}
