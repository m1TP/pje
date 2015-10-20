package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class ControlerExit implements ActionListener{

	private JFrame frame;
	
	public ControlerExit(JFrame frame){
		this.frame=frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.frame.dispose();		
	}
	
}
