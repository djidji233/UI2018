package model;

public class Attribute {

	private String name;
	private AttributeType type;
	private boolean primaryKey;
	private boolean mandatory;
	private Object defValue;
	private int length;

	public Attribute(String name, AttributeType type, boolean mandatory, boolean primaryKey, Object defValue,
			int length) {
		this.name = name;
		this.type = type;
		this.mandatory = mandatory;
		this.primaryKey = primaryKey;
		this.defValue = defValue;
		this.length = length;
	}

	public Attribute(String name, AttributeType type, boolean mandatory, boolean primaryKey, int length) {
		this.name = name;
		this.type = type;
		this.mandatory = mandatory;
		this.primaryKey = primaryKey;
		this.length = length;
	}

	public Attribute() {
		this.name = null;
		this.type = null;
		this.mandatory = false;
		this.primaryKey = false;
		this.defValue = null;
		this.length = 0;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public AttributeType getType() {
		return type;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public Object getDefValue() {
		return defValue;
	}

	public int getLength() {
		return length;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setDefValue(Object defValue) {
		this.defValue = defValue;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
