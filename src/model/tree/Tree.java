package model.tree;

import java.io.Serializable;

public class Tree implements Serializable {

	private static final long serialVersionUID = 6342356072961982683L;

	private Node rootElement;

	public Tree() {
		super();
	}

	public Node getRootElement() {
		return this.rootElement;
	}

	public void setRootElement(Node rootElement) {
		this.rootElement = rootElement;
	}
}
