package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphSup;

public class ControlerInterfaceAnalyse implements ActionListener {

	public int nbVoisinKNN;
	
	public ControlerInterfaceAnalyse(int nbVoisinKNN){
		this.nbVoisinKNN=nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Pour l'instant en test
		new InterfaceGraphSup(nbVoisinKNN); 		
	}
}
