package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.sound.midi.SoundbankResource;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.ComboBox;
import model.Attribute;
import model.Database;
import model.Entity;
import model.Relation;

public class RelationView extends JPanel{

	private Database db;
	private Relation relation;

	private JTextField relationName;
	private boolean inicijalizacija;
	JPanel bottomPanel;

	public RelationView(Database db, Relation relation) {

		this.db = db;
		this.relation = relation;
		init();

	}

	void init() {
		inicijalizacija = true;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel nameLabel = new JLabel("Ime Relacije: ");
		nameLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

		relationName = new JTextField(relation.getName());
		relationName.setBorder(new EmptyBorder(5, 5, 5, 5));
		relationName.setMaximumSize(new Dimension(Integer.MAX_VALUE, relationName.getMinimumSize().height));
		relationName.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
					String text =relationName.getText();
					 System.out.println("Text=" + text);
					if(text != null && text.trim().length() >0)
						relation.setName(text);
			}
			

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}});
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.add(nameLabel);
		topPanel.add(relationName);

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topPanel.getMinimumSize().height));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JComboBox<Entity> targetBox = new JComboBox<>();
		targetBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		targetBox.setModel(new DefaultComboBoxModel(db.getEntities().toArray()));
		targetBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int i = targetBox.getSelectedIndex();
				if (e.getStateChange() == ItemEvent.SELECTED && i != -1 && !inicijalizacija) {
					relation.setTargetEntity((Entity) e.getItem());
					popuni();
				}
			}
		});

		JComboBox<Entity> sourceBox = new JComboBox<>();
		sourceBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		sourceBox.setModel(new DefaultComboBoxModel(db.getEntities().toArray()));
		sourceBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && sourceBox.getSelectedIndex() != -1 && !inicijalizacija) {
					relation.setSourceEntity((Entity) e.getItem());
					popuni();
				}
			}
		});

		this.add(topPanel);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1, 2));
		middlePanel.add(targetBox);
		middlePanel.add(sourceBox);
		middlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, middlePanel.getMinimumSize().height));
		this.add(middlePanel);
		this.revalidate();

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		this.add(bottomPanel);

		Entity source = relation.getSourceEntity();
		Entity target = relation.getTargetEntity();

		//sourceBox.setSelectedIndex(-1);
		//targetBox.setSelectedIndex(-1);
		//targetBox.setSelectedItem(target);

		if (source != null && target != null) {
			int n = relation.getSourceKeys().size();
			if (n == 0) {
				initRelation();
			}

			sourceBox.setSelectedItem(source);
			targetBox.setSelectedItem(target);
			popuni();

			
		}
		inicijalizacija = false;
		revalidate();
		
		relationName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text =relationName.getText();
				if(text != null && text.trim().length() >0)
				relation.setName(text);
				
			}
		});

	}
	
	private void initRelation() {
		
		relation.setSourceKeys(new ArrayList<Attribute>());
		relation.setTargetKeys(new ArrayList<Attribute>());
		
		Entity source = relation.getSourceEntity();
		for (Attribute a : source.getAttributes()) {
			if (a.isPrimaryKey()) {
				relation.getSourceKeys().add(a);
				relation.getTargetKeys().add(null);
			}
		}
		
	}

	protected void popuni() {

		Entity source = relation.getSourceEntity();
		Entity target = relation.getTargetEntity();

		if (source != null && target != null) {

	
			bottomPanel.removeAll();

			//int n = Integer.min(a, b)
			for (int i=0;i<relation.getSourceKeys().size();i++) {
				
					List<Attribute> a = relation.getSourceKeys();
					List<Attribute> b = relation.getTargetKeys();
					Attribute sourceKey =  relation.getSourceKeys().get(i);
					Attribute targetKey =  relation.getTargetKeys().get(i);
					bottomPanel.add(new RelationRow(i, relation,sourceKey, target, targetKey));
				}
			}
			revalidate();
		}


}
