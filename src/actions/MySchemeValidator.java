package actions;

import java.util.List;

import javax.swing.JOptionPane;

import model.Attribute;
import model.Database;
import model.Entity;
import model.InfResources;
import model.Relation;

public class MySchemeValidator {

	public static boolean CheckMyScheme(List<InfResources> res) {
		for (int j = 0; j < res.size(); j++) {
			InfResources ir = res.get(j);
			for (int k = 0; k < ir.getDatabases().size(); k++) {
				Database database = ir.getDatabases().get(k);
				if (!checkEntity(database.getEntities()))
					return false;

				for (int i = 0; i < database.getEntities().size(); i++) {
					Entity entity = database.getEntities().get(i);
					List<Attribute> attributes = entity.getAttributes();
					if (!checkAttributeName(attributes))
						return false;
				}

				if (!checkTargetRelation(database.getEntities(), database.getRelations()))
					return false;

				if (!checkSourceRelation(database.getEntities(), database.getRelations()))
					return false;
			}
		}
		return true;
	}

	private static boolean checkEntity(List<Entity> entities) {
		int k = 0;
		for (int i = 0; i < entities.size(); i++) {
			k = checkEntityString(entities, entities.get(i).toString());
			if (k > 1 || k == 0) {
				JOptionPane.showMessageDialog(null, "Error while loading scheme: Entity " + entities.get(i), "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private static int checkEntityString(List<Entity> entities, String string) {
		if (entities == null || entities.size() == 0)
			return 0;
		int k = 0;
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).toString().equals(string))
				k++;
		}
		return k;
	}

	private static boolean checkAttributeName(List<Attribute> attributes) {
		int k;
		for (int i = 0; i < attributes.size(); i++) {
			k = checkAttributeString(attributes, attributes.get(i).toString());
			if (k > 1 || k == 0) {
				JOptionPane.showMessageDialog(null, "Error while loading scheme: Attribute " + attributes.get(i),
						"Error", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private static int checkAttributeString(List<Attribute> attributes, String string) {
		if (attributes == null || attributes.size() == 0)
			return 0;
		int k = 0;
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).toString().equals(string))
				k++;
		}
		return k;
	}

	private static boolean checkTargetRelation(List<Entity> entities, List<Relation> relations) {
		int k;
		for (int i = 0; i < relations.size(); i++) {
			k = checkRelation(entities, relations.get(i).getTargetEntity().getName());
			if (k == 0 || k > 1) {
				JOptionPane.showMessageDialog(null, "Error while loading scheme: Relation " + relations.get(i), "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private static boolean checkSourceRelation(List<Entity> entities, List<Relation> relations) {
		int k;
		for (int i = 0; i < relations.size(); i++) {
			k = checkRelation(entities, relations.get(i).getSourceEntity().getName());
			if (k == 0 || k > 1) {
				JOptionPane.showMessageDialog(null, "Error while loading scheme: Relation " + relations.get(i), "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private static int checkRelation(List<Entity> entities, String string) {
		int k = 0;
		for (int i = 0; i < entities.size(); i++) {
			if (string.equals(entities.get(i).getName()))
				k++;
		}
		return k;
	}

}