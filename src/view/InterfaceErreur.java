package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class InterfaceErreur extends JFrame{


	private static final long serialVersionUID = -7683807383857182692L;

	public InterfaceErreur(String msg) {
		super();	
		JOptionPane.showMessageDialog(this, msg);
	}

}
