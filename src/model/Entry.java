package model;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entry {


	private Entity entitet;
	private Map<Attribute, String> fields;
	
	
	
	
	public Entry(Entity entitet) {
		super();
		this.entitet = entitet;
		fields = new HashMap<>();
		
	}



	public Entity getEntitet() {
		return entitet;
	}



	public Map<Attribute, String> getFields() {
		return fields;
	}
	
	
	
	
	
}
