package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Attribute;
import model.Database;
import model.Entity;
import model.NodeMeta;
import model.file.AbstractFile;
import view.MainFrame;
import view.MainTab;

public class MouseListen implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			Object o = MainFrame.getInstance().getNodeTree().getLastSelectedPathComponent();
			if (o instanceof NodeMeta) {
				NodeMeta n = (NodeMeta) o;
				if (n.getData() instanceof Entity) {
					Entity ent = (Entity) n.getData();

					if (!ent.isTabOpen()) {
						try {
							((AbstractFile)ent).setBLOCK_SIZE(20);
							((AbstractFile)ent).fetchBlock(0);
						} catch (IOException | SQLException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						MainFrame.getInstance().getPanelTop().dodajTab(ent, n);
						MainFrame.getInstance().getPanelBot().removeAll();
						MainFrame.getInstance().getPanelBot().revalidate();
						MainFrame.getInstance().getPanelBot().repaint();
						ent.setTabOpen(true);
					} else {
						int a = MainFrame.getInstance().getPanelTop().nadjiIndex(ent);
						MainFrame.getInstance().getPanelTop().getTabPane().setSelectedIndex(a);
					}
				}

			}
		}
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
