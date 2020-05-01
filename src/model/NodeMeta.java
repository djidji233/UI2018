package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class NodeMeta extends DefaultMutableTreeNode {

	private Object data;
	private List<NodeMeta> children;
	private NodeMeta parent;

	public NodeMeta(Object data) {
		this.data = data;
		children = new ArrayList<>();

		if (data instanceof InfResources) {
			InfResources ir = (InfResources) data;
			for (int i = 0; i < ir.getDatabases().size(); i++) {
				Database d = ir.getDatabases().get(i);
				NodeMeta node = new NodeMeta(d);
				this.addChildren(node);
				node.setParent(this);
			}
		}
		if (data instanceof Database) {
			Database database = (Database) data;
			NodeMeta parentNodeEntity = new NodeMeta("Entity");
			this.children.add(parentNodeEntity);
			parentNodeEntity.setParent(this);
			NodeMeta parentNodeRelation = new NodeMeta("Relation");
			this.children.add(parentNodeRelation);
			parentNodeRelation.setParent(this);

			for (int i = 0; i < database.getEntities().size(); i++) {
				NodeMeta node = new NodeMeta(database.getEntities().get(i));
				parentNodeEntity.addChildren(node);
				node.setParent(parentNodeEntity);
			}
			for (int i = 0; i < database.getRelations().size(); i++) {
				NodeMeta node = new NodeMeta(database.getRelations().get(i));
				parentNodeRelation.addChildren(node);
				node.setParent(parentNodeRelation);
				NodeMeta childNode1 = new NodeMeta(database.getRelations().get(i).getSourceEntity().toString());
				NodeMeta childNode2 = new NodeMeta(database.getRelations().get(i).getTargetEntity().toString());
				NodeMeta childNode3 = new NodeMeta(database.getRelations().get(i).getSourceKeys().toString());
				NodeMeta childNode4 = new NodeMeta(database.getRelations().get(i).getTargetKeys().toString());
				node.addChildren(childNode1);
				node.addChildren(childNode2);
				childNode1.addChildren(childNode3);
				childNode2.addChildren(childNode4);
				childNode1.setParent(node);
				childNode2.setParent(node);
				childNode3.setParent(childNode1);
				childNode4.setParent(childNode4);
			}
		} else if (data instanceof Entity) {
			Entity entity = (Entity) data;
			for (int i = 0; i < entity.getAttributes().size(); i++) {
				NodeMeta node = new NodeMeta(entity.getAttributes().get(i));
				children.add(node);
				node.setParent(this);
			}
		} else if (data instanceof Attribute) {
			children = null;
		}
	}

	public void addChildren(NodeMeta n) {
		this.children.add(n);
	}

	@Override
	public String toString() {
		return data.toString();
	}

	@Override
	public Enumeration children() {
		return Collections.enumeration(children);
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		if (children != null && children.size() >= childIndex) {
			return (TreeNode) children.get(childIndex);
		}
		return null;
	}

	@Override
	public int getChildCount() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return children.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		if (children != null && children.size() != 0) {
			return false;
		}
		return true;
	}

	public Object getData() {
		return data;
	}

	public List<NodeMeta> getChildren() {
		return children;
	}

	public void setParent(NodeMeta parent) {
		this.parent = parent;
	}

}
