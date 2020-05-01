package view;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Attribute;
import model.AttributeType;

public class MySqlSearchPanel extends JPanel {

	private List<Attribute> attributes = null;
	private JTextField jtextf = null;
	private JComboBox<String> jOperations = null;
	private JPanel panelRight = null;
	private JComboBox<Attribute> column = null;

	public MySqlSearchPanel(List<Attribute> attributes) {
		this.attributes = attributes;
		initialiseGui();
	}

	private void initialiseGui() {
		JPanel panelLeft = new JPanel();
		panelRight = new JPanel();
		column = new JComboBox<>();
		for (int i = 0; i < this.attributes.size(); i++) {
			column.addItem(this.attributes.get(i));
		}
		column.setSelectedIndex(-1);
		panelLeft.add(column);
		column.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				changeBottomPanel((Attribute) column.getSelectedItem());
			}
		});
		this.add(panelLeft, BorderLayout.WEST);
		this.add(panelRight, BorderLayout.EAST);
	}

	private void changeBottomPanel(Attribute attribute) {
		if (panelRight.getComponentCount() > 0) {
			panelRight.removeAll();
		}
		jtextf = new JTextField(20);
		jOperations = new JComboBox<>();
		if (attribute.getType().equals(AttributeType.TYPE_NUMERIC)) {
			jOperations.addItem(">");
			jOperations.addItem("<");
			jOperations.addItem("=");
			jOperations.addItem(">=");
			jOperations.addItem("<=");
		} else {
			jOperations.addItem("LIKE");
			jOperations.addItem("=");
		}
		panelRight.add(jOperations);
		panelRight.add(jtextf);
		this.revalidate();
		this.repaint();
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public JTextField getJtextf() {
		return jtextf;
	}

	public JComboBox<String> getjOperations() {
		return jOperations;
	}

	public JPanel getPanelRight() {
		return panelRight;
	}

	public void setPanelRight(JPanel panelRight) {
		this.panelRight = panelRight;
	}

	public JComboBox<Attribute> getColumn() {
		return column;
	}
}
