package view;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.naming.ldap.PagedResultsControl;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Attribute;
import model.Entity;
import model.Entry;

public class DetailsPanel extends JPanel{

	

	Entity entitet;
	List<EntryPart> parts;
	
	boolean pkOnly;
	
	
	public DetailsPanel(Entry slog) {
		
	
		entitet = slog.getEntitet();
		int n = entitet.getAttributes().size();
		pkOnly = false;
		
		
		
		if(n > 4)
		{
			int h = n%2==0 ? n/2:n/2+1;
			setLayout(new GridLayout(h,2,5,5));
		}
		else
			setLayout(new GridLayout(n,1,5,5));
		
		
		parts = new ArrayList<>();
		
		for(int i=0;i<n;i++)
		{
			Attribute a =entitet.getAttributes().get(i);
			EntryPart part = new EntryPart(a, slog.getFields().get(a));
			
			parts.add(part);
			add(part);
			
		}
		
	}
	public DetailsPanel(Entry slog,boolean pkOnly) {
		
		entitet = slog.getEntitet();
		int n = entitet.getAttributes().size();
	
		if(n > 4)
		{
			int h = n%2==0 ? n/2:n/2+1;
			setLayout(new GridLayout(h,2,5,5));
		}
		else
			setLayout(new GridLayout(n,1,5,5));
		
		
		parts = new ArrayList<>();
		
		for(int i=0;i<n;i++)
		{
				Attribute a =entitet.getAttributes().get(i);
				
				if(a.isPrimaryKey()) {
				EntryPart part = new EntryPart(a, slog.getFields().get(a));
				
				parts.add(part);
				add(part);
			}
		}
			
	}
	public Entry getData() {
		
		Entry e = new Entry(entitet);
		
		for(int i=0;i<entitet.getAttributes().size();i++) {

					e.getFields().put(entitet.getAttributes().get(i), parts.get(i).getText());
		}
		return e;
	}
	public Entry getDataPk() {
		
		Entry e = new Entry(entitet);
		int j=0;
		for(int i=0;i<entitet.getAttributes().size();i++) {
			
			if(entitet.getAttributes().get(i).isPrimaryKey()) {
				if(parts.get(j).check())
				{
					e.getFields().put(entitet.getAttributes().get(i), parts.get(j).getText());
					j++;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Pogresno uneta vrednost za atribut "+entitet.getAttributes().get(i));
					return null;
				}
				
			}
			else
			{
				e.getFields().put(entitet.getAttributes().get(i), "");
			}
		}
		return e;
		
	}
	public void disable() {
		
		for(EntryPart part:parts)
			part.getTf().setEditable(false);
	}


	
}
