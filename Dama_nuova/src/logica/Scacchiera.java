package logica;

import java.util.ArrayList;

public class Scacchiera {

    private Casella[][] caselle = new Casella[8][8];
    /*
     * pedBianche e pedNere contengono le pedine ancora in gioco dei bianchi e
     * dei neri
     */
    private ArrayList<Pedina> pedBianche = new ArrayList<Pedina>();
    private ArrayList<Pedina> pedNere = new ArrayList<Pedina>();

    public Scacchiera() {
	initScacchiera();

    }

    /*
     * per la deep copy di una scacchiera
     */
    public Scacchiera(Scacchiera s) {
	for (int i = 0; i < 8; i++) {
	    for (int j = 0; j < 8; j++) {
		caselle[i][j] = new Casella(s.getCasella(i, j));
		if (caselle[i][j].getPed() != null
			&& caselle[i][j].getPed().isNera())
		    pedNere.add(caselle[i][j].getPed());
		if (caselle[i][j].getPed() != null
			&& !caselle[i][j].getPed().isNera())
		    pedBianche.add(caselle[i][j].getPed());
	    }
	}
    }

    /*
     * inizializza la scacchiera creando caselle e pedine
     */
    public void initScacchiera() {
	boolean c = false;
	Pedina ped;
	for (int riga = 0; riga < 8; riga++) {
	    c = !c;
	    for (int colonna = 0; colonna < 8; colonna++) {
		caselle[riga][colonna] = new Casella(c, riga, colonna);

		if (c) {
		    if (riga < 3) {
			ped = new Pedina(false);
			pedBianche.add(ped);
			caselle[riga][colonna].setPed(ped);

		    }
		    if (riga > 4) {
			ped = new Pedina(true);
			pedNere.add(ped);
			caselle[riga][colonna].setPed(ped);

		    }
		}
		c = !c;
	    }
	}
    }

    public Casella[][] getCaselle() {
	return caselle;
    }

    public void setCaselle(Casella[][] caselle) {
	this.caselle = caselle;
    }

    public ArrayList<Pedina> getPedBianche() {
	return pedBianche;
    }

    public void setPedBianche(ArrayList<Pedina> pedBianche) {
	this.pedBianche = pedBianche;
    }

    public ArrayList<Pedina> getPedNere() {
	return pedNere;
    }

    public void setPedNere(ArrayList<Pedina> pedNere) {
	this.pedNere = pedNere;
    }

    /*
     * metodo che restituisce le coordinate di una pedina come array di interi,
     * restituisce {-1,-1} se la pedine non è presente nella scacchiera
     */
    public int[] getPosizionePedina(Pedina ped) {
	for (int i = 0; i < 8; i++)
	    for (int j = 0; j < 8; j++)
		if (caselle[i][j].getPed() == ped)
		    return new int[] { i, j };
	return new int[] { -1, -1 };
    }

    /*
     * restituisce la casella alle coordinate passate
     */
    public Casella getCasella(int i, int j) {
	// System.out.print("get casella" + i + j);
	return caselle[i][j];
    }

}
