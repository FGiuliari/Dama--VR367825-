package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logica.Partita;

public class Main extends JFrame {
    private Partita p;

    // a
    public Main() {

	this.setSize(600, 600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }

    public void setMenuBar() {
	JMenuBar menu = new JMenuBar();
	JMenu azioni = new JMenu("Azioni");
	JMenuItem ricomincia = new JMenuItem("Ricomincia");
	ricomincia.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		initPartita();

	    }

	});
	JMenuItem backtomenu = new JMenuItem("Torna alla schermata iniziale");
	backtomenu.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		menuIniziale();

	    }

	});
	JMenuItem resa = new JMenuItem("Resa");
	resa.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		finePartita(false);

	    }

	});

	azioni.add(ricomincia);
	azioni.add(backtomenu);
	azioni.add(resa);
	menu.add(azioni);
	this.setJMenuBar(menu);
    }

    public void finePartita(boolean vintoNero) {
	this.getContentPane().removeAll();
	this.setJMenuBar(null);
	this.setLayout(new BorderLayout());
	JButton bota = new JButton("partitaFinita");
	add(bota);
	this.getContentPane().removeAll();
	this.setLayout(new BorderLayout());
	bota = new JButton("partitaFinita");
	bota.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		menuIniziale();

	    }

	});

	if (vintoNero) {
	    JOptionPane.showMessageDialog(this, "hanno vinto i neri");
	    bota.setIcon(new ImageIcon(new ImageIcon(Main.class
		    .getClassLoader().getResource("vittoria_neri.png"))
		    .getImage().getScaledInstance(600, 600,
			    java.awt.Image.SCALE_SMOOTH)));
	} else {
	    JOptionPane.showMessageDialog(this, "hanno vinto i bianchi");
	    bota.setIcon(new ImageIcon(new ImageIcon(Main.class
		    .getClassLoader().getResource("vittoria_bianchi.png"))
		    .getImage().getScaledInstance(600, 600,
			    java.awt.Image.SCALE_SMOOTH)));
	}
	add(bota);
	revalidate();
	repaint();
    }

    public void menuIniziale() {
	this.setLayout(new BorderLayout());
	this.setJMenuBar(null);
	this.getContentPane().removeAll();
	JButton bot = new JButton("Inizia Partita");
	bot.addActionListener((new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		initPartita();
	    }

	}));
	bot.setIcon(new ImageIcon(new ImageIcon(Main.class.getClassLoader()
		.getResource("Schermata_Iniziale.png")).getImage()
		.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH)));

	add(bot);
	revalidate();
	repaint();
    }

    public void initPartita() {
	this.getContentPane().removeAll();
	setMenuBar();
	p = new Partita(this);
	repaint();
	revalidate();

    }

    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Main a = new Main();
	a.menuIniziale();
    }

}
