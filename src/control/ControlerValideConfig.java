package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.InterfaceConfig;
import view.InterfacePrincipale;

public class ControlerValideConfig implements ActionListener{

	public InterfacePrincipale ip;
	public InterfaceConfig ic;
	
	public ControlerValideConfig(InterfacePrincipale ip, InterfaceConfig ic) {
		this.ip=ip;
		this.ic=ic;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new InterfacePrincipale(Integer.parseInt(ic.getNbVoisin().getText()));
		this.ip.closeInterface();
		this.ic.dispose();
	}

}
