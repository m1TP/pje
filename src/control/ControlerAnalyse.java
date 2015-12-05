package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraph;
import view.InterfaceGraphSup;

import javax.swing.JComboBox;

public class ControlerAnalyse implements ActionListener {

	public JComboBox<String> box;
	public InterfaceGraphSup frame;
	public int nbVoisinKNN;
	
	public ControlerAnalyse(JComboBox<String> box, InterfaceGraphSup frame){
		this.box=box;
		this.frame=frame;
		this.nbVoisinKNN=frame.nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Nombre d'élément :"+frame.getCompGraph());
		frame.deleteCompGraph();
		frame.add(new InterfaceGraph(this.box.getSelectedItem().toString(),nbVoisinKNN));
		frame.revalidate();
	}

}
