package view;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
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
	
	private JPanel panelNbVoisin;
	private JPanel panelButton;
	private JLabel label;
	private JTextField nbVoisin;
	private JButton button;
	
	public InterfaceConfig(InterfacePrincipale ip){
		
		setTitle("Configuration des méthodes de classification");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		
		panelNbVoisin = new JPanel();
		panelNbVoisin.setLayout(new FlowLayout());
		label = new JLabel("Nombres de plus proches voisins (0 = par défauts):");
		nbVoisin = new JTextField(""+ip.nbVoisinKNN);
		nbVoisin.setPreferredSize(new Dimension(40, 20));
		
		panelNbVoisin.add(label);
		panelNbVoisin.add(nbVoisin);
		
		getContentPane().add(panelNbVoisin);
		
		panelButton = new JPanel();
		button = new JButton("Valider Config");
		button.addActionListener(new ControlerValideConfig(ip,this));
		
		panelButton.add(button);
		
		getContentPane().add(panelButton);
		
		setPreferredSize(new Dimension(400, 120));
		setVisible(true);
		pack();
	}

	public JTextField getNbVoisin() {
		return nbVoisin;
	}

	public void setNbVoisin(JTextField nbVoisin) {
		this.nbVoisin = nbVoisin;
	}
}
