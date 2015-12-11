package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraphSupKNN;
import view.InterfaceGraphBaseKNN;

import javax.swing.JComboBox;

public class ControlerGraphKNNGenerer implements ActionListener {

	public JComboBox<String> box;
	public InterfaceGraphBaseKNN frame;
	public int nbVoisinKNN;
	
	public ControlerGraphKNNGenerer(JComboBox<String> box, InterfaceGraphBaseKNN frame){
		this.box=box;
		this.frame=frame;
		this.nbVoisinKNN=frame.nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.deleteCompGraph();
		frame.add(new InterfaceGraphSupKNN(this.box.getSelectedItem().toString(),nbVoisinKNN));
		frame.revalidate();
	}

}
