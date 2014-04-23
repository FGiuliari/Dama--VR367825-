package grafica;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import logica.Casella;

public class Casella_grafica extends JButton {

    Casella cas;
    Pedina_grafica pg;
    public static final ImageIcon bgNero = new ImageIcon(Casella_grafica.class
	    .getClassLoader().getResource("Sfondo_nero.png"));
    public static final ImageIcon bgNeroAttivo = new ImageIcon(
	    Casella_grafica.class.getClassLoader().getResource(
		    "Sfondo_nero_attivo.png"));
    public static final ImageIcon bgBianco = new ImageIcon(
	    Casella_grafica.class.getClassLoader().getResource(
		    "Sfondo_bianco.png"));
    public static final ImageIcon bgBiancoAttivo = new ImageIcon(
	    Casella_grafica.class.getClassLoader().getResource(
		    "Sfondo_bianco.png"));

    public Casella_grafica(Casella c) {
	cas = c;
	setImg();
	if (cas.getPed() != null) {
	    update();
	}
    }

    /* setta l'immagine della casella */
    public void setImg() {
	if (cas.isNera()) {
	    this.setIcon(bgNero);
	} else {
	    this.setIcon(bgBianco);
	}
	this.setAlignmentX(CENTER_ALIGNMENT);
	this.setAlignmentY(CENTER_ALIGNMENT);
	setMargin(new Insets(1, 8, 1, 1));
    }

    /* ricreo la pedina grafica all'interno della casella grafica */
    public void update() {
	if (pg != null)
	    remove(pg);

	if (cas.getPed() != null) {
	    pg = new Pedina_grafica(cas.getPed());
	    add(pg);
	}
	repaint();
	revalidate();

    }

    /* setta il bg attivato (immagine illuminata) sulla casella */
    public void illumina() {
	if (cas.isNera())
	    this.setIcon(bgNeroAttivo);
	else
	    this.setIcon(bgBiancoAttivo);
	repaint();
    }

    public Casella getCas() {
	return cas;
    }

    public void setCas(Casella cas) {
	this.cas = cas;
    }

    public Pedina_grafica getPg() {
	return pg;
    }

    public void setPg(Pedina_grafica pg) {
	this.pg = pg;
    }
}
