package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sun.javafx.scene.BoundsAccessor;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import model.Database;
import model.Entity;
import model.EntityListModel;
import model.NodeTreeModel;
import model.Relation;
import model.RelationListModel;
import model.Workspace;

public class MetaEditorFrame extends JFrame implements Observable{
	
	
	Database db;

	private Object activeObject;
	private EntityListModel entityList;
	private RelationListModel relationList;
	private JTextField dbNameTextField;
	
	private JButton addEntityButton;
	private JButton delEntityButton;
	
	JButton addRelationButton;
	JButton delRelationButton;
	
	JList<Entity> entityJList;
	JList<Relation> relationJList;
	

	JPanel panelRigth = new JPanel();

	
	public MetaEditorFrame(String path){
		super();
		
		db = new Database();
		try {
			db.loadBase(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entityList = new EntityListModel((ArrayList<Entity>)db.getEntities());
		relationList = new RelationListModel(db.getRelations());
		initialiseGui();
	}

	private void initialiseGui() {
		setSize(800, 600);
		setTitle("MetaEditor");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		entityJList = new JList<>(entityList);
		relationJList = new JList<>(relationList);
		
		entityJList.setBorder( new EmptyBorder(5, 5, 5, 5));
		relationJList.setBorder( new EmptyBorder(5, 5, 5, 5));

		//MyMenuBar myMenuBar = new MyMenuBar();
		//this.setJMenuBar(myMenuBar);

		//MyToolbar toolbar = new MyToolbar();
		//this.add(toolbar, BorderLayout.NORTH);
		
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));
		
		JLabel dbNameLabel = new JLabel("Ime baze: ");
		dbNameTextField = new JTextField(db.getName());
		
		dbNameTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String text = dbNameLabel.getText();
				db.setName(text);
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});


		panelTop.setBorder(new EmptyBorder(10,20, 10, 20));
		
		panelTop.add(dbNameLabel);
		panelTop.add(dbNameTextField);
		
		this.add(panelTop, BorderLayout.NORTH);
	

		addEntityButton = new JButton("Dodaj");
		addEntityButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			 entityList.addElement(new Entity());
				
			}
		});
		delEntityButton = new JButton("Brisi");
		delEntityButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(activeObject != null && activeObject instanceof Entity)
					entityList.removeElement(activeObject);
					activeObject = null;
					delEntityButton.setEnabled(false);
				
			}
		});
		MetaListPanel mlpEntities = new MetaListPanel(delEntityButton, addEntityButton);
		mlpEntities.setScrollPanel(new JScrollPane(entityJList));
		
		entityJList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(entityJList.getSelectedValue()!= null)
				changeAcitveObject(entityJList.getSelectedValue());
				
				
			}
		});
		
		relationJList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
					if(relationJList.getSelectedValue() != null)
					changeAcitveObject(relationJList.getSelectedValue());
				
				
			}
		});
		
		addRelationButton = new JButton("Dodaj");
		addRelationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				relationList.addElement(new Relation());
				
			}
		});
		delRelationButton = new JButton("Brisi");
		delRelationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(activeObject != null && activeObject instanceof Relation) {
					relationList.removeElement(activeObject);
					activeObject = null;
					delRelationButton.setEnabled(false);
				}
				
			}
		});
		MetaListPanel mlpRelations = new MetaListPanel(delRelationButton, addRelationButton);
		mlpRelations.setScrollPanel(new JScrollPane(relationJList));
		
		
		JSplitPane panelSplitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT,mlpEntities,mlpRelations);
		panelSplitLeft.setResizeWeight(0.5);
	

		panelRigth = new JPanel(new BorderLayout());


		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelSplitLeft, panelRigth);
		splitPane.setResizeWeight(0.15);

		this.add(splitPane, BorderLayout.CENTER);
		
		
		//Bottom
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.X_AXIS));
		panelBottom.setBorder(new EmptyBorder(10,10, 10, 10));
		
		JButton metaCloseButton = new JButton("Odustani");
		JButton metaSaveButton = new JButton("Sacuvaj");
		
		metaSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				db.writeMeta(new File("res/editovana_meta.json"));
			}
		});
		metaCloseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog (null, "Da li ste sigurni da zelite da izadjete","alert" ,JOptionPane.YES_NO_CANCEL_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					setVisible(false); 
					dispose(); 
				
					
				}
				
			}
		});
		
		panelBottom.add(Box.createHorizontalGlue());
		panelBottom.add(metaCloseButton);
		panelBottom.add(Box.createHorizontalStrut(10));
		panelBottom.add(metaSaveButton);
		
		this.add(panelBottom, BorderLayout.SOUTH);

	}

	protected void changeAcitveObject(Object selectedValue) {
		activeObject = selectedValue;
		//dbNameTextField.setText(activeObject.toString());
		if(selectedValue instanceof Entity) {
			relationJList.clearSelection();
			delRelationButton.setEnabled(false);
			delEntityButton.setEnabled(true);
			prikazi();
			
		}
		else if(selectedValue instanceof Relation) {
			entityJList.clearSelection();
			delRelationButton.setEnabled(true);
			delEntityButton.setEnabled(false);
			prikazi();
		}
		
	}

	private void prikazi() {
		if(activeObject == null)
			return;
		
		panelRigth.removeAll();
		if(activeObject instanceof Relation) {
			RelationView rv =new RelationView(db, (Relation) activeObject);
			panelRigth.add(rv,BorderLayout.CENTER);
			revalidate();
		}
		else if(activeObject instanceof Entity) {
			panelRigth.add(new EntityView((Entity) activeObject),BorderLayout.CENTER);
			revalidate();
		}
			
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}
}
