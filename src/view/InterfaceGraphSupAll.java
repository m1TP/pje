package view;

import graph.GraphTrio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import methode.ClassifieurBayesien;
import methode.KNN;
import model.TweetSkeleton;
import csv.ControllerCSV;

public class InterfaceGraphSupAll extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7416253567498964407L;
	
	public int [] polarite;
	private JPanel panelInterne;
	private JPanel panelTab;
	
	public InterfaceGraphSupAll(String nomFichierCsv,int nbVoisin, boolean [] param){
		
		setSize(new Dimension(550, 260));
		setLayout(new BorderLayout());
		panelInterne = new JPanel();
		panelTab = new JPanel();
		panelInterne.setLayout(new GridLayout(3,1));
		panelTab.setLayout(new GridLayout(2,1));
		//Lecture du fichier csv
		ControllerCSV db = new ControllerCSV(new File("./db/"+nomFichierCsv+".csv"));
		
		//Recuperation des tweets dans la base et creation d'une liste de TweetSkeleton
		List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
		
		if(db.getListTweet().size()<1){
			JOptionPane.showMessageDialog(null,"Pas de données en base sur ce sujet pour classifier par Bayésien ce fichier... changez vos paramètres");
			return;
		}
			
		
		/**
		* polarite[0]= nbPositifReelle
		* polarite[1]= nbNeutreReelle
		* polarite[2]= nbNegatifReelle
		* polarite[3]= nbPositifEstimeeKNN
		* polarite[4]= nbNeutreEstimeeKNN
		* polarite[5]= nbNegatifEstimeeKNN
		* polarite[6]= nbNegatifEstimeeBayesien
		* polarite[7]= nbNegatifEstimeeBayesien
		* polarite[8]= nbNegatifEstimeeBayesien
		**/
		polarite = new int[9];
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
		
		int posposKNN = 0;
		int posneuKNN = 0;
		int posnegKNN = 0;
		int neuposKNN = 0;
		int neuneuKNN = 0;
		int neunegKNN = 0;
		int negposKNN = 0;
		int negneuKNN = 0;
		int negnegKNN = 0;
		int posposBay = 0;
		int posneuBay = 0;
		int posnegBay = 0;
		int neuposBay = 0;
		int neuneuBay = 0;
		int neunegBay = 0;
		int negposBay = 0;
		int negneuBay = 0;
		int negnegBay = 0;
		
		//KNN
		for(TweetSkeleton tmp : tweetDb){
			
			int annotationTmp = tmp.getAnnotation();
			
			//Classe estimee
			if(nbVoisin>0)
				KNN.knn_annotation(tmp, nbVoisin, tweetDb);
			else
				KNN.knn_annotation(tmp, tweetDb.size()/3 , tweetDb);
		
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
				posposKNN++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==1){
				posneuKNN++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==0){
				posnegKNN++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==2){
				neuposKNN++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==1){
				neuneuKNN++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==0){
				neunegKNN++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==2){
				negposKNN++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==1){
				negneuKNN++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==0){
				negnegKNN++;
			}
			
		}
		
		//Réinitialisation des liste
		tweetDb = new ArrayList<TweetSkeleton>();
		for(String tweet : db.getListTweet()){
	
			//Formatage du tweet
			TweetSkeleton tmp = null;
			try {
				tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, nomFichierCsv);
			} catch (ParseException e) {
				System.out.println("PROBLEME DE LECTURE DE CSV");
				e.printStackTrace();
			}
			
			tweetDb.add(tmp);
		}	

		//Bayesien
		for(TweetSkeleton tmp : tweetDb){
			
			int annotationTmp = tmp.getAnnotation();
			
			//Classe estimee
			new ClassifieurBayesien().bayesienne_annotation(
					tmp,
					new ClassifieurBayesien().precomputeProba(nomFichierCsv,param[1]), 
					tweetDb,
					param
					);
		
			switch(tmp.getAnnotation()){
			case 0:
				polarite[8]++;
				break;
			case 1:
				polarite[7]++;
				break;
			case 2:
				polarite[6]++;
				break;
			default:
				break;
			}
			
			//Classement dans la matrice
			if(annotationTmp == 2 && tmp.getAnnotation()==2){
				posposBay++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==1){
				posneuBay++;
			}else if(annotationTmp == 2 && tmp.getAnnotation()==0){
				posnegBay++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==2){
				neuposBay++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==1){
				neuneuBay++;
			}else if(annotationTmp == 1 && tmp.getAnnotation()==0){
				neunegBay++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==2){
				negposBay++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==1){
				negneuBay++;
			}else if(annotationTmp == 0 && tmp.getAnnotation()==0){
				negnegBay++;
			}
			
		}
		
		panelInterne.add(new GraphTrio(polarite));
		
		
		/**
		 * Matrice de confusion
		 */
		String[] entetes1 = {"","Positif","Neutre","Negatif"};
		String[] entetes2 = {"","Positif","Neutre","Negatif"};
		
		Object[][] donnees1 = new Object[3][4];
		Object[][] donnees2 = new Object[3][4];
		
		donnees1[0][0]="KNN - Positif";
		donnees1[0][1]=posposKNN;
		donnees1[0][2]=neuposKNN;
		donnees1[0][3]=negposKNN;
		
		donnees1[1][0]="KNN - Neutre";
		donnees1[1][1]=posneuKNN;
		donnees1[1][2]=neuneuKNN;
		donnees1[1][3]=negneuKNN;
		
		donnees1[2][0]="KNN - Negatif";
		donnees1[2][1]=posnegKNN;
		donnees1[2][2]=neunegKNN;
		donnees1[2][3]=negnegKNN;
		
		donnees2[0][0]="Bayésien - Positif";
		donnees2[0][1]=posposBay;
		donnees2[0][2]=neuposBay;
		donnees2[0][3]=negposBay;
		
		donnees2[1][0]="Bayésien - Neutre";
		donnees2[1][1]=posneuBay;
		donnees2[1][2]=neuneuBay;
		donnees2[1][3]=negneuBay;
		
		donnees2[2][0]="Bayésien - Negatif";
		donnees2[2][1]=posnegBay;
		donnees2[2][2]=neunegBay;
		donnees2[2][3]=negnegBay;
		
		JTable matrice = new JTable(donnees1,entetes1){
			private static final long serialVersionUID = 3432869486539089276L;

			/** Pour ne plus pouvoir editer les colonnes ... */
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		
		matrice.getColumnModel().getColumn(0).setPreferredWidth(100);
		
		JScrollPane sp = new JScrollPane(matrice);
		sp.setPreferredSize(new Dimension(550,80));
		panelTab.add(sp);
		
		JTable matrice2 = new JTable(donnees2,entetes2){
			private static final long serialVersionUID = -6163847425149227992L;

			/** Pour ne plus pouvoir editer les colonnes ... */
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
	
		matrice2.getColumnModel().getColumn(0).setPreferredWidth(100);
		
		JScrollPane sp2 = new JScrollPane(matrice2);
		sp2.setPreferredSize(new Dimension(550,80));
		panelTab.add(sp2);
		
		panelInterne.add(panelTab);
		
		//Taux d'erreur
		double tauxErreurKNN = 100-(((double)posposKNN+(double)negnegKNN+(double)neuneuKNN)/((double)neuposKNN+(double)negposKNN+(double)posneuKNN+(double)negneuKNN+(double)posnegKNN+(double)neunegKNN+(double)posposKNN+(double)negnegKNN+(double)neuneuKNN))*100;
		double tauxErreurBay = 100-(((double)posposBay+(double)negnegBay+(double)neuneuBay)/((double)neuposBay+(double)negposBay+(double)posneuBay+(double)negneuBay+(double)posnegBay+(double)neunegBay+(double)posposBay+(double)negnegBay+(double)neuneuBay))*100;
				
		panelInterne.add(new InterfaceAnalyseGraphAll(tauxErreurKNN, tauxErreurBay));
		
		
		add(panelInterne,BorderLayout.CENTER);
		
	}
}
