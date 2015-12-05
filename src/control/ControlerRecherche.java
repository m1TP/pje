package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import model.TweetSkeleton;
import essai.ControllerCSV;
import essai.KNN;
import essai.KeywordsAnnotation;
import essai.RetrieveTweet;
import view.InterfaceErreur;
import view.InterfacePrincipale;
import view.InterfaceRecherche;

public class ControlerRecherche implements ActionListener {


	public InterfacePrincipale view;
	public ButtonGroup optionAnnotation;
	public int nbVoisinKNN;
	
	public ControlerRecherche(InterfacePrincipale view, ButtonGroup optionAnnotation){
		this.view = view;
		this.optionAnnotation = optionAnnotation;
		this.nbVoisinKNN = view.nbVoisinKNN;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {	
			if(!view.getMotCle().getText().equals("") && !view.getNbTweet().getText().equals("")){
				if(optionAnnotation.getSelection().getMnemonic() == 0){
					
					//Annotation manuelle
					new InterfaceRecherche(
						new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText())), nbVoisinKNN);
				
				}else if(optionAnnotation.getSelection().getMnemonic() == 2){
					
					//Annotation KNN
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					ControllerCSV db = new ControllerCSV(new File("db/"+view.getMotCle().getText()+".csv"));
					
					//KNN seulement 
					if(db.getListTweet().size()<1){
						JOptionPane.showMessageDialog(null,"Aucune base de donnée sur ce sujet pour classifier les tweets !");
						return;
					}
					
					
					//Recupération des tweets dans la base et création d'une list de TweetSkeleton
					List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
					for(String tweet : db.getListTweet()){
						TweetSkeleton tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, view.getMotCle().getText());
						tweetDb.add(tmp);
					}
					
					
					for(TweetSkeleton elt : list)
					{
						TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
						tmp.setData((tmp.cleanData(tmp.getText())));
						//(tweetDb.size()*2/3)
						KNN.knn_annotation(tmp, 3, tweetDb);
						elt.setAnnotation(tmp.getAnnotation());
						System.out.println(elt);
						System.out.println(tmp);
					}
					
					new InterfaceRecherche(list,nbVoisinKNN);
					
				}else if(optionAnnotation.getSelection().getMnemonic() == 3){
					
					//Annotation Bayesienne
					//En attente
					
				}else{
				
					//Annotation automatique
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					KeywordsAnnotation methode = new KeywordsAnnotation();
					
					for(TweetSkeleton elt : list)
					{
						TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
						tmp.setData((tmp.cleanData(tmp.getText())));
						
						methode.annotateTweet(tmp);
						
						elt.setAnnotation(tmp.getAnnotation());
					}
					
					new InterfaceRecherche(list,nbVoisinKNN);
				}
					
			}else{
				new InterfaceErreur("Erreur de formulaire - Indiquez un sujet et un nombre de tweet");
			}
		} catch (Exception e) {	e.printStackTrace();}
		
	}
}
