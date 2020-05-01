package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.Entity;

public class EntityView extends JPanel {
	
	Entity entity;
	private JTextField entityName;
	
	public EntityView(Entity entiy) {
		this.entity = entiy;
		

		setLayout(new BorderLayout());
		JLabel nameLabel = new JLabel("Ime Entiteta: ");
		nameLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		entityName = new JTextField(entity.getName());
		entityName.setBorder(new EmptyBorder(5, 5, 5, 5));		
		entityName.setMaximumSize(new Dimension(Integer.MAX_VALUE, entityName.getMinimumSize().height));
		
		entityName.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
					String text =entityName.getText();
					 System.out.println("Text=" + text);
					if(text != null && text.trim().length() >0)
						entiy.setName(text);
			}
			

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.add(nameLabel);
		topPanel.add(entityName);
		
		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, topPanel.getMinimumSize().height));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		this.add(topPanel, BorderLayout.NORTH);
		
		AttributeGrid grid = new AttributeGrid(entiy);

		//JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//middlePanel.add((JPanel)grid);
		
		JPanel middlePanel = new JPanel(new BorderLayout());
		middlePanel.add(grid,BorderLayout.CENTER);
		
		this.add(middlePanel,BorderLayout.CENTER);
		this.revalidate();
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(Box.createHorizontalGlue());
		JButton addAttribure = new JButton("dodaj");
		bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottomPanel.add(addAttribure);
		addAttribure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				entiy.addAtribute(new Attribute());
				middlePanel.removeAll();
				middlePanel.add(new AttributeGrid(entiy));
				revalidate();
			}
		});
		this.add(bottomPanel,BorderLayout.SOUTH);
		
		
		
		
		
	}
	

}
