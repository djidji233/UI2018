package view;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class MyToolbar extends JToolBar {

	public MyToolbar() {
		super(SwingConstants.HORIZONTAL);

		setupToolBar();
	}

	public void setupToolBar() {

		this.add(MainFrame.getInstance().getActionManager().getLoadMetaSchemaAction());

		this.addSeparator();

		this.add(MainFrame.getInstance().getActionManager().getWriteMetaSchemaAction());

		this.addSeparator();

		this.add(MainFrame.getInstance().getActionManager().getCloseDatabaseAction());

		// ------------------------------------------------------------------

		this.addSeparator();

		// ------------------------------------------------------------------

		
		JButton btnMetaEditor = new JButton(MainFrame.getInstance().getActionManager().getMetaEditorAction());
		btnMetaEditor.setHideActionText(true);

		this.add(btnMetaEditor);

		this.addSeparator();

	}

}
