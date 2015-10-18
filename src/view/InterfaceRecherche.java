package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import control.ControlerSaveManuel;
import model.TweetSkeleton;

public class InterfaceRecherche extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JPanel panelBouton;
	
	public InterfaceRecherche(List<TweetSkeleton> listTweets){
		super();
		
		setTitle("Tweets obtenu");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Realisation du tableau de tweets		
		String [] nameColumn = {"Alias","Tweet","Annotation"};
		Object [][] data = new Object[listTweets.size()][3];
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Positif");
		combo.addItem("Negatif");
		combo.addItem("Neutre");
		combo.addItem("Indefini");
		
		for(int i=0; i<listTweets.size();i++){
			data[i][0]=listTweets.get(i).getUser();
			data[i][1]=listTweets.get(i).getText();
			if(listTweets.get(i).getAnnotation()==-1)
				data[i][2]="Indefini";
			else if(listTweets.get(i).getAnnotation()==4)
				data[i][2]="Positif";
			else if(listTweets.get(i).getAnnotation()==2)
				data[i][2]="Neutre";	
			else
				data[i][2]="Negatif";
		}
		
		table = new JTable(data, nameColumn);
		
		
		TableColumn newColumn = table.getColumnModel().getColumn(2);
		newColumn.setCellEditor(new DefaultCellEditor(combo));
		repaint();
		
		
		table.getColumn("Alias").setMinWidth(50);	
		table.getColumn("Tweet").setMinWidth(800);	
		table.getColumn("Annotation").setMinWidth(100);
		table.getColumn("Annotation").setMaxWidth(100);
		JButton bouton = new JButton("Sauvegarder Resultat");
		bouton.addActionListener(new ControlerSaveManuel(listTweets,data,this));
		
		panelBouton = new JPanel();
		panelBouton.add(bouton);
		
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		getContentPane().add(panelBouton,BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(1000, 250));
		
		setVisible(true);
		
		pack();
	}

}
