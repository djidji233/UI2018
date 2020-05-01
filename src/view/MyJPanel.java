package view;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Attribute;

public class MyJPanel extends JPanel {

	private JTextField jtf;
	private JCheckBox jcb;

	public MyJPanel(Attribute attribute) {
		jtf = new JTextField(20);
		JLabel lb = new JLabel(attribute.getName());
		this.add(lb);
		this.add(jtf);
	}

	public MyJPanel(String name) {
		jcb = new JCheckBox(name);
		this.add(jcb);
	}

	public JTextField getJtf() {
		return jtf;
	}

	public JCheckBox getJcb() {
		return jcb;
	}

}
