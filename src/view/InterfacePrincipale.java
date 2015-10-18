package view;

import java.awt.BorderLayout;
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
	private JPanel panelFrame;
	private JPanel panelBoiteOutil;
	
	private JPanel panelSujet;
	private JLabel labelSujet;
	private JTextField sujet;
	private JPanel panelNbTweet;
	private JLabel labelNbTweet;
	private JTextField nbTweet;
	
	
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
		frame.setSize(500,200);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		panelFrame = new JPanel();
		panelFrame.setLayout(new GridLayout(1,2));
		panelFrame.setBackground(new Color(102,204,255));
		
		
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		proprieties = new JMenuItem("Proprietes");
		/** Controller a implementer **/
		//m.addActionListener(new Controller());
		menu.addSeparator();
		menu.add(proprieties);
		menuBar.add(menu);
		frame.add("North",menuBar);
		
		//ajout des composants de la fenetre
		panelSujet = new JPanel(new BorderLayout());
		labelSujet= new JLabel(" Mot-cle :");
		sujet = new JTextField("Equipe de France de rugby");
		panelSujet.add(labelSujet, BorderLayout.NORTH);
		panelSujet.add(sujet, BorderLayout.CENTER);
		
		panelNbTweet = new JPanel(new BorderLayout());
		labelNbTweet = new JLabel("Nombre de tweet:");
		nbTweet = new JTextField("1");
		panelNbTweet.add(labelNbTweet, BorderLayout.NORTH);
		panelNbTweet.add(nbTweet, BorderLayout.CENTER);
		
		panelBoiteOutil = new JPanel();
		panelBoiteOutil.setLayout(new GridLayout(3, 1));
		panelBoiteOutil.add(panelSujet);
		panelBoiteOutil.add(panelNbTweet);
		
		//creation des boutons
		boutonRecherche = new JButton("Rechercher");
		boutonRecherche.addActionListener(new ControlerRecherche(this));					
		
		panelBouton = new JPanel();
		panelBouton.add(boutonRecherche);
		panelBoiteOutil.add(panelBouton);
		
		
		
		panelFrame.add(panelBoiteOutil);
		
		//affichage des tweets
	/*	panelAffichage = new JPanel();
		
		String [] nameColumn = {"ID","Alias","Tweet","Date","Annotation"};
		Object[][] data = {
			    {"Mary", "Campione",
			     "Snowboarding", "21011958", -1},
			    {"Alison", "Huml",
			     "Rowing", new Integer(3), -1},
			    {"Kathy", "Walrath",
			     "Knitting", new Integer(2), -1},
			    {"Sharon", "Zakhour",
			     "Speed reading", new Integer(20), -1},
			    {"Philip", "Milne",
			     "Pool", new Integer(10), -1}
			};
		table = new JTable(data, nameColumn);
		
		
		panelAffichage.add(table);
		
		
		panelFrame.add(panelAffichage);*/
		frame.add(panelFrame);
		frame.setVisible(true);
		
	}
	
	/***************** getter ***************************/
	public JTextField getMotCle() {	return sujet;}
	public JTextField getNbTweet() {return nbTweet;}
	

}
