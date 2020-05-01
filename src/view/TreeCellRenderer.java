package view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import controller.MyController;
import model.Database;
import model.Entity;

public class TreeCellRenderer extends DefaultTreeCellRenderer {
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		if(value instanceof Entity)
			System.out.println("cao");
		
		if (expanded || leaf) {
			Icon icon = new ImageIcon("images/item1.png");
			setIcon(icon);
		} else {
			Icon icon = new ImageIcon("images/item0.png");
			setIcon(icon);
		}
		return this;
	}
}
