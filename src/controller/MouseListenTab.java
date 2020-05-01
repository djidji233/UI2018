package controller;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.Observable;
import model.Observer;
import view.DesnoGore;
import view.MainFrame;
import view.MainTab;
import view.Tab;

public class MouseListenTab implements MouseListener{


	@Override
	public void mouseClicked(MouseEvent e) {

		MainTab t = (MainTab) MainFrame.getInstance().getPanelTop().getTabPane().getSelectedComponent();
		MainFrame.getInstance().getPanelBot().removeAll();
		MainFrame.getInstance().getPanelBot().revalidate();
		MainFrame.getInstance().getPanelBot().repaint();
	    t.setBottom();
	    	   
	
		
		//MainFrame.getInstance().getPanelBot().dodajTabove(t.getEntity());

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
