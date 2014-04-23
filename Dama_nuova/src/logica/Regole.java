package logica;

import java.util.ArrayList;

public class Regole {

    private Scacchiera scacchiera;

    public Regole(Scacchiera s) {
	this.scacchiera = s;
    }

    /*
     * calcola tutte le mangiate e le mosse possibili e ritorna solo le mosse
     * che si è obbligati a fare, ad esempio se è possibile fare una doppia e
     * una tripla mangiata restituisce solo la tripla mangiata
     */
    public ArrayList<Mossa> getMossePermesse(boolean turnoneri,
	    ArrayList<Pedina> pedine) {
	ArrayList<MangiataMultipla> mangiate = new ArrayList<MangiataMultipla>();
	ArrayList<Mossa> mosse = new ArrayList<Mossa>();
	for (Pedina p : pedine)
	    mangiate.addAll(getMangiatePermesse(p));
	if (mangiate.size() != 0) {
	    int max = 0;

	    int i = 0;
	    while (i < mangiate.size()) {
		if (mangiate.get(i).getNumeroMangiate() >= max) {
		    max = mangiate.get(i).getNumeroMangiate();
		    i++;
		} else
		    mangiate.remove(i);
	    }

	    i = 0;
	    while (i < mangiate.size()) {
		if (mangiate.get(i).getNumeroMangiate() < max)
		    mangiate.remove(i);
		else
		    i++;
	    }

	    mosse.addAll(mangiate);
	    return mosse;
	} else {
	    for (Pedina p : pedine)
		mosse.addAll(getMovimentiPermessi(p));
	    return mosse;
	}

    }

    private ArrayList<Mossa> getMovimentiPermessi(Pedina ped) {
	if (!ped.isDamone()) {
	    int dir = 1;
	    if (ped.isNera())
		dir = -1;
	    return getMovimentiPermessi(ped, dir);
	} else {
	    ArrayList<Mossa> a;
	    a = getMovimentiPermessi(ped, 1);
	    a.addAll(getMovimentiPermessi(ped, -1));
	    return a;
	}

    }

    /*
     * restituisce i movimenti possibili della pedina
     */
    private ArrayList<Mossa> getMovimentiPermessi(Pedina ped, int direzione) {
	ArrayList<Mossa> mos = new ArrayList<Mossa>();
	Mossa mossa = null;
	int i = scacchiera.getPosizionePedina(ped)[0];
	int j = scacchiera.getPosizionePedina(ped)[1];
	if ((direzione == 1 && i != 7) || (direzione == -1 && i != 0)) {

	    if (j != 0) {
		mossa = new Mossa(scacchiera.getCasella(i, j),
			scacchiera.getCasella(i + direzione, j - 1));
		if (this.isValid(mossa))
		    mos.add(mossa);
	    }

	    if (j != 7) {
		mossa = new Mossa(scacchiera.getCasella(i, j),
			scacchiera.getCasella(i + direzione, j + 1));
		if (this.isValid(mossa))
		    mos.add(mossa);
	    }
	}
	return mos;
    }

    private ArrayList<MangiataMultipla> getMangiatePermesse(Pedina ped) {
	if (!ped.isDamone()) {
	    int dir = 1;
	    if (ped.isNera())
		dir = -1;
	    return getMangiatePermesse(ped, dir, null, false);
	} else {
	    ArrayList<MangiataMultipla> a;
	    a = getMangiatePermesse(ped, 1, null, true);
	    a.addAll(getMangiatePermesse(ped, -1, null, true));
	    return a;
	}

    }

