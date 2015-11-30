package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphSup;

public class ControlerInterfaceAnalyse implements ActionListener {

	public ControlerInterfaceAnalyse(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Pour l'instant en test
		new InterfaceGraphSup(); 		
	}
}
