package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.MyAbout;

public class AboutAction extends AbstractAction {

	public AboutAction() {
		putValue(NAME, "More information");
		putValue(SHORT_DESCRIPTION, "More information");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
	}

}