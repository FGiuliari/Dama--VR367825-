package logica;

import java.util.ArrayList;

public class MangiataMultipla extends Mossa {
    private ArrayList<Mangiata> mangiate = new ArrayList<Mangiata>();
    private int num_mosse;

    public MangiataMultipla(ArrayList<Mangiata> man) {
	super(man.get(0).getCasellaInizio(), man.get(man.size())
		.getCasellaFine());
	num_mosse = man.size();
    }

    public MangiataMultipla(MangiataMultipla m, Mangiata mm) {
	super();
	if (m != null) {
	    mangiate.addAll(m.mangiate);
	    setCasellaInizio(m.getCasellaInizio());
	} else {
	    setCasellaInizio(mm.casellaInizio);
	}
	mangiate.add(mm);
	setCasellaFine(mm.getCasellaFine());
	num_mosse = mangiate.size();
    }

    public MangiataMultipla() {
	super(null, null);
	num_mosse = 0;
    }

    public Mangiata getMangiata(int i) {
	if (i < num_mosse) {
	    return mangiate.get(i);
	}
	throw new IllegalArgumentException();
    }

    public int getNumeroMangiate() {
	return num_mosse;
    }

    public void addMangiata(Mangiata m) {
	if (num_mosse == 0) {
	    casellaInizio = m.getCasellaInizio();
	}
	casellaFine = m.getCasellaFine();
	mangiate.add(m);
	++num_mosse;
    }

    public Mangiata getUltimaMangiata() {
	return mangiate.get(mangiate.size() - 1);
    }

    public void removeMangiata(int i) {
	mangiate.remove(i);
	--num_mosse;
	if (mangiate.size() > 0)
	    casellaInizio = mangiate.get(0).getCasellaInizio();
    }

    public boolean checkGiaMangiata(Casella cas) {
	for (int i = 0; i < mangiate.size() - 1; i++)
	    if (mangiate.get(i).getMangiata() == cas)
		return true;
	return false;
    }
}
