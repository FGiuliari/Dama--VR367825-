package grafica;

import java.awt.GridLayout;

import javax.swing.JLabel;

import logica.Scacchiera;

public class Scacchiera_grafica extends JLabel {
	public Casella_grafica[][] caselle;
	Scacchiera s;
	int lung;

	public Scacchiera_grafica(Scacchiera s) {
		this.s = s;
		this.lung = 8;
		caselle = new Casella_grafica[lung][lung];
		this.setSize(600, 600);
		setLayout(new GridLayout(lung, lung));
		popola();
		setVisible(true);
		// this.setResizable(false);
	}

	public void popola() {
		for (int i = 0; i < lung; i++) {
			for (int j = 0; j < lung; j++) {
				caselle[i][j] = new Casella_grafica(s.getCasella(i, j));
				add(caselle[i][j]);
			}
		}

	}

	public void updateCas() {
		for (Casella_grafica[] a : caselle)
			for (Casella_grafica b : a) {
				b.update();
				b.setImg();
			}

	}

	public void resetColorCas(int rigai, int coli) {
		caselle[rigai][coli].setImg();

	}

	public Casella_grafica getCasellaGrafica(int riga, int col) {
		return caselle[riga][col];
	}

	public Casella_grafica[][] getCaselleGrafiche() {
		return caselle;
	}

	public static void main() {

	}

}
