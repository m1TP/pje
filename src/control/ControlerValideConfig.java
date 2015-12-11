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
		boolean [] tab = {false,false};
		if(ic.getParamB1().isSelected())
			tab[0]=true;
		if(ic.getParamB2().isSelected())
			tab[1]=true;
		new InterfacePrincipale(Integer.parseInt(ic.getNbVoisin().getText()),tab);
		this.ip.closeInterface();
		this.ic.dispose();
	}

}
