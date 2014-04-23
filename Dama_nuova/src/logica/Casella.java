package logica;

public class Casella {
    private boolean nera;
    private Pedina ped;
    private int riga, colonna;

    public Casella(boolean nera, Pedina ped, int riga, int colonna) {
	super();
	this.nera = nera;
	this.ped = ped;
	this.riga = riga;
	this.colonna = colonna;
    }

    public Casella(boolean nera, int riga, int colonna) {
	this(nera, null, riga, colonna);
    }

    /*
     * per la deep copy di una casella
     */
    public Casella(Casella casella) {

	// TODO Auto-generated constructor stub
	super();
	this.nera = casella.isNera();
	if (casella.getPed() != null)
	    this.ped = new Pedina(casella.getPed());
	else
	    this.ped = null;
	this.riga = casella.getRiga();
	this.colonna = casella.getColonna();
    }

    public boolean isNera() {
	return nera;
    }

    public void setNera(boolean nera) {
	this.nera = nera;
    }

    public Pedina getPed() {
	return ped;
    }

    public void setPed(Pedina ped) {
	this.ped = ped;
    }

    public int getRiga() {
	return riga;
    }

    public void setRiga(int riga) {
	this.riga = riga;
    }

    public int getColonna() {
	return colonna;
    }

    public void setColonna(int colonna) {
	this.colonna = colonna;
    }

}
