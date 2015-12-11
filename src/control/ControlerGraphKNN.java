package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphBaseKNN;
import view.InterfacePrincipale;

public class ControlerGraphKNN implements ActionListener {

	public int nbVoisinKNN;
	
	public ControlerGraphKNN(InterfacePrincipale interfacePrincipale){
		this.nbVoisinKNN=interfacePrincipale.nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new InterfaceGraphBaseKNN(nbVoisinKNN); 		
	}
}
