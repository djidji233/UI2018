package view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.MouseListenTab;
import model.Entity;
import model.NodeMeta;
import model.TableModel;

public class DesnoGore extends JPanel {
	private JTabbedPane tabPane;

	public DesnoGore() {
		
		setLayout(new BorderLayout());
		tabPane = new JTabbedPane();
		tabPane.addMouseListener(new MouseListenTab());
		
		
	
		add(tabPane,BorderLayout.CENTER);
		

		
	}
	
	public void dodajTab(Entity e,NodeMeta node) {
		boolean ok = true;
		for(int i=0; i<tabPane.getTabCount(); i++) {
			Entity tmp = ((MainTab) tabPane.getComponentAt(i)).getEntity();
			if(e.equals(tmp)) {
				ok = false;
				break;
			}
		}
		if(ok) {
			// dodace + selektovati
			MainTab t = new MainTab(e);
			e.addObserver(t);
			tabPane.addTab(e.getName(), t);
			int a = MainFrame.getInstance().getPanelTop().nadjiIndex(e);
			MainFrame.getInstance().getPanelTop().getTabPane().setSelectedIndex(a);
		} else {
			// samo selektovati
			int a = MainFrame.getInstance().getPanelTop().nadjiIndex(e);
			MainFrame.getInstance().getPanelTop().getTabPane().setSelectedIndex(a);
		}
	}
	
	public int nadjiIndex(Entity p) {
		int a=-1;
		for(int i=0; i<tabPane.getTabCount(); i++) {
			Entity tmp = ((MainTab) tabPane.getComponentAt(i)).getEntity();
			if(p==tmp) {
				a = i;
				break;
			}
		}
		if(a==-1)
			System.out.println("(DesnoGore.nadjiIndex) a je -1");
		return a;
	}
	public JTabbedPane getTabPane() {
		return tabPane;
	}
}
