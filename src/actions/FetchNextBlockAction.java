package actions;

import java.awt.event.ActionEvent;
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

public class FetchNextBlockAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FetchNextBlockAction() {
		putValue(SMALL_ICON, MyController.getInstance().malaIkonica("next"));
		putValue(NAME, "Fetch next block");
		putValue(SHORT_DESCRIPTION, "Fetch next block");
		// putValue(MNEMONIC_KEY, KeyEvent.VK_D);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("FETCH NEXT BLOCK");
		MainTab t = (MainTab) MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent();
		Entity tmpE = (Entity) t.getEntity();
		
		if(t.getToolbar().getJtfVelicinaBloka().getText().trim().equals("")) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Please enter the block size", "Error", 0);
			return;
		}

		AbstractFile absF = (AbstractFile) t.getEntity();
		
		int tmpSize = Integer.parseInt(t.getToolbar().getJtfVelicinaBloka().getText().trim());
		
		if(tmpSize > 0)
			absF.setBLOCK_SIZE(tmpSize);
		else
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Block size cannot be 0", "Error", 0);

		long pointer = absF.getFILE_POINTER();
		//System.out.println("stari fp: " + pointer);

		if (pointer + absF.getBLOCK_SIZE() * absF.getRECORD_SIZE() < absF.getFILE_SIZE()) {
			try {
				absF.fetchBlock(pointer);
				//System.out.println("fetching next...");
				//System.out.println("novi fp: " + absF.getFILE_POINTER());
			} catch (IOException | SQLException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, e2.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		} else {
			long size = ((absF.getFILE_SIZE() - absF.getFILE_POINTER()) / absF.getRECORD_SIZE());
			System.out.println(size);
			/*if (size > 0) {
				if (pointer + size * absF.getRECORD_SIZE() < absF.getFILE_SIZE()) {
					int block = absF.getBLOCK_SIZE();
					absF.setBLOCK_SIZE((int) size+1);
					try {
						absF.fetchBlock(pointer);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.out.println("Block " + block);
					absF.setBLOCK_SIZE(block);
					System.out.println("fetching next...");
					System.out.println("novi fp: " + absF.getFILE_POINTER());
				}
			} else*/
				JOptionPane.showMessageDialog(MainFrame.getInstance(), "File pointer > File size. Block size needed: "+size, "Error", 0);
					
		}

		t.getTabela().setModel(new TableModel(tmpE, ((AbstractFile)tmpE).getData()));
	}
}
