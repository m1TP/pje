package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import control.ControlerRechercheUpdate;
import control.ControlerSave;
import model.TweetSkeleton;

public class InterfaceRecherche extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JPanel panelBouton;
	private JMenuBar menuBar;
	private JMenu menuOption;
	private ButtonGroup optionAnnotation;
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	private JRadioButton radio4;
	
	public int nbVoisinKNN;
	
	public InterfaceRecherche(List<TweetSkeleton> listTweets, int nbVoisinKNN){
		super();
		this.nbVoisinKNN=nbVoisinKNN;
		
		setTitle("Tweets obtenu");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Ajout menu
		menuBar = new JMenuBar();
		menuOption = new JMenu("Options");
		
		optionAnnotation = new ButtonGroup();
		radio1 = new JRadioButton("Annotation manuelle",true);
		radio1.setMnemonic(0);
		radio1.addActionListener(new ControlerRechercheUpdate(listTweets, optionAnnotation, this));
		radio2 = new JRadioButton("Annotation automatique");
		radio2.setMnemonic(1);
		radio2.addActionListener(new ControlerRechercheUpdate(listTweets, optionAnnotation, this));
		radio3 = new JRadioButton("Annotation par KNN");
		radio3.setMnemonic(2);
		radio3.addActionListener(new ControlerRechercheUpdate(listTweets, optionAnnotation, this));
		radio4 = new JRadioButton("Annotation par Baysienne");
		radio4.setMnemonic(3);
		radio4.addActionListener(new ControlerRechercheUpdate(listTweets, optionAnnotation, this));
		optionAnnotation.add(radio1);
		optionAnnotation.add(radio2);
		optionAnnotation.add(radio3);
		optionAnnotation.add(radio4);
		
		menuOption.add(radio1);
		menuOption.add(radio2);
		menuOption.add(radio3);
		menuOption.add(radio4);
		
		menuBar.add(menuOption);
		getContentPane().add(menuBar,BorderLayout.NORTH);
		
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
			else if(listTweets.get(i).getAnnotation()==2)
				data[i][2]="Positif";
			else if(listTweets.get(i).getAnnotation()==1)
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
		JButton bouton = new JButton();
		bouton.setBorderPainted(false);
		bouton.setContentAreaFilled(false);
		bouton.setIcon(new ImageIcon("img/bouton-sauvegarde-bleu.png"));
		bouton.addActionListener(new ControlerSave(listTweets,data,this));
		
		panelBouton = new JPanel();
		panelBouton.add(bouton);
		
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		getContentPane().add(panelBouton,BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(1000, 250));
		
		setVisible(true);
		
		pack();
	}
	
	public void updateInterface(List<TweetSkeleton> listTweets){
		
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
			else if(listTweets.get(i).getAnnotation()==2)
				data[i][2]="Positif";
			else if(listTweets.get(i).getAnnotation()==1)
				data[i][2]="Neutre";	
			else
				data[i][2]="Negatif";
		}
				
		table = new JTable(data, nameColumn);
		
		TableColumn newColumn = table.getColumnModel().getColumn(2);
		newColumn.setCellEditor(new DefaultCellEditor(combo));
		
		table.getColumn("Alias").setMinWidth(50);	
		table.getColumn("Tweet").setMinWidth(800);	
		table.getColumn("Annotation").setMinWidth(100);
		table.getColumn("Annotation").setMaxWidth(100);
		
		getContentPane().add(new JScrollPane(table),1);
		revalidate();
	}

}
