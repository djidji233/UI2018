package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.MyController;
import model.Database;
import model.NodeMeta;
import model.Workspace;
import view.MainFrame;

public class CloseDatabaseAction extends AbstractAction {

	public CloseDatabaseAction() {
		putValue(SMALL_ICON, MyController.getInstance().ikonica("close"));
		putValue(NAME, "Delete Database");
		putValue(SHORT_DESCRIPTION, "Delete Database");
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object object = MainFrame.getInstance().getNodeTree().getSelectedObject();
		if (object instanceof NodeMeta) {
			NodeMeta node = (NodeMeta) object;
			if (node.getData() instanceof Database) {
				Workspace root = (Workspace) MainFrame.getInstance().getNodeTree().getModel().getRoot();
				root.removeChildren(node);
				System.out.println(root.getChildCount());
				MainFrame.getInstance().getPanelTop().getTabPane().removeAll();
				MainFrame.getInstance().getPanelBot().removeAll();
				SwingUtilities.updateComponentTreeUI(MainFrame.getInstance());
			} else {
				JOptionPane.showMessageDialog(null, "You have to select a database to remove.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

}
