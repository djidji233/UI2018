package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import javafx.event.ActionEvent;
import model.Attribute;
import model.Entity;
import model.Entry;
import model.Observer;
import model.TableModel;
import model.file.AbstractFile;
import model.file.INDFile;
import model.file.IUDBFile;
import model.tree.IndSekTreeModel;
import model.tree.Tree;
import state.DodavanjeState;
import state.IState;
import state.IzmenaState;
import state.PretragaState;
import state.StateManager;

public class MainTab extends JPanel implements Observer {

	public Entity getEntity() {
		return entity;
	}

	public JTable getTabela() {
		return tabela;
	}

	public TableToolbar getToolbar() {
		return toolbar;
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	private Entity entity;

	private JTable tabela;
	private TableToolbar toolbar;
	private StateManager stateManager;

	public MainTab(Entity entity) {
		super();

		this.entity = entity;

		setLayout(new BorderLayout());
		tabela = new JTable(new TableModel(entity.getAttributes(), ((AbstractFile) entity).getData()));
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel selectionModel = tabela.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				setBottom();

			}
		});

		stateManager = new StateManager();
		stateManager.setCurrentRelacijeState();

		toolbar = new TableToolbar(this);
		toolbar.setState(stateManager.getCurrent());

		setBorder(new EmptyBorder(5, 5, 5, 5));

		add(toolbar, BorderLayout.NORTH);

		if (entity.getType().equals("ind")) {

			String filename = ((INDFile) entity).getPath() + "/" + entity.getName() + ".tree";

			FileInputStream file;
			try {
				file = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(file);
				entity.setStablo((Tree) in.readObject());

				Tree ist = entity.getStablo();
				JTree jtree = new JTree(new IndSekTreeModel(ist));
				jtree.setCellRenderer(new IndSekTreeCellRenderer());
				in.close();
				JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jtree),
						new JScrollPane(tabela));
				this.add(splitPane1, BorderLayout.CENTER);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		} else {
			add(new JScrollPane(tabela), BorderLayout.CENTER);
		}

		// add(new JScrollPane(tabela), BorderLayout.CENTER);

	}

	public void refreshTable() {
		AbstractFile absF = (AbstractFile) entity;
		if (absF.getFILE_POINTER() + absF.getBLOCK_SIZE() * absF.getRECORD_SIZE() < absF.getFILE_SIZE()) {
			MainFrame.getInstance().getActionManager().getFetchNextBlockAction().actionPerformed(null);
			MainFrame.getInstance().getActionManager().getFetchPreviousBlockAction().actionPerformed(null);
		} else {
			MainFrame.getInstance().getActionManager().getFetchPreviousBlockAction().actionPerformed(null);
			MainFrame.getInstance().getActionManager().getFetchNextBlockAction().actionPerformed(null);
		}
		this.revalidate();
	}

	public void setData(String[][] data) {
		// tabela = new JTable(new TableModel(entity, data));
		tabela.setModel(new TableModel(entity.getAttributes(), data));
		this.revalidate();
		this.repaint();
	}

	public void setBottom() {
		JPanel panel = getStateManager().getCurrent().bottomPanel();
		MainFrame.getInstance().getPanelBot().removeAll();
		if (panel != null) {
			MainFrame.getInstance().getPanelBot().add(panel, BorderLayout.CENTER);
		}
		MainFrame.getInstance().getPanelBot().revalidate();
		MainFrame.getInstance().getPanelBot().repaint();
	}

	public Entry getSelectedEntry() {

		int row = getTabela().getSelectedRow();

		if (row != -1) {
			Entry slog = new Entry(getEntity());
			List<Attribute> atributi = getEntity().getAttributes();

			int n = getEntity().getAttributes().size();
			// System.out.printf("n=%d row=%d\n", n, row);
			for (int i = 0; i < n; i++) {
				String s = getTabela().getModel().getValueAt(row, i).toString();
				Map<Attribute, String> fields = slog.getFields();
				fields.put(atributi.get(i), s);

			}
			return slog;
		}
		return null;

	}

	@Override
	public void update() {

		if (entity.getLast_exeption() != null) {
			JOptionPane.showMessageDialog(null, entity.getLast_exeption().getMessage());
			entity.setLast_exeption(null);
			return;

		}
		
		
		if (entity instanceof IUDBFile && stateManager.getCurrent() instanceof PretragaState) {

			IUDBFile file = (IUDBFile) entity;
			
				tabela.setModel(new model.TableModel(file.getAtributiZaIspis(), ((AbstractFile) entity).getData()));
			
			 
		} else
			tabela.setModel(new model.TableModel(entity.getAttributes(), ((AbstractFile) entity).getData()));
		
		if (entity instanceof IUDBFile  && stateManager.getCurrent() instanceof DodavanjeState) {
			//tabela.setModel(new model.TableModel(entity.getAttributes(), ((AbstractFile) entity).getData()));
			IUDBFile file = (IUDBFile) entity;
			if(file.getLastEdded() !=null)
			selektuj(file.getLastEdded());
		}


		System.out.println("stigap do repaint");
		
	
		this.revalidate();
		this.repaint();
	
		setBottom();
	}

	public void selektuj(Entry res) {

		int pos = -1;
		String[][] data = ((AbstractFile) entity).getData();

		List<Attribute> atributi = entity.getAttributes();
		int n = atributi.size();
		
		System.out.println("datalen: "+data.length);
		System.out.println(n);

		for (int j = 0; j < data.length; j++) {
			pos = j;
			for (int i = 0; i < n; i++) {
				if (atributi.get(i).isPrimaryKey()) {
					System.out.println(data[j][i].trim() + " " + res.getFields().get(atributi.get(i)));
					if (!data[j][i].trim().equalsIgnoreCase(res.getFields().get(atributi.get(i)))) {
						pos = -1;
					}
				} else
					break;

			}
			if (pos != -1)
				break;
		}

		if (pos != -1) {
			getTabela().addRowSelectionInterval(pos, pos);
		}
	}

}
