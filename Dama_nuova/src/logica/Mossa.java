package logica;

public class Mossa {
    protected Casella casellaInizio, casellaFine;

    public Mossa(Casella casellaInizio, Casella casellafine) {
	this.casellaInizio = casellaInizio;
	this.casellaFine = casellafine;
    }

    public Mossa() {

    }

    public Casella getCasellaInizio() {
	return casellaInizio;
    }

    public void setCasellaInizio(Casella casellaInizio) {
	this.casellaInizio = casellaInizio;
    }

    public Casella getCasellaFine() {
	return casellaFine;
    }

    public void setCasellaFine(Casella casellaFine) {
	this.casellaFine = casellaFine;
    }

    /*
     * override del equals per poter usare il metodo equals dell'arraylist
     * basandosi sulla casella di inizio e su quella di fine invece che sull'id
     * dell oggetto
     */
    @Override
    public boolean equals(Object o) {
	if (o instanceof Mossa) {
	    Mossa m = (Mossa) o;
	    if (this.casellaFine == m.casellaFine
		    && this.casellaInizio == m.casellaInizio)
		return true;
	}
	return false;
    }

}