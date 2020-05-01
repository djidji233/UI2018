package model.tree;

import javax.swing.tree.DefaultTreeModel;

public class IndSekTreeModel extends DefaultTreeModel {

	public IndSekTreeModel(Tree ist) {
		super(ist.getRootElement());
	}

}
