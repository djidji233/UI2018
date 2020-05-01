package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import controller.MyController;
import view.MainFrame;
import view.MainTab;
import view.SortFrame;

public class SortAction extends AbstractAction {
	public SortAction() {
		//putValue(SMALL_ICON, MyController.getInstance().malaIkonica("sort"));
		putValue(NAME, "Sort");
		putValue(SHORT_DESCRIPTION, "Sort");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainTab tab = (MainTab) MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent();
		SortFrame sf = new SortFrame(tab.getEntity());
		sf.setVisible(true);
	}
}
