package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controller.MyController;
import model.Observable;
import model.Observer;
import model.TableModel;
import model.file.AbstractFile;
import model.file.IUDBFile;
import model.file.SEKFile;
import state.BrisanjeState;
import state.DodavanjeState;
import state.IState;
import state.IzmenaState;
import state.PretragaState;
import state.RelacijeState;
import state.StateManager;

public class TableToolbar extends JToolBar implements Observable {

	JButton btnDodaj;
	JButton btnObrisi;
	JButton btnIzmeni;
	JButton btnPretraga;
	JButton btnRelacije;

	ArrayList<Observer> observeri;

	JButton btnSort;
	JTextField jtfVelicinaBloka;
	MainTab tab;

	JButton btnSlBlok;
	JButton btnPreBlok;

	private StateManager stateManager;

	public TableToolbar(MainTab mainTab) {

		super();

		tab = mainTab;
		stateManager = mainTab.getStateManager();

		observeri = new ArrayList<>();
		addObserver(mainTab);

		btnDodaj = new JButton();
		btnDodaj.setIcon(MyController.getInstance().malaIkonica("add"));
		btnDodaj.setPreferredSize(new Dimension(30, 30));
		btnDodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainTab.getStateManager().setCurrentDodavanjeState();
				setState(stateManager.getCurrent());
				// mainTab.setBottom();
				notifyObservers();

			}
		});
		btnObrisi = new JButton();
		btnObrisi.setIcon(MyController.getInstance().malaIkonica("delete"));
		btnObrisi.setPreferredSize(new Dimension(30, 30));
		btnObrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainTab.getStateManager().setCurrentBrisanjeState();
				setState(stateManager.getCurrent());
				// mainTab.setBottom();
				notifyObservers();

			}
		});
		btnPretraga = new JButton();
		btnPretraga.setIcon(MyController.getInstance().malaIkonica("search"));
		btnPretraga.setPreferredSize(new Dimension(30, 30));
		btnPretraga.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainTab.getStateManager().setCurrentPretragaState();
				setState(stateManager.getCurrent());
				// mainTab.setBottom();
				notifyObservers();
			}
		});

		btnIzmeni = new JButton();
		btnIzmeni.setIcon(MyController.getInstance().malaIkonica("rename"));
		btnIzmeni.setPreferredSize(new Dimension(30, 30));
		btnIzmeni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainTab.getStateManager().setCurrentIzmenaState();
				setState(stateManager.getCurrent());
				// mainTab.setBottom();
				notifyObservers();
			}
		});

		btnRelacije = new JButton();
		btnRelacije.setIcon(MyController.getInstance().malaIkonica("relation"));
		btnRelacije.setPreferredSize(new Dimension(30, 30));
		btnRelacije.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainTab.getStateManager().setCurrentRelacijeState();
				setState(stateManager.getCurrent());
				// mainTab.setBottom();
				notifyObservers();
			}
		});

		jtfVelicinaBloka = new JTextField("20");

		btnPreBlok = new JButton(MainFrame.getInstance().getActionManager().getFetchPreviousBlockAction());
		btnPreBlok.setPreferredSize(new Dimension(30, 30));
		btnPreBlok.setHideActionText(true);

		btnSlBlok = new JButton(MainFrame.getInstance().getActionManager().getFetchNextBlockAction());
		btnSlBlok.setPreferredSize(new Dimension(30, 30));
		btnSlBlok.setHideActionText(true);

		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractFile af = (AbstractFile) mainTab.getEntity();
				af.setFILE_POINTER(af.getFILE_POINTER() - (af.getBLOCK_SIZE() * af.getRECORD_SIZE()));

				if (jtfVelicinaBloka.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(), "Please enter the block size", "Error", 0);
					return;
				}
				af.setBLOCK_SIZE(Integer.parseInt(jtfVelicinaBloka.getText().trim()));

				if (af.getFILE_POINTER() + af.getBLOCK_SIZE() * af.getRECORD_SIZE() < af.getFILE_SIZE()) {
					try {
						af.fetchNextBlock();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					mainTab.getTabela().setModel(
							new TableModel(mainTab.getEntity(), ((AbstractFile) (mainTab.getEntity())).getData()));
				} else {
					JOptionPane.showMessageDialog(MainFrame.getInstance(), "File pointer > File size.", "Error", 0);
				}
			}
		};

		jtfVelicinaBloka.addActionListener(action);

		this.add(btnDodaj);
		// this.addSeparator();
		this.add(btnObrisi);
		// this.addSeparator();
		this.add(btnIzmeni);
		// this.addSeparator();
		this.add(btnPretraga);
		// this.addSeparator();
		this.add(btnRelacije);

		if (tab.getEntity().getType().equals("sek")) {
			JButton btnTree = new JButton();
			btnTree.setIcon(MyController.getInstance().malaIkonica("maketree"));
			btnTree.setPreferredSize(new Dimension(30, 30));
			btnTree.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SEKFile sf = (SEKFile) tab.getEntity();
					try {
						sf.makeTree();
					} catch (IOException e1) {
						System.out.println("Make Tree fail.");
						e1.printStackTrace();
					}
				}
			});
			this.add(btnTree);

			btnSort = new JButton("S");
			// btnSort.setIcon(MyController.getInstance().malaIkonica("sort"));
			btnSort.setPreferredSize(new Dimension(30, 30));
			btnSort.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					MainFrame.getInstance().getActionManager().getSortAction().actionPerformed(null);
					;
				}
			});
			this.add(btnSort);
		}

		if (tab.getEntity().getType().equals("sql")) {
			JButton btnRefresh = new JButton();
			btnRefresh.setIcon(MyController.getInstance().malaIkonica("refrash"));
			btnRefresh.setPreferredSize(new Dimension(30, 30));
			btnRefresh.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						((IUDBFile) tab.getEntity()).fetchNextBlock();
					} catch (ClassNotFoundException | IOException | SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "UI Project", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			});
			this.add(btnRefresh);
		}

		if (tab.getEntity().getType().equals("sek")) {
			this.addSeparator(new Dimension(470, -1));
		} else
			this.addSeparator(new Dimension(500, -1));

		this.add(jtfVelicinaBloka);
		this.add(btnPreBlok);
		this.add(btnSlBlok);

		// TODO Auto-generated constructor stub
	}

	public void setState(IState state) {
		Component[] components = getComponents();
		for (Component c : components) {
			c.setEnabled(true);
		}
		if (state instanceof BrisanjeState)
			btnObrisi.setEnabled(false);
		else if (state instanceof DodavanjeState)
			btnDodaj.setEnabled(false);
		else if (state instanceof IzmenaState)
			btnIzmeni.setEnabled(false);
		else if (state instanceof PretragaState)
			btnPretraga.setEnabled(false);
		else if (state instanceof RelacijeState)
			btnRelacije.setEnabled(false);

	}

	public JTextField getJtfVelicinaBloka() {
		return jtfVelicinaBloka;
	}

	@Override
	public void addObserver(Observer o) {
		observeri.add(o);

	}

	@Override
	public void deleteObservers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObserver(Observer o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyObservers() {

		for (Observer o : observeri)
			o.update();

	}

}
