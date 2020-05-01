package view;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MyMenuBar extends JMenuBar {
	public MyMenuBar() {

		JMenu about = new JMenu("About");

		add(Box.createHorizontalGlue());
		add(about);
		about.add(MainFrame.getInstance().getActionManager().getAboutAction());
	}
}
