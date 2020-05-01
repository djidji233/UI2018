package model.tree;

import java.io.Serializable;

import model.AttributeType;

public class KeyElement implements Serializable {

	private static final long serialVersionUID = 3215531261892437543L;
	private String type;
	private Object value;

	public KeyElement(String type, Object value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String toString() {
		return value.toString();
	}
	
	public int compareTo(KeyElement k) {
		
		if(type.equals("TYPE_NUMERIC"))
			return Integer.parseInt(value.toString()) - Integer.parseInt(k.value.toString());
		
		return value.toString().compareTo(k.value.toString());
	}

}
