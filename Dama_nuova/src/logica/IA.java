package logica;

import java.util.ArrayList;
import java.util.Random;

public class IA {
    private Random r = new Random();

    public void eseguiMossa(Partita p) throws InterruptedException {
	ArrayList<Mossa> mosse = p.getMosseturno();
	trovaMossaMigliore(mosse, p.getSca(), p.getTurno(), 3);
	Mossa m = mosse.get(r.nextInt(mosse.size()));
	Thread.sleep(600);
	if (m instanceof MangiataMultipla) {
	    MangiataMultipla mm = (MangiataMultipla) m;
	    while (mm.getNumeroMangiate() > 0) {
		p.eseguiMossa(mm.getMangiata(0));
		Thread.sleep(600);
	    }
	} else
	    p.eseguiMossa(m);
    }

    /*
     * simula n (iteraz) turni in avanti tutte le possibili mosse e mi
     * restituisce un array con 4 interi che valuta il caso peggiore a cui posso
     * arrivare con precedenza (così com'è funziona solo se l'ia usa il bianco):
     * caso peggiore: bianco ha perso pedoni, ne ha mangiati di meno, ha perso
     * più pedine e ne ha mangiate di meno
     */
    public int[] simulaPossibiliMosse(Scacchiera s, int iteraz, boolean turno) {
	ArrayList<Mossa> mosse;
	Regole r = new Regole(s);
	if (turno)
	    mosse = r.getMossePermesse(turno, s.getPedNere());
	else
	    mosse = r.getMossePermesse(turno, s.getPedBianche());
	int[] ret = { 20, 0, 20, 0 };
	if (iteraz == 0) {
	    int c1 = 0; // min_bianche_mangiate
	    int c2 = 0; // min_nere_mangiate
	    ret[2] = s.getPedBianche().size();
	    ret[3] = s.getPedNere().size();
	    for (Pedina p : s.getPedBianche())
		if (p.isDamone())
		    c1++;
	    for (Pedina p : s.getPedNere())
		if (p.isDamone())
		    c2++;
	    ret[0] = c1;
	    ret[1] = c2;
	    return ret;
	}

	for (int i = 0; i < mosse.size(); i++) {
	    if (iteraz > 0) {
		int[] k = simulaPossibiliMosse(simula(mosse.get(i), s),
			iteraz - 1, !turno);
		if (k[0] < ret[0]) {
		    ret[0] = k[0];
		    ret[1] = k[1];
		    ret[2] = k[2];
		    ret[3] = k[3];
		} else {
		    if (k[1] == ret[1]) {
			if (k[1] > ret[1]) {
			    ret[1] = k[1];
			    ret[2] = k[2];
			    ret[3] = k[3];
			} else {
			    if (k[2] == ret[2]) {
				if (k[2] < ret[2]) {
				    ret[2] = k[2];
				    ret[3] = k[3];
				} else {
				    if (k[3] == ret[3]) {
					if (k[3] > ret[3])
					    ret[3] = k[3];
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return ret;
    }

    /*
     * calcola la mossa migliore calcolando il caso peggiore dopo n turni per
     * ogni mossa iniziale e scegliendo il caso migliore tra quelli peggiori, se
     * trova più di una mossa migliore si riesegue calcolando le mosse migliori
     * trovate fino a n-2 turni etc fino a che non trova solo una mossa o fino a
     * che n >2
     */
    public ArrayList<Mossa> trovaMossaMigliore(ArrayList<Mossa> mosse,
	    Scacchiera s, boolean turno, int n_mosse) {
	int mie = 0;
	int sue = 1;
	int dam_mie = 2;
	int dam_sue = 3;
	if (turno) {
	    mie = 1;
	    sue = 0;
	    dam_mie = 3;
	    dam_sue = 2;
	}
	int[] base = { 0, 20, 0, 20 };
	ArrayList<int[]> sim = new ArrayList<int[]>();

	for (int i = 0; i < mosse.size(); i++) {

	    int[] k = simulaPossibiliMosse(simula(mosse.get(i), s), n_mosse,
		    !turno);
	    sim.add(k);
	    if (k[mie] > base[mie]) {
		base[mie] = k[mie];
		base[sue] = k[sue];
		base[dam_mie] = k[dam_mie];
		base[dam_sue] = k[dam_sue];
	    } else {
		if (k[mie] == base[mie]) {
		    if (k[sue] < base[sue]) {
			base[sue] = k[sue];
			base[dam_mie] = k[dam_mie];
			base[dam_sue] = k[dam_sue];
		    } else {
			if (k[sue] == base[sue]) {
			    if (k[dam_mie] > base[dam_mie]) {
				base[dam_mie] = k[dam_mie];
				base[dam_sue] = k[dam_sue];
			    } else {
				if (k[dam_mie] == base[dam_mie]) {
				    if (k[dam_sue] < base[dam_sue])
					base[dam_sue] = k[dam_sue];
				}
			    }
			}
		    }
		}

	    }
	}
	int i = 0;
	// System.out.println("----------");
	while (i < mosse.size()) {
	    if (sim.get(i)[mie] < base[mie]) {
		sim.remove(i);
		mosse.remove(i);
	    } else {
		if (sim.get(i)[sue] > base[sue]) {
		    sim.remove(i);
		    mosse.remove(i);
		} else {
		    if (sim.get(i)[dam_mie] < base[dam_mie]) {
			sim.remove(i);
			mosse.remove(i);
		    } else {
			if (sim.get(i)[dam_sue] > base[dam_sue]) {
			    sim.remove(i);
			    mosse.remove(i);
			} else {
			    i++;
			}
		    }
		}
	    }
	}
	if (mosse.size() > 1 && n_mosse > 2) {
	    return trovaMossaMigliore(mosse, s, turno, n_mosse - 2);
	}
	System.out.println(mosse.size());
	return mosse;

    }

    /*
     * simula la mossa su una scacchiera nuova ne restituisci il risultato
     */
    public Scacchiera simula(Mossa m, Scacchiera sca) {
	Scacchiera s = new Scacchiera(sca);
	if (m instanceof MangiataMultipla) {
	    MangiataMultipla mm = (MangiataMultipla) m;
	    s.getCasella(mm.casellaFine.getRiga(), mm.casellaFine.getColonna())
		    .setPed(s.getCasella(mm.casellaInizio.getRiga(),
			    mm.getCasellaInizio().getColonna()).getPed());
	    s.getCasella(mm.casellaInizio.getRiga(),
		    mm.casellaInizio.getColonna()).setPed(null);
	    for (int i = 0; i < mm.getNumeroMangiate(); i++) {
		s.getPedBianche().remove(
			s.getCasella(mm.getMangiata(i).getMangiata().getRiga(),
				mm.getMangiata(i).getMangiata().getColonna())
				.getPed());

		s.getPedNere().remove(
			s.getCasella(mm.getMangiata(i).getMangiata().getRiga(),
				mm.getMangiata(i).getMangiata().getColonna())
				.getPed());
		s.getCasella(mm.getMangiata(i).getMangiata().getRiga(),
			mm.getMangiata(i).getMangiata().getColonna()).setPed(
			null);
	    }

	} else {
	    s.getCasella(m.casellaFine.getRiga(), m.casellaFine.getColonna())
		    .setPed(s.getCasella(m.casellaInizio.getRiga(),
			    m.getCasellaInizio().getColonna()).getPed());
	    s.getCasella(m.casellaInizio.getRiga(),
		    m.casellaInizio.getColonna()).setPed(null);
	}
	return s;
    }
}
