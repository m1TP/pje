package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import view.InterfaceGraphBaseBaye;
import view.InterfaceGraphSupBaye;

public class ControlerGraphBayeGenerer implements ActionListener{

	public JComboBox<String> box;
	public InterfaceGraphBaseBaye frame;
	
	public ControlerGraphBayeGenerer(JComboBox<String> box, InterfaceGraphBaseBaye frame){
		this.box=box;
		this.frame=frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.deleteCompGraph();
		frame.add(new InterfaceGraphSupBaye(this.box.getSelectedItem().toString(),frame.param));
		frame.revalidate();
	}

}
