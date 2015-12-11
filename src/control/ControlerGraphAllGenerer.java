package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import view.InterfaceGraphBaseAll;
import view.InterfaceGraphSupAll;

public class ControlerGraphAllGenerer implements ActionListener{

	public JComboBox<String> box;
	public InterfaceGraphBaseAll frame;
	
	public ControlerGraphAllGenerer(JComboBox<String> box, InterfaceGraphBaseAll frame){
		this.box=box;
		this.frame=frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.deleteCompGraph();
		frame.add(new InterfaceGraphSupAll(this.box.getSelectedItem().toString(),frame.nbVoisin ,frame.param));
		frame.revalidate();
	}
}
