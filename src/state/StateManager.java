package state;

public class StateManager {

	private DodavanjeState ddState;
	private BrisanjeState bState;
	private PretragaState pState;
	private RelacijeState rState;
	private IzmenaState iState;

	private IState current;

	public StateManager() {
		ddState = new DodavanjeState();
		bState = new BrisanjeState();
		pState = new PretragaState();
		rState = new RelacijeState();
		iState = new IzmenaState();
	}

	public IState getCurrent() {
		return current;
	}
	
	public void setCurrentIzmenaState() {
		this.current = iState;
	}

	public void setCurrentDodavanjeState() {
		this.current = ddState;
	}

	public void setCurrentBrisanjeState() {
		this.current = bState;
	}

	public void setCurrentPretragaState() {
		this.current = pState;
	}

	public void setCurrentRelacijeState() {
		this.current = rState;
	}
}
