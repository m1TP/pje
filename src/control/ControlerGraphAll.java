package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphBaseAll;

public class ControlerGraphAll implements ActionListener {

	public boolean [] param;
	public int nbVoisin;
	
	public ControlerGraphAll(int nbVoisin, boolean[] param) {
		this.param=param;
		this.nbVoisin=nbVoisin;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new InterfaceGraphBaseAll(this.nbVoisin,this.param);
	}
}
