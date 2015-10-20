package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window.Type;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.ControlerExit;
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
	private JMenuItem exit;
	private ButtonGroup optionAnnotation;
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	
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
		
		
		
		
		
		/**
		 * Realisation d'un menu avec la possibilitée de modifier l'annotation des tweets souhaités.
		 */
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		
		optionAnnotation = new ButtonGroup();
		radio1 = new JRadioButton("Annotation manuelle",true);
		radio1.setMnemonic(0);
		radio2 = new JRadioButton("Annotation automatique");
		radio2.setMnemonic(1);
		radio3 = new JRadioButton("Annotation par KNN");
		radio3.setMnemonic(2);
		optionAnnotation.add(radio1);
		optionAnnotation.add(radio2);
		optionAnnotation.add(radio3);
		menu.add(radio1);
		menu.add(radio2);
		menu.add(radio3);
		
		menu.addSeparator();
		
		exit = new JMenuItem("Exit");
		/** Controller a implementer **/
		exit.addActionListener(new ControlerExit(frame));
		menu.add(exit);
		
		menuBar.add(menu);
		frame.add("North",menuBar);
		
		
		
		
		
		
		/**
		 * Ajout des composants de la fenetre
		 */
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
		boutonRecherche = new JButton("Search");
		boutonRecherche.addActionListener(new ControlerRecherche(this,optionAnnotation));					
		
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
