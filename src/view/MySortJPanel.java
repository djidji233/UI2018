package view;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Attribute;

public class MySortJPanel extends JPanel{
	private JCheckBox jchb;
	private JComboBox<String> jcbb;
	private String[] items = {"Rastuce","Opadajuce"};

	public MySortJPanel(Attribute attribute) {
		jchb = new JCheckBox(attribute.getName());
		jcbb = new JComboBox<>(items);
		this.add(jchb);
		this.add(jcbb);
	}


	public JCheckBox getJchb() {
		return jchb;
	}
	public JComboBox<String> getJcbb() {
		return jcbb;
	}

}
