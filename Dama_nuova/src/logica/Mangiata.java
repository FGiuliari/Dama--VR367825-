package logica;

public class Mangiata extends Mossa {
	private Casella mangiata;

	public Mangiata(Casella casellaInizio, Casella mangiata, Casella casellaFine) {
		super(casellaInizio, casellaFine);
		this.mangiata = mangiata;
	}

	public Casella getMangiata() {
		return mangiata;
	}

	public void setMangiata(Casella mangiata) {
		this.mangiata = mangiata;
	}

}
