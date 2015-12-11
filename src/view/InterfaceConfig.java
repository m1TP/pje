package view;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.ControlerValideConfig;

public class InterfaceConfig extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2043856456312280204L;
	
	private JPanel panelKNN;
	private JPanel panelKNNParam;
	private JPanel panelBayesien;
	private JPanel panelButton1;
	private JPanel panelButton2;
	private JLabel labelKNN;
	private JCheckBox paramB1;
	private JCheckBox paramB2;
	private JTextField nbVoisin;
	private JButton button;
	private Box panneauBayesien;
	private JLabel labelMethode1;
	private JLabel labelMethode2;
	private JLabel labelEspace;
	
	public InterfaceConfig(InterfacePrincipale ip){
		
		setTitle("Configuration des méthodes de classification");
		setType(Type.UTILITY);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3,1));
		
		
		panelKNN = new JPanel();
		panelKNNParam = new JPanel();
		panelKNN.setLayout(new GridLayout(2,1));
		panelKNNParam.setLayout(new FlowLayout());
		labelMethode1 = new JLabel("Paramètre KNN");
		labelKNN = new JLabel("Nombres de plus proches voisins (0 = par défauts):");
		nbVoisin = new JTextField(""+ip.nbVoisinKNN);
		nbVoisin.setPreferredSize(new Dimension(40, 20));
		panelKNN.add(labelMethode1);
		panelKNNParam.add(labelKNN);
		panelKNNParam.add(nbVoisin);
		panelKNN.add(panelKNNParam);
		
		getContentPane().add(panelKNN);
		
		
		panelBayesien = new JPanel();
		panelBayesien.setLayout(new GridLayout(2,1));
		labelMethode2 = new JLabel("Paramètres Bayésienne");
		panneauBayesien = Box.createVerticalBox();
		paramB1 = new JCheckBox("Ne pas comprendre les mots de moins de 3 lettres",ip.param[0]);
		paramB2 = new JCheckBox("Comprendre les bi-grammes",ip.param[1]);
		panelBayesien.add(labelMethode2);
		panneauBayesien.add(paramB1);
		panneauBayesien.add(paramB2);
		panelBayesien.add(panneauBayesien);
		
		getContentPane().add(panelBayesien);
		
		panelButton1 = new JPanel();
		panelButton2 = new JPanel();
		panelButton1.setLayout(new GridLayout(2,1));
		button = new JButton("Valider Config");
		button.addActionListener(new ControlerValideConfig(ip,this));
		labelEspace = new JLabel(" ");
		panelButton1.add(labelEspace);
		panelButton2.add(button);
		panelButton1.add(panelButton2);
		
		getContentPane().add(panelButton1);
		
		setPreferredSize(new Dimension(400, 300));
		setResizable(false);
		setVisible(true);
		pack();
	}

	public JCheckBox getParamB1() {
		return paramB1;
	}

	public JCheckBox getParamB2() {
		return paramB2;
	}

	public JTextField getNbVoisin() {
		return nbVoisin;
	}

	public void setNbVoisin(JTextField nbVoisin) {
		this.nbVoisin = nbVoisin;
	}
}
