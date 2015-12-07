package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphSup;
import view.InterfacePrincipale;

public class ControlerInterfaceAnalyse implements ActionListener {

	public int nbVoisinKNN;
	
	public ControlerInterfaceAnalyse(InterfacePrincipale interfacePrincipale){
		System.out.println("Nb voisin pour l'analyse 1 : "+interfacePrincipale.nbVoisinKNN);
		this.nbVoisinKNN=interfacePrincipale.nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Pour l'instant en test
		new InterfaceGraphSup(nbVoisinKNN); 		
		System.out.println("Nb voisin pour l'analyse 2 : "+this.nbVoisinKNN);
	}
}