    /*
     * calcola ricorsivamente le mangiate multiple, prova a vedere se la pedina
     * dalla posizione raggiunta dall'ultima mangiata, o dalla sua posizione di
     * partenza nel caso la mangiata precedente sia null, può effettuare un
     * ulteriore mangiata, in caso positivo controlla che sia valida e nel caso
     * lo sia aggiunge la mangiata alla mangiatamultipla e si richiama; nel caso
     * la mangiata non sia valida o non sia possibile mangiare restituisce la
     * mangiataMultipla precedente
     */
    private ArrayList<MangiataMultipla> getMangiatePermesse(Pedina ped,
	    int direzione, MangiataMultipla prec, boolean damone) {
	int i, j;
	ArrayList<MangiataMultipla> mos = new ArrayList<MangiataMultipla>();
	ArrayList<MangiataMultipla> mosse = new ArrayList<MangiataMultipla>();
	MangiataMultipla mangiata;

	if (prec == null) {
	    i = scacchiera.getPosizionePedina(ped)[0];
	    j = scacchiera.getPosizionePedina(ped)[1];
	    // System.out.println("" + i + j);
	} else {
	    i = prec.getCasellaFine().getRiga();
	    j = prec.getCasellaFine().getColonna();

	}
	if ((direzione == -1 && i > 1) || (direzione == 1 && i < 6)) {
	    if (j > 1) {
		// System.out.println("j>1" + i + j);
		mangiata = new MangiataMultipla(prec, new Mangiata(
			scacchiera.getCasella(i, j), scacchiera.getCasella((i)
				+ direzione, j - 1), scacchiera.getCasella(i
				+ direzione * 2, j - 2)));
		if (isValid(mangiata, ped, damone)) {
		    // System.out.print("èvalida");
		    mosse.clear();
		    if (damone
			    || ((ped.isNera() && mangiata.getCasellaFine()
				    .getRiga() == 0) || (!ped.isNera() && mangiata
				    .getCasellaFine().getRiga() == 7))) {
			damone = true;
			mosse = getMangiatePermesse(ped, 1, mangiata, true);
			mosse.addAll(getMangiatePermesse(ped, -1, mangiata,
				true));
		    } else {
			mosse = getMangiatePermesse(ped, direzione, mangiata,
				false);
		    }
		    if (mosse.size() == 0) {
			mos.add(mangiata);
		    } else {
			mos.addAll(mosse);
		    }
		}
	    }
	    if (j < 6) {
		// System.out.println("j<6" + i + j);
		mangiata = new MangiataMultipla(prec, new Mangiata(
			scacchiera.getCasella(i, j), scacchiera.getCasella(i
				+ direzione, j + 1), scacchiera.getCasella(i
				+ direzione * 2, j + 2)));
		if (isValid(mangiata, ped, damone)) {
		    mosse.clear();
		    if (damone
			    || ((ped.isNera() && mangiata.getCasellaFine()
				    .getRiga() == 0) || (!ped.isNera() && mangiata
				    .getCasellaFine().getRiga() == 7))) {
			damone = true;
			mosse = getMangiatePermesse(ped, 1, mangiata, true);
			mosse.addAll(getMangiatePermesse(ped, -1, mangiata,
				true));
		    } else {
			mosse = getMangiatePermesse(ped, direzione, mangiata,
				false);
		    }
		    if (mosse.size() == 0) {
			mos.add(mangiata);
		    } else {
			mos.addAll(mosse);
		    }
		}
	    }
	}

	return mos;
    }

    /*
     * la mossa, per come è costruita è valida se la casella finale è vuota
     */
    private boolean isValid(Mossa m) {
	if (m.casellaFine.getPed() == null)
	    return true;
	return false;
    }

    /*
     * la mangiataMultipla, per come è stata costruita, è valida se non mangia 2
     * volte la stessa pedina
     */
    private boolean isValid(MangiataMultipla m, Pedina p, boolean damone) {
	if (m.getCasellaFine().getPed() == null) {
	    if (m.getUltimaMangiata().getMangiata().getPed() != null
		    && m.getUltimaMangiata().getMangiata().getPed().isNera() != p
			    .isNera()) {
		if (damone
			|| (!damone && !m.getUltimaMangiata().getMangiata()
				.getPed().isDamone()))
		    if (!m.checkGiaMangiata(m.getUltimaMangiata().getMangiata()))
			return true;
	    }
	}
	return false;
    }
}
