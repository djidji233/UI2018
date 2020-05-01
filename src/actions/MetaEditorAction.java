package actions;

import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import controller.MyController;
import view.MainFrame;
import view.MetaEditorFrame;

import java.awt.event.ActionEvent;

public class MetaEditorAction extends AbstractAction {

	public MetaEditorAction() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));
		putValue(SMALL_ICON, MyController.getInstance().ikonica("edit"));
		putValue(NAME, "MetaEditor");
		putValue(SHORT_DESCRIPTION, "MetaEditor");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (MainFrame.getInstance().getModel().getActiveDatabase() != null) {
			new MetaEditorFrame(MainFrame.getInstance().getModel().getActiveDatabase().getPath()).setVisible(true);
		}
	}

}
