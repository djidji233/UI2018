package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.file.AbstractFile;
import model.file.INDFile;
import model.file.IUDBFile;
import model.file.SEKFile;
import model.file.SERFile;

public class InfResources {

	private List<Database> databases;
	private String type;
	private String name;

	private Connection conn = null;

	private String ip;
	private String username;
	private String dbname;
	private String password;

	public InfResources() {
		databases = new ArrayList<>();
	}

	public static List<InfResources> loadBase(JSONArray obj) {

		List<InfResources> resources = new ArrayList<>();
		for (int i = 0; i < obj.length(); i++) {
			JSONObject jObj = (JSONObject) obj.get(i);
			InfResources ir = new InfResources();
			ir.setName(jObj.getString("ir_name"));
			ir.setType(jObj.getString("type"));
			ir.setIp(jObj.getString("ip"));
			ir.setDbname(jObj.getString("dbname"));
			ir.setUsername(jObj.getString("usn"));
			ir.setPassword(jObj.getString("password"));
			resources.add(ir);
			JSONArray jPack = jObj.getJSONArray("packages");
			for (int j = 0; j < jPack.length(); j++) {
				JSONObject jObjPack = (JSONObject) jPack.get(j);
				Database database = new Database(jObjPack.getString("pack_name"), ir);
				ir.addDatabase(database);
				JSONArray jEntities = jObjPack.getJSONArray("entities");

				for (int k = 0; k < jEntities.length(); k++) {
					JSONObject jEntity = (JSONObject) jEntities.get(k);

					String type = jEntity.getString("type");
					String entityName = jEntity.getString("name");
					String path = jEntity.getString("path");
					Entity entity = null;

					switch (type) {
					case "ind":
						entity = new INDFile(entityName, type, database, path);
						break;
					case "sek":
						entity = new SEKFile(entityName, type, database, path);
						break;
					case "ser":
						entity = new SERFile(entityName, type, database, path);
						break;
					case "sql":
						entity = new IUDBFile(entityName, type, database, null);
						break;
					}
					database.addEntity(entity);

					JSONArray jAttr = jEntity.getJSONArray("attributes");
					for (int z = 0; z < jAttr.length(); z++) {
						JSONObject jAttrObj = (JSONObject) jAttr.get(z);
						AttributeType att = findAttributeType(jAttrObj.getString("dt"));
						boolean b1 = jAttrObj.getBoolean("mend");
						boolean b2 = jAttrObj.getBoolean("pk");
						Attribute attribute = new Attribute(jAttrObj.getString("key"), att, b1, b2,
								jAttrObj.getInt("len"));
						entity.addAtribute(attribute);
					}
					if (!type.equals("sql"))
						((AbstractFile) entity).updateRECORD_SIZE();
				}
				JSONArray jRelations = jObjPack.getJSONArray("relations");
				for (int k = 0; k < jRelations.length(); k++) {
					JSONObject jRel = (JSONObject) jRelations.getJSONObject(k);
					Entity entity1 = findEntity(database, jRel.getString("source_table"));
					Entity entity2 = findEntity(database, jRel.getString("target_table"));
					List<Attribute> atribs1 = findAttribute(jRel.getJSONArray("source_keys"), entity1);
					List<Attribute> atribs2 = findAttribute(jRel.getJSONArray("target_keys"), entity2);

					Relation relation = new Relation(jRel.getString("name"), entity2, entity1, atribs2, atribs1);
					database.addRelation(relation);
				}
			}
		}
		return resources;
	}

	private static AttributeType findAttributeType(String string) {
		if (string.equals("TYPE_NUMERIC"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("TYPE_CHAR"))
			return AttributeType.TYPE_CHAR;
		if (string.equals("TYPE_DATETIME"))
			return AttributeType.TYPE_DATETIME;
		if (string.equals("TYPE_VARCHAR"))
			return AttributeType.TYPE_VARCHAR;
		if (string.equals("BIGINT"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("BIT"))
			return AttributeType.TYPE_BOOLEAN;
		if (string.equals("VARCHAR"))
			return AttributeType.TYPE_VARCHAR;
		if (string.equals("DATETIME"))
			return AttributeType.TYPE_DATETIME;
		if (string.equals("DECIMAL"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("FLOAT"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("INT"))
			return AttributeType.TYPE_NUMERIC;
		if (string.equals("NUMERIC"))
			return AttributeType.TYPE_NUMERIC;
		else
			return null;
	}

	private static Entity findEntity(Database d, String string) {
		if (d.getEntities() == null || d.getEntities().size() == 0)
			return null;

		for (int i = 0; i < d.getEntities().size(); i++) {
			if (d.getEntities().get(i).toString().equals(string))
				return d.getEntities().get(i);
		}
		return null;
	}

	private static List<Attribute> findAttribute(JSONArray jArray, Entity entity) {
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

	public JSONObject writeMetaFile(InfResources ir) {
		JSONObject jObject = new JSONObject();
		JSONArray jsonobject1 = new JSONArray();
		JSONObject jIR = new JSONObject();
		for (int k = 0; k < ir.getDatabases().size(); k++) {
			Database database = ir.getDatabases().get(k);
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArrayEntity = new JSONArray();
			jsonObject.put("pack_name", database.getName());
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
					jObjAt.put("len", entity.getAttributes().get(j).getLength());
					jArray.put(jObjAt);
				}
				jA.put("type", entity.getType());
				jA.put("name", entity.getName());
				// jA.put("tree", entity.getTreePath());
				jA.put("attributes", jArray);
				jsonArrayEntity.put(jA);
				jsonObject.put("entities", jsonArrayEntity);
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
			jsonObject.put("relations", jArRel);
			jsonobject1.put(jsonObject);
		}
		jIR.put("ir_name", ir.getName());
		jIR.put("type", ir.getType());
		jIR.put("packages", jsonobject1);
		jObject.put("inf_resources", jIR);
		return jObject;
	}

	public boolean openConnection() throws ClassNotFoundException, SQLException {
		boolean result = false;
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String url = "jdbc:jtds:sqlserver://" + ip + "/" + dbname;
		conn = DriverManager.getConnection(url, username, password);
		result = true;
		System.out.println(result);
		return result;

	}

	public Connection getConn() throws ClassNotFoundException, SQLException {
		if (conn == null)
			openConnection();

		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String toString() {
		return name;
	}

	public void addDatabase(Database d) {
		this.databases.add(d);
	}

	public List<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
