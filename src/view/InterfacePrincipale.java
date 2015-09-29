package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window.Type;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.ControlerRecherche;

public class InterfacePrincipale {
	private JFrame frame;
	private JPanel panelSujet;
	private JLabel labelSujet;
	public JTextField sujet;
	private JPanel panelNbTweet;
	private JLabel labelNbTweet;
	public JTextField nbTweet;
	private JPanel panelBouton;
	private JButton boutonRecherche;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem proprieties;

	@SuppressWarnings("unused")
	private int nbtweet;
	@SuppressWarnings("unused")
	private String sujettweet;
	
	public InterfacePrincipale(){
		
		frame = new JFrame("API Twitter");
		frame.setType(Type.UTILITY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setSize(300,200);
		frame.setLayout(new GridLayout(5,1));
		frame.setResizable(false);
		
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		proprieties = new JMenuItem("Proprietes");
		/** Controller a implementer **/
		//m.addActionListener(new Controller());
		menu.addSeparator();
		menu.add(proprieties);
		menuBar.add(menu);
		frame.add(menuBar);
		
		//ajout des composants de la fenetre
		labelSujet= new JLabel("Mot-cle :");
		labelNbTweet = new JLabel("Nombre de tweet:");
		sujet = new JTextField("Equipe de France de rugby");
		nbTweet = new JTextField("1");
		
		panelSujet = new JPanel();
		panelSujet.add(labelSujet);
		panelSujet.add(sujet);
		panelNbTweet = new JPanel();
		panelNbTweet.add(labelNbTweet);
		panelNbTweet.add(nbTweet);

		frame.add(panelSujet);
		frame.add(panelNbTweet);
		
		//creation des panels
		panelBouton = new JPanel();
		panelBouton.setBackground(new Color(102,204,255));
		boutonRecherche = new JButton("Rechercher");
		boutonRecherche.addActionListener(new ControlerRecherche(this));					
		
		
		panelBouton.add(boutonRecherche);
		frame.add(panelBouton);
		
		frame.setVisible(true);
		
	}
	
	/***************** getter ***************************/
	public JTextField getMotCle() {	return sujet;}
	public JTextField getNbTweet() {return nbTweet;}
	

}
