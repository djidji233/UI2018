package view;

import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

public class NodeTreeEditor extends DefaultTreeCellEditor{
	private Object obj = null;
	private JTextField naziv = null;

	public NodeTreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);
	}

	public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4,
			int arg5) {
		obj = arg1;
		naziv = new JTextField(arg1.toString());
		naziv.addActionListener(this);
		return naziv;
	}
}
