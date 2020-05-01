package state;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.Entry;
import view.AddRowPanel;
import view.ChangeRowPanel;
import view.MainFrame;
import view.MainTab;

public class IzmenaState implements IState{

	@Override
	public JPanel bottomPanel() {
		
		MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());
		if (tab != null && tab.getEntity().getType().toLowerCase().equals("sek")) {
			

			Entry slog = tab.getSelectedEntry();
			if(slog !=null) {
				ChangeRowPanel changePanel = new ChangeRowPanel(slog);
				JPanel bpanel = new JPanel(new BorderLayout());
				bpanel.add(changePanel, BorderLayout.CENTER);

				return bpanel;
			}
			
		}
		return null;
	}

}
