package grafica;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import logica.Pedina;

public class Pedina_grafica extends JLabel {

    Pedina ped;
    /*
     * image icon statiche e final in modo da caricarle solo una volta per
     * partita invece che per ogni pedina, scalate in modo da avere dimensione
     * 50x50
     */
    public static final ImageIcon pedNero = new ImageIcon(new ImageIcon(
	    Pedina_grafica.class.getClassLoader().getResource("ped_nera.png"))
	    .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));

    public static final ImageIcon pedNeroDamone = new ImageIcon(new ImageIcon(
	    Pedina_grafica.class.getClassLoader().getResource(
		    "ped_nera_damone.png")).getImage().getScaledInstance(50,
	    50, java.awt.Image.SCALE_SMOOTH));

    public static final ImageIcon pedBianco = new ImageIcon(
	    new ImageIcon(Pedina_grafica.class.getClassLoader().getResource(
		    "ped_bianca.png")).getImage().getScaledInstance(50, 50,
		    java.awt.Image.SCALE_SMOOTH));

    public static final ImageIcon pedBiancoDamone = new ImageIcon(
	    new ImageIcon(Pedina_grafica.class.getClassLoader().getResource(
		    "ped_bianca_damone.png")).getImage().getScaledInstance(50,
		    50, java.awt.Image.SCALE_SMOOTH));

    public Pedina_grafica(Pedina p) {
	ped = p;
	setImg();
	setVisible(true);
    }

    public void setImg() {
	if (!ped.isDamone()) {
	    if (ped.isNera())
		this.setIcon(pedNero);
	    else
		this.setIcon(pedBianco);
	} else {
	    if (ped.isNera())
		this.setIcon(pedNeroDamone);
	    else
		this.setIcon(pedBiancoDamone);
	}
    }
}
