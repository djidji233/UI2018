package model.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Attribute;
import model.Entry;

public class NodeElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6796740872107316791L; //3215531261892437543
	private int blockAddress;
	private List<KeyElement> keyValue;

	public NodeElement(int blockAddress, List<KeyElement> keyValue) {
		this.blockAddress = blockAddress;
		this.keyValue = keyValue;
	}
	
	public NodeElement(Entry e) {
		
		blockAddress =0;
		keyValue = new ArrayList<>();
		
		List<Attribute> atributi = e.getEntitet().getAttributes();
		
		int n=atributi.size();
		for(int i=0;i<n;i++) {
			if(atributi.get(i).isPrimaryKey()) {
				keyValue.add(new KeyElement(atributi.get(i).getType().name(), e.getFields().get(atributi.get(i))));
			}
			else break;
		}
		
		
	}

	public List<KeyElement> getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(List<KeyElement> keyValue) {
		this.keyValue = keyValue;
	}

	public int getBlockAddress() {
		return blockAddress;
	}

	public void setBlockAddress(int blockAddress) {
		this.blockAddress = blockAddress;
	}

	public String toString() {
		String value = "[";
		for (int i = 0; i < keyValue.size(); i++) {
			value += keyValue.get(i).getValue() + "|";
		}
		return value + "]";

	}

	public NodeElement clone() {
		List<KeyElement> keyValueCopy = new ArrayList<KeyElement>();
		keyValueCopy.addAll(keyValue);
		return new NodeElement(blockAddress, keyValueCopy);
	}
	
	public int compareTo(NodeElement node) {
		
		int n = keyValue.size();
		for(int i=0;i<n;i++) {
			int k = keyValue.get(i).compareTo(node.getKeyValue().get(i));
			if(k!=0)
				return k;
		}
		return 0;
		
	}

}
