package view;

import java.awt.Dimension;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.TweetSkeleton;
import essai.ControllerCSV;
import essai.KNN;

public class InterfaceGraph extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 04L;
	public int nbNegatifReelle;
	public int nbPositifReelle;
	public int nbNeutreReelle;
	public int nbNeutreEstimee;
	public int nbNegatifEstimee;
	public int nbPositifEstimee;
	
	public InterfaceGraph(String nomFichierCsv){
		super();
		setTitle("Evaluation classe Réelle/classe Estimée");
		setPreferredSize(new Dimension(500, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		//Lecture du fichier csv
		ControllerCSV db = new ControllerCSV(new File("db/"+nomFichierCsv+".csv"));
		
		//Recuperation des tweets dans la base et création d'une liste de TweetSkeleton
		List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
		for(String tweet : db.getListTweet()){
			
			nbPositifReelle = 0; nbNegatifReelle = 0; nbNeutreReelle = 0;
			nbPositifEstimee = 0;	nbNegatifEstimee = 0;	nbNeutreEstimee = 0;
			
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
					nbNegatifReelle++;
					break;
				case 1:
					nbNeutreReelle++;
					break;
				case 2:
					nbPositifReelle++;
					break;
				default:
					break;
			}
			
			//Classe estimee
			KNN.knn_annotation(tmp, (tweetDb.size()*2/3), tweetDb);
			switch(tmp.getAnnotation()){
			case 0:
				nbNegatifEstimee++;
				break;
			case 1:
				nbNeutreEstimee++;
				break;
			case 2:
				nbPositifEstimee++;
				break;
			default:
				break;
			}
			
		}

	}
	
}
