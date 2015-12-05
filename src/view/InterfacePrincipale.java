package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window.Type;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.ControlerConfig;
import control.ControlerExit;
import control.ControlerInterfaceAnalyse;
import control.ControlerRecherche;


public class InterfacePrincipale {
	private JFrame frame;
	private JPanel panelFrame;
	private JPanel panelBoiteOutil;
	
	private JPanel panelIcone;
	private JLabel labelIcone;
	private JPanel panelSujet;
	private JLabel labelSujet;
	private JTextField sujet;
	private JPanel panelNbTweet;
	private JLabel labelNbTweet;
	private JTextField nbTweet;
	
	
	private JPanel panelBouton;
	private JButton boutonRecherche;
	private JMenuBar menuBar;
	private JMenu menuOption;
	private JMenu menuAnalyse;
	private JMenuItem exit;
	private JMenuItem config;
	private JMenuItem analyseKnn;
	private JMenuItem analyseBayesienne;
	private JMenuItem analyseKnnBayesienne;
	private ButtonGroup optionAnnotation;
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	private JRadioButton radio4;
	
	@SuppressWarnings("unused")
	private int nbtweet;
	@SuppressWarnings("unused")
	private String sujettweet;
	
	public int nbVoisinKNN;
	
	public InterfacePrincipale(int nbVoisinKNN){
		this.nbVoisinKNN=nbVoisinKNN;
		
		
		frame = new JFrame("API Twitter");
		frame.setType(Type.UTILITY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setSize(500,300);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		panelFrame = new JPanel();
		panelFrame.setLayout(new GridLayout(1,2));
		panelFrame.setBackground(new Color(102,204,255));
		
		
		
		
		
		/**
		 * Realisation d'un menu avec la possibilitï¿½e de modifier l'annotation des tweets souhaitï¿½s.
		 */
		menuBar = new JMenuBar();
		menuOption = new JMenu("Options");
		menuAnalyse = new JMenu("Analyse Experimentale");
		
		optionAnnotation = new ButtonGroup();
		radio1 = new JRadioButton("Annotation manuelle",true);
		radio1.setMnemonic(0);
		radio2 = new JRadioButton("Annotation automatique");
		radio2.setMnemonic(1);
		radio3 = new JRadioButton("Annotation par KNN");
		radio3.setMnemonic(2);
		radio4 = new JRadioButton("Annotation par Baysienne");
		radio4.setMnemonic(3);
		optionAnnotation.add(radio1);
		optionAnnotation.add(radio2);
		optionAnnotation.add(radio3);
		optionAnnotation.add(radio4);
		
		menuOption.add(radio1);
		menuOption.add(radio2);
		menuOption.add(radio3);
		menuOption.add(radio4);
		
		menuOption.addSeparator();
		
		config = new JMenuItem("Configuration");
		config.addActionListener(new ControlerConfig(this));
		menuOption.add(config);
		
		menuOption.addSeparator();
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ControlerExit(frame));
		menuOption.add(exit);
		
		
		
		analyseKnn = new JMenuItem("Reelle/KNN");
		analyseKnn.addActionListener(new ControlerInterfaceAnalyse(nbVoisinKNN));
		analyseBayesienne = new JMenuItem("Reelle/Bayesienne");
		//controler
		analyseKnnBayesienne = new JMenuItem("KNN/Bayesienne");
		//controler
		menuAnalyse.add(analyseKnn);
		menuAnalyse.add(analyseBayesienne);
		menuAnalyse.add(analyseKnnBayesienne);
		
		menuBar.add(menuOption);
		menuBar.add(menuAnalyse);
		frame.add("North",menuBar);
		
		
		
		
		
		
		/**
		 * Ajout des composants de la fenetre
		 */
		panelIcone = new JPanel(new BorderLayout());
		labelIcone = new JLabel(new ImageIcon("img/bird.png"));
		panelIcone.add(labelIcone,BorderLayout.WEST);

		panelSujet = new JPanel(new GridLayout(2,1));
		labelSujet= new JLabel(" Sujet des tweets à récupérer :");
		final String initialText = "Lille 1";
		sujet = new JTextField(initialText);
		sujet.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		       if (sujet.getText().equals(initialText)) {
		    	   sujet.selectAll();
		       }
		    }
		});
		panelSujet.add(labelSujet);
		panelSujet.add(sujet);
		
		panelNbTweet = new JPanel(new GridLayout(2,1));
		labelNbTweet = new JLabel(" Nombre de tweets souhaités :");
		final String initialNum = "1";
		nbTweet = new JTextField(initialNum);
		nbTweet.addFocusListener(new java.awt.event.FocusAdapter() {
		    public void focusGained(java.awt.event.FocusEvent evt) {
		       if (nbTweet.getText().equals(initialNum)) {
		    	   nbTweet.selectAll();
		       }
		    }
		});		
		panelNbTweet.add(labelNbTweet);
		panelNbTweet.add(nbTweet);
		
		panelBoiteOutil = new JPanel();
		panelBoiteOutil.setLayout(new GridLayout(4, 1));
		panelBoiteOutil.add(panelIcone);
		panelBoiteOutil.add(panelSujet);
		panelBoiteOutil.add(panelNbTweet);
		
		
		
		
		
		//creation des boutons
				

		boutonRecherche = new JButton();
		boutonRecherche.setBorderPainted(false);
		boutonRecherche.setContentAreaFilled(false);
		boutonRecherche.setIcon(new ImageIcon("img/bouton-rechercher-bleu.png"));
		boutonRecherche.addActionListener(new ControlerRecherche(this,optionAnnotation));					
		
		panelBouton = new JPanel();
		panelBouton.add(boutonRecherche);
		panelBoiteOutil.add(panelBouton);
		
		
		panelFrame.add(panelBoiteOutil);
		
		
		frame.add(panelFrame);
		frame.setVisible(true);
		
	}
	
	/***************** getter ***************************/
	public JTextField getMotCle() {	return sujet;}
	public JTextField getNbTweet() {return nbTweet;}
	
	public void closeInterface(){
		frame.dispose();
	}
	
}
