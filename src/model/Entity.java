package model;

import java.util.ArrayList;

import java.util.List;

import model.tree.Tree;

public class Entity implements Observable {

	protected String name;
	private List<Attribute> attributes;
	// private String treePath
	protected String type;
	// private AbstractFile file;
	private Database database;
	private List<IzmenaItem> izmene;
	private boolean isTabOpen;
	private List<Observer> observers;
	private Exception last_exeption;

	public Exception getLast_exeption() {
		return last_exeption;
	}

	public void setLast_exeption(Exception last_exeption) {
		this.last_exeption = last_exeption;
	}

	private Tree stablo;

	public Tree getStablo() {
		return stablo;
	}

	public void setStablo(Tree stablo) {
		this.stablo = stablo;
	}

	public Entity(String name, String tip, Database database) {
		this.name = name;
		this.type = tip;
		this.attributes = new ArrayList<>();
		this.database = database;
		this.isTabOpen = false;
		
		last_exeption = null;

		izmene = new ArrayList<>();
		
		observers = new ArrayList<>();

	}

	public List<IzmenaItem> getIzmene() {
		return izmene;
	}

	public Database getDatabase() {
		return database;
	}

	public Entity() {
		this.name = "newEntity";
		this.attributes = new ArrayList<>();
	}

	public void addAtribute(Attribute child) {
		this.attributes.add(child);
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTabOpen() {
		return isTabOpen;
	}

	public void setTabOpen(boolean isTabOpen) {
		this.isTabOpen = isTabOpen;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);

	}

	@Override
	public void deleteObservers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteObserver(Observer o) {
		observers.remove(o);

	}

	@Override
	public void notifyObservers() {

		for(Observer o:observers) {
			o.update();
		}

	}
}
