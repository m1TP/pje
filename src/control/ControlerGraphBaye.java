package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphBaseBaye;

public class ControlerGraphBaye implements ActionListener{
	
	public boolean [] param;
	
	public ControlerGraphBaye(boolean [] param){
		this.param=param;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new InterfaceGraphBaseBaye(this.param);
	}
	
}
