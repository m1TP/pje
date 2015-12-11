package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfaceAnalyseGraphAll extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1063945887287412562L;
	
	public double tauxKNN;
	public double tauxBaye;
	
	public InterfaceAnalyseGraphAll(double tauxErreurKNN,double tauxErreurBay){
		this.tauxBaye=tauxErreurBay;
		this.tauxKNN=tauxErreurKNN;
		
		setLayout(new GridLayout(4, 1));
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");
		JLabel label1 = new JLabel("Taux d'erreur par classification KNN : "+df.format(tauxErreurKNN)+"%");
		JLabel label2 = new JLabel("Taux d'erreur par classification Bayesienne : "+df.format(tauxErreurBay)+"%");
		JLabel label3 = new JLabel("Les performances du classifieur Bayésien sont supérieur à celle du classifeur KNN pour cette base d'apprentissage");
		JLabel label4 = new JLabel("Les performances du classifieur KNN sont supérieur à celle du classifeur Bayésien pour cette base d'apprentissage");
		
		add(label1);
		add(label2);
		if(tauxBaye<tauxKNN)
			add(label3);
		else
			add(label4);
	}
	
}
