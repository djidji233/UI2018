package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import controller.MyController;
import model.Entity;
import model.TableModel;
import model.file.AbstractFile;
import view.MainFrame;
import view.MainTab;
import view.Tab;

public class FetchPreviousBlockAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FetchPreviousBlockAction() {
		putValue(SMALL_ICON, MyController.getInstance().malaIkonica("previous"));
		putValue(NAME, "Fetch previous block");
		putValue(SHORT_DESCRIPTION, "Fetch previous block");
		// putValue(MNEMONIC_KEY, KeyEvent.VK_D);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("FETCH PREVIOUS BLOCK");

		MainTab t = (MainTab) MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent();
		Entity tmpE = (Entity) t.getEntity();
		
		if(t.getToolbar().getJtfVelicinaBloka().getText().trim().equals("")) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Please enter the block size", "Error", 0);
			return;
		}

		AbstractFile absF = (AbstractFile)(t.getEntity());
		int tmpSize = Integer.parseInt(t.getToolbar().getJtfVelicinaBloka().getText().trim());

		long pointer = absF.getFILE_POINTER();
		pointer -= absF.getBLOCK_SIZE() * absF.getRECORD_SIZE();

		if (tmpSize > 0) {
			pointer -= tmpSize * absF.getRECORD_SIZE();
			absF.setBLOCK_SIZE(tmpSize);
		} else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Block size cannot be 0", "Error", 0);

		if (pointer >= 0) {
			try {
				absF.fetchBlock(pointer);
			} catch (IOException | SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		} else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "file pointer < 0", "Error", 0);
		t.getTabela().setModel(new TableModel(tmpE, ((AbstractFile)tmpE).getData()));
	}

}
