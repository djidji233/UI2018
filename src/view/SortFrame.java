package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Entity;

public class SortFrame extends JFrame{
	private JPanel panel;
	private List<MySortJPanel> myPanels;
	private JButton btnSort;
	
	public SortFrame(Entity entity) {
		this.setSize(300, 600);
		this.setName("Sort");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		panel = new JPanel(new GridLayout(entity.getAttributes().size() + 1, 1, 1, 1));
		myPanels = new ArrayList<>();
		for (int i = 0; i < entity.getAttributes().size(); i++) {
			MySortJPanel mjp = new MySortJPanel(entity.getAttributes().get(i));
			myPanels.add(mjp);
			panel.add(mjp);
		}
		btnSort = new JButton("Sort");
		//btnSort.setMinimumSize(new Dimension(50,30));
		//btnSort.setPreferredSize(new Dimension(50,30));
		panel.add(btnSort);
		this.add(panel);
	}
}
