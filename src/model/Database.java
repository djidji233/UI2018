package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.file.INDFile;
import model.file.SEKFile;
import model.file.SERFile;

public class Database {

	private String name;
	private String type; // ovo ne treba
	private List<Entity> entities;
	private List<Relation> relations;
	private String path;
	private InfResources infResources;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public Database(String name,InfResources infResources) {
		this.name = name;
		entities = new ArrayList<>();
		relations = new ArrayList<>();
		this.infResources = infResources;
	}

	public InfResources getInfResources() {
		return infResources;
	}

	public void setInfResources(InfResources infResources) {
		this.infResources = infResources;
	}

	public Database() {
		entities = new ArrayList<>();
		relations = new ArrayList<>();
	}

	public void loadBase(String path) throws IOException {
		BufferedReader bReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("res/meta.json"))));
		JSONTokener tokener = new JSONTokener(bReader);
		JSONObject obj = new JSONObject(tokener);
		bReader.close();

		JSONObject jScheme = new JSONObject(new JSONTokener(new FileInputStream(new File("res/meta_meta.json"))));
		Schema schema = SchemaLoader.load(jScheme);
		schema.validate(obj);
		loadBase(obj);
	}

	public void loadBase(JSONObject obj) {
		entities = new ArrayList<>();
		relations = new ArrayList<>();
		this.name = obj.getJSONObject("db").getString("name");
		this.type = obj.getJSONObject("db").getString("type");
		JSONArray jEntityArray = obj.getJSONObject("db").getJSONArray("entiteti");

		for (int i = 0; i < jEntityArray.length(); i++) {
			JSONObject jObject = (JSONObject) jEntityArray.get(i);
			String type =jObject.getString("type");
			String entityName = jObject.getString("name");
			Entity entity = null;
			switch(type) {
			case "ind":
				entity = new INDFile(entityName,type,this,path);
			case "sek":
				entity = new SEKFile(entityName,type,this,path);
			case "ser":
				entity =  new SERFile(entityName,type,this,path);
				
			}
			
			this.entities.add(entity);
			JSONArray jAttributeArray = jObject.getJSONArray("attributes");

			for (int j = 0; j < jAttributeArray.length(); j++) {
				JSONObject jAttObj = (JSONObject) jAttributeArray.get(j);
				AttributeType att = findAttributeType(jAttObj.getString("dt"));
				boolean b1 = jAttObj.getBoolean("mend");
				boolean b2 = jAttObj.getBoolean("pk");
				Attribute attribute = new Attribute(jAttObj.getString("key"), att, b1, b2, jAttObj.get("def"),
						jAttObj.getInt("len"));
				entity.addAtribute(attribute);
			}

		}
		JSONArray jRelationArray = obj.getJSONObject("db").getJSONArray("rel");
		for (int i = 0; i < jRelationArray.length(); i++) {
			JSONObject jObject = (JSONObject) jRelationArray.get(i);
			Entity entity1 = findEntity(jObject.getString("source_table"));
			Entity entity2 = findEntity(jObject.getString("target_table"));
			List<Attribute> atribs1 = findAttribute(jObject.getJSONArray("source_keys"), entity1);
			List<Attribute> atribs2 = findAttribute(jObject.getJSONArray("target_keys"), entity2);
			Relation relation = new Relation(jObject.getString("name"), entity2, entity1, atribs2, atribs1);
			this.relations.add(relation);
		}
	}

	public void addEntity(Entity entry) {
		this.entities.add(entry);
	}

	public void addRelation(Relation relation) {
		this.relations.add(relation);
	}

	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	public String getName() {
		return name;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	private Entity findEntity(String string) {
		if (entities == null || entities.size() == 0)
			return null;

		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).toString().equals(string))
				return entities.get(i);
		}
		return null;
	}

	private AttributeType findAttributeType(String string) {
		if (string.equals("TYPE_NUMERIC"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("TYPE_CHAR"))
			return AttributeType.TYPE_CHAR;
		if (string.equals("TYPE_DATETIME"))
			return AttributeType.TYPE_DATETIME;
		if (string.equals("TYPE_VARCHAR"))
			return AttributeType.TYPE_VARCHAR;
		else
			return null;
	}

	private List<Attribute> findAttribute(JSONArray jArray, Entity entity) {
		List<Attribute> list = new ArrayList<>();
		List<Attribute> attr = entity.getAttributes();
		for (int i = 0; i < attr.size(); i++) {
			for (int j = 0; j < jArray.length(); j++) {
				if (attr.get(i).getName().equals(jArray.get(j))) {
					list.add(attr.get(i));
				}
			}
		}
		return list;
	}

	public String getType() {
		return type;
	}

	public void writeMeta(File file) {
		Database database = this;
		// file = new File("res/writeFile.json");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			JSONObject jsonobject1 = new JSONObject();
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArrayEntity = new JSONArray();
			jsonObject.put("name", database.getName());
			jsonObject.put("type", database.getType());
			for (int i = 0; i < database.getEntities().size(); i++) {
				Entity entity = database.getEntities().get(i);
				JSONObject jObjEnt = new JSONObject();
				jObjEnt.put("name", entity.getName());
				JSONArray jArray = new JSONArray();
				JSONObject jA = new JSONObject();
				for (int j = 0; j < entity.getAttributes().size(); j++) {
					JSONObject jObjAt = new JSONObject();
					jObjAt.put("key", entity.getAttributes().get(j).getName());
					jObjAt.put("dt", entity.getAttributes().get(j).getType());
					jObjAt.put("mend", entity.getAttributes().get(j).isMandatory());
					jObjAt.put("pk", entity.getAttributes().get(j).isPrimaryKey());
					jObjAt.put("def", entity.getAttributes().get(j).getDefValue());
					jObjAt.put("len", entity.getAttributes().get(j).getLength());
					jArray.put(jObjAt);
				}
				jA.put("name", entity.getName());
				jA.put("attributes", jArray);
				jsonArrayEntity.put(jA);
				jsonObject.put("entiteti", jsonArrayEntity);
			}
			JSONArray jArRel = new JSONArray();
			for (int i = 0; i < database.getRelations().size(); i++) {
				JSONObject jR = new JSONObject();
				JSONArray jSK = new JSONArray();
				JSONArray jTK = new JSONArray();
				jR.put("name", database.getRelations().get(i).getName());
				jR.put("source_table", database.getRelations().get(i).getSourceEntity());
				jR.put("target_table", database.getRelations().get(i).getTargetEntity());
				for (int j = 0; j < database.getRelations().get(i).getSourceKeys().size(); j++) {
					jSK.put(database.getRelations().get(i).getSourceKeys().get(j).getName());
				}
				jR.put("source_keys", jSK);
				for (int j = 0; j < database.getRelations().get(i).getTargetKeys().size(); j++) {
					jTK.put(database.getRelations().get(i).getTargetKeys().get(j).getName());
				}
				jR.put("target_keys", jTK);
				jArRel.put(jR);
			}
			jsonObject.put("rel", jArRel);
			jsonobject1.put("db", jsonObject);
			JSONObject jScheme = new JSONObject(new JSONTokener(new FileInputStream(new File("res/meta_meta.json"))));
			Schema schema = SchemaLoader.load(jScheme);
			try {
				schema.validate(jsonobject1);
			} catch (ValidationException e1) {
				System.out.println("Everit validaciju nije prosao");
				return;
			}
			jsonobject1.write(pw);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
