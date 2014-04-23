package logica;

public class Pedina {
    private boolean damone;
    private boolean nera;

    public Pedina(boolean nera, boolean damone) {
	super();
	this.damone = damone;
	this.nera = nera;
    }

    public Pedina(boolean nera) {
	this(nera, false);
    }

    /*
     * per la deep copy della Pedina
     */
    public Pedina(Pedina ped) {
	// TODO Auto-generated constructor stub
	super();
	this.damone = ped.isDamone();
	this.nera = ped.isNera();
    }

    public boolean isDamone() {
	return damone;
    }

    public void setDamone(boolean damone) {
	this.damone = damone;
    }

    public boolean isNera() {
	return nera;
    }

    public void setNera(boolean nera) {
	this.nera = nera;
    }

}
