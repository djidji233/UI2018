package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

public class RelationListModel extends DefaultListModel<Relation>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Relation> elements;
	
	public RelationListModel(List<Relation> list) {

		this.elements = list;

	}

	public void addElement(Relation element) {
		int s = elements.size();
		elements.add(element);
		fireIntervalAdded(this, s, s);
	}

	public boolean removeElement(Object element) {
		int index;
		index = elements.indexOf(element);
		if (index != -1) {
			elements.remove(index);
			fireIntervalRemoved(this, index, index);
			return true;
		}
		return false;
	}

	@Override
	public Relation getElementAt(int index) {
		return (elements.get(index));

	}

	@Override
	public int getSize() {
		return elements.size();
	}
	
}
