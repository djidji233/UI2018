package view;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import controller.MouseListen;

public class NodeTree extends JTree {

	public NodeTree() {
		setCellEditor(new NodeTreeEditor(this, new DefaultTreeCellRenderer()));
		setCellRenderer(new TreeCellRenderer());
		setEditable(true);
		this.addMouseListener(new MouseListen());
	}

	public Object getSelectedObject() {
		TreePath treePath = MainFrame.getInstance().getNodeTree().getSelectionPath();
		if (treePath != null) {
			Object object = treePath.getLastPathComponent();
			return object;
		}
		return null;
	}

}
