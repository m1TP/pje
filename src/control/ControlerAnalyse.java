package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.InterfaceGraph;
import view.InterfaceGraphSup;

import javax.swing.JComboBox;

public class ControlerAnalyse implements ActionListener {

	public JComboBox<String> box;
	public InterfaceGraphSup frame;
	
	public ControlerAnalyse(JComboBox<String> box, InterfaceGraphSup frame){
		this.box=box;
		this.frame=frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Nombre d'�l�ment :"+frame.getCompGraph());
		frame.deleteCompGraph();
		frame.add(new InterfaceGraph(this.box.getSelectedItem().toString()));
		frame.revalidate();
	}

}
