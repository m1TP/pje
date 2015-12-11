package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import control.ControlerGraphBayeGenerer;

public class InterfaceGraphBaseBaye extends JFrame {

	private static final long serialVersionUID = 3674888765597531946L;

	private Container c;
	private JPanel panel;
	private JPanel panelGraph = new JPanel();
	private JComboBox<String> listeCSV;
	private JLabel label;
	public boolean [] param;
	
	public InterfaceGraphBaseBaye(boolean [] param){
		super();
		this.param=param;
		
		setTitle("Evaluation classe Réelle/Bayésienne");
		setSize(new Dimension(550, 300));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		c = this.getContentPane();
		
		panel = new JPanel();
		label = new JLabel("Annotation pour le CSV : ");
		panel.add(label);
		
		FilenameFilter csvFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		};
		
		File repertoire = new File("./db");
		String[] child = repertoire.list(csvFilter);
		listeCSV = new JComboBox<String>();
		for(int i=0;i<child.length;i++)
			listeCSV.addItem(child[i].substring(0,child[i].lastIndexOf(".csv")));
			
		 	
		listeCSV.setPreferredSize( new Dimension(100,20));
		listeCSV.addActionListener(new ControlerGraphBayeGenerer(listeCSV,this));
		
		panel.add(listeCSV);
		
		c.add(panel,BorderLayout.NORTH);
		
		panelGraph = new JPanel();
		panelGraph.add(new JLabel(" "));
		c.add(panelGraph,BorderLayout.SOUTH);
		
		
		setVisible(true);
	}
	
	public JPanel getCompGraph() {
		return panelGraph;
	}

	public void setCompGraph(JPanel jPanel) {
		this.panelGraph = jPanel;
	}
	
	public void deleteCompGraph(){
		this.c.remove(1);
	}
	
}
