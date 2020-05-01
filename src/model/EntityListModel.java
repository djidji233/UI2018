package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class EntityListModel extends AbstractListModel<Entity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<Entity> elements;

	public EntityListModel(ArrayList<Entity> list) {

		this.elements = list;

	}

	public void addElement(Entity element) {
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
	public Entity getElementAt(int index) {
		return (elements.get(index));

	}

	@Override
	public int getSize() {
		return elements.size();
	}

}
