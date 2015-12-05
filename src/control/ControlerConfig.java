package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceConfig;
import view.InterfacePrincipale;

public class ControlerConfig implements ActionListener{

	public InterfacePrincipale ip;
	
	public ControlerConfig(InterfacePrincipale ip) {
		this.ip=ip;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new InterfaceConfig(ip);
	}

}
