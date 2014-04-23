package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logica.Casella;
import logica.Mangiata;
import logica.Mossa;
import logica.Partita;

public class ListenerMossa implements ActionListener {
    Partita partita;
    Casella cas;

    public ListenerMossa(Partita p, Casella cas) {
	this.partita = p;
	this.cas = cas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (partita.getCasellaIniziale() == null && cas.getPed() != null
		&& cas.getPed().isNera() == partita.getTurno()) {
	    /*
	     * Se non abbiamo ancora impostato la casella iniziale e quella su
	     * cui abbiamo cliccato ha una casella ed è coerente col turno
	     * (casella nera se turno dei neri) impostiamo la casella su cui
	     * abbiamo cliccato come casella iniziale
	     */
	    partita.setCasellaIniziale(cas);
	} else {
	    if (cas.getPed() != null
		    && (cas.getPed().isNera() == partita.getTurno())) {
		/*
		 * per poter selezionare una casella coerente col turno con un
		 * click solo
		 */
		partita.setCasellaIniziale(cas);
	    } else {
		if (partita.getCasellaIniziale() != null) {

		    if (partita.getCasellaIniziale().getRiga() - cas.getRiga() == 1
			    || partita.getCasellaIniziale().getRiga()
				    - cas.getRiga() == -1) {
			/*
			 * se la casella è a distanza 1 (movimento)
			 */
			partita.eseguiMossa(new Mossa(partita
				.getCasellaIniziale(), cas));
		    } else {
			/*
			 * se la casella è a distanza 2 (mangiata)
			 */
			partita.eseguiMossa(new Mangiata(partita
				.getCasellaIniziale(),
				partita.getSca()
					.getCasella(
						(partita.getCasellaIniziale()
							.getRiga() + cas
							.getRiga()) / 2,
						(partita.getCasellaIniziale()
							.getColonna() + cas
							.getColonna()) / 2),
				cas));
		    }
		    partita.illuminapedineMovibili();
		    partita.setCasellaIniziale(null);
		}
	    }
	}
    }
}
