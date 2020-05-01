package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import model.Entity;
import model.NodeMeta;
import model.TableModel;
import model.file.AbstractFile;

public class Tab extends JPanel {
	private Entity entity;
	private JTable tabela;

	public Tab(Entity e, NodeMeta node) {
		this.entity = e;
		setLayout(new BorderLayout());
		tabela = new JTable(new TableModel(e, ((AbstractFile)e).getData()));
		tabela.setPreferredSize(MainFrame.getInstance().getPanelTop().getSize());
		tabela.setPreferredScrollableViewportSize(new Dimension(500, 250));
		add(new JScrollPane(tabela), BorderLayout.NORTH);
	}

	public Tab(Entity e) {
		this.entity = e;
		setLayout(new BorderLayout());
		tabela = new JTable(new TableModel(e, ((AbstractFile)e).getData()));

		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		add(new JScrollPane(tabela),BorderLayout.CENTER);

		tabela.setPreferredSize(MainFrame.getInstance().getPanelTop().getSize());
		tabela.setPreferredScrollableViewportSize(new Dimension(500, 250));
		add(new JScrollPane(tabela), BorderLayout.NORTH );
	}

	public Entity getEntity() {
		return entity;
	}


	public JTable getTabela() {
		return tabela;
	}

	public void setFindTab(String[][] data) {
		tabela = new JTable(new TableModel(entity, data));
		tabela.setPreferredSize(MainFrame.getInstance().getPanelTop().getSize());
		tabela.setPreferredScrollableViewportSize(new Dimension(500, 250));
		this.removeAll();
		add(new JScrollPane(tabela), BorderLayout.NORTH);
		this.revalidate();
		this.repaint();
	}
}
