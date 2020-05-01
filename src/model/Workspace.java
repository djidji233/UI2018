package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class Workspace extends DefaultMutableTreeNode {

	private String name;
	private List<NodeMeta> children;

	public Workspace(String name) {
		this.name = name;
		children = new ArrayList<>();
	}

	public void addChildren(NodeMeta child) {
		this.children.add(child);
	}

	public void removeChildren(NodeMeta child) {
		this.children.remove(child);
	}

	public String toString() {
		return name;
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
		return null;
	}

	@Override
	public boolean isLeaf() {
		if (children != null && children.size() != 0) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public List<NodeMeta> getChildren() {
		return children;
	}

}
