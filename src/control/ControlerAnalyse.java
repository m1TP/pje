package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraph;

public class ControlerAnalyse implements ActionListener {

	public ControlerAnalyse(){
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Pour l'instant en test
		new InterfaceGraph("police"); 		
	}

}
