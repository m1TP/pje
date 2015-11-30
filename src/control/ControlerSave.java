package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import essai.ControllerCSV;
import model.TweetSkeleton;
import view.InterfaceRecherche;

/**
 * Controler qui permet de sauvegarder des données d'une liste de tweet annote sur un fichier csv 
 * @author Christopher
 *
 */
public class ControlerSave implements ActionListener {

	private Object [][] data;
	private InterfaceRecherche frame;
	private List<TweetSkeleton> listTweets;
	
	public ControlerSave(List<TweetSkeleton> listTweets,Object [][] data, InterfaceRecherche frame){
		this.listTweets=listTweets;
		this.data=data;
		this.frame=frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*
		 *  Modifie la liste de TweetSkeleton contenant les tweets
         *  en fonction de l'annotation souhaite
		 */
		for(int i=0;i<data.length;i++){
			if(data[i][2]=="Positif")
				listTweets.get(i).setAnnotation(2);
			else if(data[i][2]=="Neutre")
				listTweets.get(i).setAnnotation(1);
			else if(data[i][2]=="Negatif")
				listTweets.get(i).setAnnotation(0);	
			else
				listTweets.get(i).setAnnotation(-1);
		}
		
		//Sauvegarde les donnees sur le fichier csv
		if(data.length>0)
		{
			ControllerCSV csv = new ControllerCSV(
					new File("db"+File.separator+listTweets.get(0).getQuery().toLowerCase()+".csv"));
			csv.doAll(listTweets);
			
			ControllerCSV csvAll = new ControllerCSV(
					new File("db"+File.separator+"all.csv"));
			csvAll.doAll(listTweets);
			
		}
		//Fermeture de la fenetre
		frame.dispose();
	}

}
