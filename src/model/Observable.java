package model;

public interface Observable {
	
	public void addObserver(Observer o);
	public void deleteObservers();
	public void deleteObserver(Observer o);
	public void notifyObservers();

}
