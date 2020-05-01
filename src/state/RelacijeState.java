package state;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.Attribute;
import model.Entry;
import view.MainFrame;
import view.MainTab;
import view.RelationViewPanel;

public class RelacijeState implements IState {

	public RelacijeState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JPanel bottomPanel() {

		MainTab tab = (MainTab) (MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent());

		JPanel bpanel = null;

		// System.out.println("TYPE: "+tab.getEntity().getType().toLowerCase());

		if (tab != null && tab.getEntity().getType().toLowerCase().equals("sek")) {

			Entry slog = tab.getSelectedEntry();

			if (slog != null) {
				RelationViewPanel relationPanel = new RelationViewPanel(slog);
				bpanel = new JPanel(new BorderLayout());
				bpanel.add(relationPanel, BorderLayout.CENTER);
			}

		}

		if (tab != null && tab.getEntity().getType().toLowerCase().equals("sql")) {

			Entry slog = tab.getSelectedEntry();
			if (slog != null) {
				RelationViewPanel relationPanel = new RelationViewPanel(slog);
				bpanel = new JPanel(new BorderLayout());
				bpanel.add(relationPanel, BorderLayout.CENTER);
			}

		}

		System.out.println("Relacije State");
		return bpanel;
	}

}
