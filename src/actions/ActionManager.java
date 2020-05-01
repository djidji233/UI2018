package actions;

import view.MyAbout;

public class ActionManager {

	private AboutAction aboutAction;
	private MetaEditorAction metaEditorAction;
	private LoadMetaSchemaAction loadMetaSchemaAction;
	private CloseDatabaseAction closeDatabaseAction;
	private WriteMetaSchemaAction writeMetaSchemaAction;
	private FetchPreviousBlockAction fetchPreviousBlockAction;
	private FetchNextBlockAction fetchNextBlockAction;
	private SortAction sortAction;

	public ActionManager() {
		initialiseActions();
	}

	private void initialiseActions() {
		aboutAction = new AboutAction();
		metaEditorAction = new MetaEditorAction();
		loadMetaSchemaAction = new LoadMetaSchemaAction();
		closeDatabaseAction = new CloseDatabaseAction();
		writeMetaSchemaAction = new WriteMetaSchemaAction();
		fetchPreviousBlockAction = new FetchPreviousBlockAction();
		fetchNextBlockAction = new FetchNextBlockAction();
		sortAction = new SortAction();
	}

	public WriteMetaSchemaAction getWriteMetaSchemaAction() {
		return writeMetaSchemaAction;
	}

	public void setWriteMetaSchemaAction(WriteMetaSchemaAction writeMetaSchemaAction) {
		this.writeMetaSchemaAction = writeMetaSchemaAction;
	}

	public CloseDatabaseAction getCloseDatabaseAction() {
		return closeDatabaseAction;
	}

	public void setCloseDatabaseAction(CloseDatabaseAction closeDatabaseAction) {
		this.closeDatabaseAction = closeDatabaseAction;
	}

	public LoadMetaSchemaAction getLoadMetaSchemaAction() {
		return loadMetaSchemaAction;
	}

	public void setLoadMetaSchemaAction(LoadMetaSchemaAction loadMetaSchemaAction) {
		this.loadMetaSchemaAction = loadMetaSchemaAction;
	}

	public AboutAction getAboutAction() {
		return aboutAction;
	}

	public void setAboutAction(AboutAction aboutAction) {
		this.aboutAction = aboutAction;
	}

	public MetaEditorAction getMetaEditorAction() {
		return metaEditorAction;
	}

	public void setMetaEditorAction(MetaEditorAction metaEditorAction) {
		this.metaEditorAction = metaEditorAction;
	}
	public FetchPreviousBlockAction getFetchPreviousBlockAction() {
		return fetchPreviousBlockAction;
	}
	public FetchNextBlockAction getFetchNextBlockAction() {
		return fetchNextBlockAction;
	}
	public SortAction getSortAction() {
		return sortAction;
	}
	public void setSortAction(SortAction sortAction) {
		this.sortAction = sortAction;
	}

}
