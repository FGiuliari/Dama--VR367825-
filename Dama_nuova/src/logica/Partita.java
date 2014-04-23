package logica;

import grafica.Casella_grafica;
import grafica.ListenerMossa;
import grafica.Scacchiera_grafica;

import java.awt.BorderLayout;
import java.util.ArrayList;

import main.Main;

public class Partita {
    private boolean turno;
    private Scacchiera sca;
    private Scacchiera_grafica sca_g;
    private Regole reg;
    private ArrayList<Mossa> mosseturno;
    private ArrayList<Pedina> pedBianche;
    private ArrayList<Pedina> pedNere;
    private Casella casellaIniziale;
    private IA ia = new IA();
    private Main frame;

    public Partita(Main frame) {
	this.frame = frame;
	init();

    }

    public void init() {
	frame.setLayout(new BorderLayout());

	this.sca = new Scacchiera();
	this.sca_g = new Scacchiera_grafica(sca);
	this.frame.add(sca_g);
	this.frame.revalidate();
	this.turno = true; // true tocca ai neri
	this.reg = new Regole(this.sca);
	this.pedBianche = sca.getPedBianche();
	this.pedNere = sca.getPedNere();
	casellaIniziale = null;
	getMosseTurno();
	illuminapedineMovibili();
	initActionListener();
    }

    public void cambiaTurno() {
	sca_g.updateCas();

	turno = !turno;

	getMosseTurno();

	if (!checkVinto()) {
	    if (!turno) {

		(new Thread() {
		    public void run() {
			try {
			    ia.eseguiMossa(Partita.this);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }

		}).start();
		;
	    }
	}
	illuminapedineMovibili();
    }

    public boolean checkVinto() {
	if (!turno) {
	    if (mosseturno.size() == 0) {
		// JOptionPane
		// .showMessageDialog(sca_g, "complimenti, hai vinto");
		frame.finePartita(true);
		return true;

	    }

	} else {
	    if (mosseturno.size() == 0) {
		// JOptionPane.showMessageDialog(sca_g,
		// "spiacente, hai perso");
		frame.finePartita(false);
		return true;

	    }
	}
	return false;

    }

    public void getMosseTurno() {
	if (turno)
	    mosseturno = reg.getMossePermesse(true, pedNere);
	else
	    mosseturno = reg.getMossePermesse(false, pedBianche);
    }

    public void illuminapedineMovibili() {

	for (Mossa m : mosseturno) {
	    // System.out.println("" + m.getCasellaInizio().getRiga()
	    // + m.getCasellaInizio().getColonna());
	    sca_g.getCasellaGrafica(m.getCasellaInizio().getRiga(),
		    m.getCasellaInizio().getColonna()).illumina();
	}

    }

    public boolean getTurno() {
	return this.turno;
    }

    public Scacchiera_grafica getScacchiera_grafica() {
	return sca_g;
    }

    public void initActionListener() {
	Casella_grafica[][] cas;
	cas = sca_g.getCaselleGrafiche();
	for (Casella_grafica[] cass : cas)
	    for (Casella_grafica c : cass)
		c.addActionListener(new ListenerMossa(this, c.getCas()));
    }

    public void eseguiMossa(Mossa m) {
	if (m instanceof MangiataMultipla) {
	    if (mosseturno.contains(m)) {
		MangiataMultipla man = (MangiataMultipla) m;
		for (int i = 0; i < man.getNumeroMangiate(); i++) {
		    man.getMangiata(i).casellaFine
			    .setPed(man.getMangiata(i).casellaInizio.getPed());
		    if (!turno) {
			pedNere.remove(man.getMangiata(i).getMangiata()
				.getPed());
			if (man.getMangiata(i).getCasellaFine().getRiga() == 7)
			    man.getMangiata(i).casellaInizio.getPed()
				    .setDamone(true);
		    } else {
			pedBianche.remove(man.getMangiata(i).getMangiata()
				.getPed());
			if (man.getMangiata(i).getCasellaFine().getRiga() == 0)
			    man.getMangiata(i).casellaInizio.getPed()
				    .setDamone(true);
		    }

		    man.getMangiata(i).getMangiata().setPed(null);
		    man.getMangiata(i).casellaInizio.setPed(null);

		}
		sca_g.updateCas();
		cambiaTurno();
		return;
	    }
	}
	if (m instanceof Mangiata) {
	    Mangiata man = (Mangiata) m;
	    if (mosseturno.get(0) instanceof MangiataMultipla) {
		boolean c = false;
		for (Mossa mossa : mosseturno) {
		    MangiataMultipla mangiata = (MangiataMultipla) mossa;
		    if (mangiata.getMangiata(0).equals(m))
			c = true;
		}
		if (c) {
		    int i = 0;
		    while (i < mosseturno.size()) {
			MangiataMultipla mangiata = (MangiataMultipla) mosseturno
				.get(i);
			if (!mangiata.getMangiata(0).equals(m))
			    mosseturno.remove(mosseturno.get(i));
			else {
			    mangiata.removeMangiata(0);
			    if (mangiata.getNumeroMangiate() == 0)
				mosseturno.remove(mosseturno.get(i));
			    else
				i++;
			}
		    }

		    m.casellaFine.setPed(man.casellaInizio.getPed());
		    if (turno) {
			pedBianche.remove(man.getMangiata().getPed());
			if (man.getCasellaFine().getRiga() == 0)
			    man.casellaInizio.getPed().setDamone(true);
		    } else {
			pedNere.remove(man.getMangiata().getPed());
			if (man.getCasellaFine().getRiga() == 7)
			    man.casellaInizio.getPed().setDamone(true);
		    }

		    man.getMangiata().setPed(null);
		    man.casellaInizio.setPed(null);

		    sca_g.updateCas();
		    illuminapedineMovibili();
		    if (mosseturno.size() == 0)
			cambiaTurno();

		}
	    }

	    return;
	}
	if (mosseturno.contains(m)) {
	    m.casellaFine.setPed(m.casellaInizio.getPed());
	    m.casellaInizio.setPed(null);
	    if (turno && m.getCasellaFine().getRiga() == 0)
		m.casellaFine.getPed().setDamone(true);
	    if (!turno && m.getCasellaFine().getRiga() == 7)
		m.casellaFine.getPed().setDamone(true);
	    cambiaTurno();
	}

    }

    public Scacchiera getSca() {
	return sca;
    }

    public void setSca(Scacchiera sca) {
	this.sca = sca;
    }

    public Scacchiera_grafica getSca_g() {
	return sca_g;
    }

    public void setSca_g(Scacchiera_grafica sca_g) {
	this.sca_g = sca_g;
    }

    public Regole getReg() {
	return reg;
    }

    public void setReg(Regole reg) {
	this.reg = reg;
    }

    public ArrayList<Mossa> getMosseturno() {
	return mosseturno;
    }

    public void setMosseturno(ArrayList<Mossa> mosseturno) {
	this.mosseturno = mosseturno;
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

    public Casella getCasellaIniziale() {
	return casellaIniziale;
    }

    public void setCasellaIniziale(Casella casellaIniziale) {
	this.casellaIniziale = casellaIniziale;
	if (casellaIniziale != null) {
	    sca_g.updateCas();
	    illuminapedineMovibili();
	    sca_g.getCasellaGrafica(casellaIniziale.getRiga(),
		    casellaIniziale.getColonna()).setIcon(null);
	}
    }

    public void setTurno(boolean turno) {
	this.turno = turno;
    }

}
