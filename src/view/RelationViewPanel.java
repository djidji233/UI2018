package view;

import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;

import model.Attribute;
import model.Entity;
import model.Entry;
import model.Relation;

public class RelationViewPanel extends JTabbedPane{
	
	private Entry slog;
	private Entity entitet;
	
	public RelationViewPanel(Entry slog) {
		super();
		this.slog = slog;
		entitet = slog.getEntitet();
		
		
		for(Relation r: entitet.getDatabase().getRelations()) {
			
			if(r.getTargetEntity() == entitet) {
				addTab(r.getSourceEntity().getName(),new RelationTab(r.getSourceEntity(),r.getSourceKeys(),r.getTargetKeys(),slog));
			}
			else if(r.getSourceEntity() == entitet) {
				addTab(r.getTargetEntity().getName(),new RelationTab(r.getTargetEntity(),r.getTargetKeys(),r.getSourceKeys(),slog));
			}
		}
		
		
	}
	
	

}
