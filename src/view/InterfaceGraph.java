package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.TweetSkeleton;
import essai.ControllerCSV;
import essai.KNN;

public class InterfaceGraph extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 04L;
	
	public int [] polarite;
	
	
	public InterfaceGraph(String nomFichierCsv){
		
		setSize(new Dimension(550, 260));
		setLayout(new BorderLayout());
		
		//Lecture du fichier csv
		ControllerCSV db = new ControllerCSV(new File("./db/"+nomFichierCsv+".csv"));
		
		//Recuperation des tweets dans la base et creation d'une liste de TweetSkeleton
		List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
		
		/**
		* polarite[0]= nbPositifReelle
		* polarite[1]= nbNeutreReelle
		* polarite[2]= nbNegatifReelle
		* polarite[3]= nbPositifEstimee
		* polarite[4]= nbNeutreEstimee
		* polarite[5]= nbNegatifEstimee
		**/
		polarite = new int[6];
		for(int i=0;i<polarite.length;i++)
			polarite[i]=0;
		
		for(String tweet : db.getListTweet()){
			
			//Formatage du tweet
			TweetSkeleton tmp = null;
			try {
				tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, nomFichierCsv);
			} catch (ParseException e) {
				System.out.println("PROBLEME DE LECTURE DE CSV");
				e.printStackTrace();
			}
			
			switch(tmp.getAnnotation()){
				case 0:
					polarite[2]++;
					break;
				case 1:
					polarite[1]++;
					break;
				case 2:
					polarite[0]++;
					break;
				default:
					break;
			}
			
			tweetDb.add(tmp);
		}	
		
		int pospos = 0;
		int posneu = 0;
		int posneg = 0;
		int neupos = 0;
		int neuneu = 0;
		int neuneg = 0;
		int negpos = 0;
		int negneu = 0;
		int negneg = 0;
		
		for(TweetSkeleton tmp : tweetDb){
			
			int annotationTmp = tmp.getAnnotation();
			
			//Classe estimee
			KNN.knn_annotation(tmp, 2*tweetDb.size()/3, tweetDb);
			System.out.println(tmp.getAnnotation());
			switch(tmp.getAnnotation()){
			case 0:
				polarite[5]++;
				break;
			case 1:
				polarite[4]++;
				break;
			case 2:
				polarite[3]++;
				break;
			default:
				break;
			}
			
			//Classement dans la matrice
			if(annotationTmp == 2 && tmp.getAnnotation()==2){
				pospos++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==1){
				posneu++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==0){
				posneg++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==2){
				neupos++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==1){
				neuneu++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==0){
				neuneg++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==2){
				negpos++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==1){
				negneu++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==0){
				negneg++;
			}
			
		}
		
		add(new Graph(polarite),BorderLayout.CENTER);
		
		
		/**
		 * Matrice de confusion
		 */
		//JPanel matricePanel = new JPanel();
		String[] entetes = {" ","Classe Reelle - Positif","Neutre","Negatif"};
		Object[][] donnees = new Object[3][4];
		
		donnees[0][0]="Classe Estimee - Positif";
		donnees[0][1]=pospos;
		donnees[0][2]=neupos;
		donnees[0][3]=negpos;
		
		donnees[1][0]="Neutre";
		donnees[1][1]=posneu;
		donnees[1][2]=neuneu;
		donnees[1][3]=negneu;
		
		donnees[2][0]="Negatif";
		donnees[2][1]=posneg;
		donnees[2][2]=neuneg;
		donnees[2][3]=negneg;
		
		JTable matrice = new JTable(donnees,entetes){
			private static final long serialVersionUID = 3432869486539089276L;

			/** Pour ne plus pouvoir editer les colonnes ... */
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		
		matrice.getColumnModel().getColumn(0).setPreferredWidth(100);
		
		JScrollPane sp = new JScrollPane(matrice);
		sp.setPreferredSize(new Dimension(550,80));
		add(sp ,BorderLayout.AFTER_LAST_LINE);
		

	}
	
}
