package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Attribute;
import model.AttributeType;
import model.Entity;

public class MySQLSearch extends JFrame {

	private Entity entity = null;

	public MySQLSearch(MainTab tab) {
		entity = tab.getEntity();
		initialiseGui();
	}

	private void initialiseGui() {
		this.setSize(500, 400);
		this.setTitle("Search");
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new SQLSearchPanel(entity));
		this.setVisible(true);
	}


}
