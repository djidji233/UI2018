package actions;

import view.MetaEditorFrame;

public class MetaActionManager {

	private AddAttributeAction addAttributeAction;
	private DelAttributeAction delAttributeAction;
	
	private AddEntityAction addEntityAction;
	private DelEntityAction delEntityAction;
	
	private AddRelationAction addRelationAction;
	private DelRelationAction delRelationAction;
	
	private MetaEditorFrame frame;
	

	public MetaActionManager() {
		

		
		addAttributeAction = new AddAttributeAction();
		delAttributeAction = new DelAttributeAction();
		
		addEntityAction = new AddEntityAction();
		delEntityAction = new DelEntityAction();
		
		addRelationAction = new AddRelationAction();
		delRelationAction = new DelRelationAction();
		
		
		initialiseActions();
	}
	
	

	public AddAttributeAction getAddAttributeAction() {
		return addAttributeAction;
	}



	public void setAddAttributeAction(AddAttributeAction addAttributeAction) {
		this.addAttributeAction = addAttributeAction;
	}



	public DelAttributeAction getDelAttributeAction() {
		return delAttributeAction;
	}



	public void setDelAttributeAction(DelAttributeAction delAttributeAction) {
		this.delAttributeAction = delAttributeAction;
	}



	public AddEntityAction getAddEntityAction() {
		return addEntityAction;
	}



	public void setAddEntityAction(AddEntityAction addEntityAction) {
		this.addEntityAction = addEntityAction;
	}



	public DelEntityAction getDelEntityAction() {
		return delEntityAction;
	}



	public void setDelEntityAction(DelEntityAction delEntityAction) {
		this.delEntityAction = delEntityAction;
	}



	public AddRelationAction getAddRelationAction() {
		return addRelationAction;
	}



	public void setAddRelationAction(AddRelationAction addRelationAction) {
		this.addRelationAction = addRelationAction;
	}



	public DelRelationAction getDelRelationAction() {
		return delRelationAction;
	}



	public void setDelRelationAction(DelRelationAction delRelationAction) {
		this.delRelationAction = delRelationAction;
	}



	private void initialiseActions() {
		
	}
	
}
