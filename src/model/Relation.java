package model;

import java.util.ArrayList;
import java.util.List;

public class Relation {

	private String name;
	private Entity targetEntity;
	private Entity sourceEntity;

	private List<Attribute> targetKeys;
	private List<Attribute> sourceKeys; // moraju biti primarni kljucevi tabele SourceEntity

	public Relation(String name, Entity targetEntity, Entity sourceEntity, List<Attribute> targetKeys,
			List<Attribute> sourceKeys) {
		super();
		this.name = name;
		this.targetEntity = targetEntity;
		this.sourceEntity = sourceEntity;
		this.targetKeys = targetKeys;
		this.sourceKeys = sourceKeys;
	}

	public Relation(String name, Entity targetEntity, Entity sourceEntity) {
		this.name = name;
		this.targetEntity = targetEntity;
		this.sourceEntity = sourceEntity;
		targetKeys = new ArrayList<>();
		sourceKeys = new ArrayList<>();

	}

	public Relation() {
		super();
		this.name = "newRelation";
		this.targetEntity = null;
		this.sourceEntity = null;
		targetKeys = new ArrayList<>();
		sourceKeys = new ArrayList<>();

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTargetKeys(List<Attribute> targetKeys) {
		this.targetKeys = targetKeys;
	}

	public void setSourceKeys(List<Attribute> sourceKeys) {
		this.sourceKeys = sourceKeys;
	}

	public void setTargetEntity(Entity targetEntity) {
		this.targetEntity = targetEntity;
	}

	public void setSourceEntity(Entity sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	@Override
	public String toString() {
		return name;
	}

	public void addTargetKeys(Attribute key) {
		this.targetKeys.add(key);
	}

	public void addSourceKeys(Attribute key) {
		this.sourceKeys.add(key);
	}

	public String getName() {
		return name;
	}

	public Entity getTargetEntity() {
		return targetEntity;
	}

	public Entity getSourceEntity() {
		return sourceEntity;
	}

	public List<Attribute> getTargetKeys() {
		return targetKeys;
	}

	public List<Attribute> getSourceKeys() {
		return sourceKeys;
	}

}
