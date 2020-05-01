package model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

public class Node implements TreeNode, Serializable {

	private static final long serialVersionUID = 3560278628182342316L;
	private List<NodeElement> data;
	private List<Node> children;

	public Node() {
		this.data = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	public Node(List<NodeElement> data) {
		this();
		setData(data);
	}

	@Override
	public String toString() {
		return data.toString();
	}

	public void addChildren(Node istn) {
		this.children.add(istn);
	}

	public List<NodeElement> getData() {
		return data;
	}

	public void setData(List<NodeElement> data) {
		this.data = data;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	@Override
	public Enumeration children() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TreeNode getChildAt(int arg0) {
		return this.children.get(arg0);
	}

	@Override
	public int getChildCount() {
		return this.children.size();
	}

	@Override
	public int getIndex(TreeNode arg0) {
		return this.children.indexOf(arg0);
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return children.size()==0;

	}

	public int find(NodeElement element) {
		
//		if(isLeaf())
//			return data.get(0).getBlockAddress();
		
		int t = -1;
		for( NodeElement d : data) {
			if(d.compareTo(element)<0)
			{
				System.out.println(d+ " < " + element);
				t++;
			}
			else break;
		}
		if(t==-1)
			t=0;
		if(isLeaf())
			return data.get(t).getBlockAddress();
		
		System.out.println("==================================");
		
		return children.get(t).find(element);
		
	}
}
