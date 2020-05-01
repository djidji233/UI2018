package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.Attribute;
import model.Entity;

public class AttributeGrid extends JPanel {
	
	
	private Entity entity;
	public AttributeGrid(Entity entyty) {
		
		this.entity = entyty;
		BoxLayout layout =new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		
		
		
		JPanel header = new JPanel(new GridLayout(1,7,5,5));
		header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getMinimumSize().height+20));
		header.add(new JLabel("pk"));
		header.add(new JLabel("name"));
		header.add(new JLabel("type"));
		header.add(new JLabel("mandatory"));
		header.add(new JLabel("def Value"));
		header.add(new JLabel("length"));
		header.add(new JLabel("del"));
		this.add(header);
		
		for(Attribute a:entyty.getAttributes())
		{
			JPanel p =new AttributeRow(a, entyty, this);
			p.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getMinimumSize().height+20));
			p.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.add(p);
		}
		
	}
	
	

}
