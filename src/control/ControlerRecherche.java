package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import csv.ControllerCSV;
import methode.KNN;
import methode.ClassifieurBayesien;
import methode.KeywordsAnnotation;
import methode.RetrieveTweet;
import model.TweetSkeleton;
import view.InterfaceErreur;
import view.InterfacePrincipale;
import view.InterfaceAnnotationTweet;

public class ControlerRecherche implements ActionListener {


	public InterfacePrincipale view;
	public ButtonGroup optionAnnotation;
	public int nbVoisinKNN;
	public boolean [] tab;
	
	public ControlerRecherche(InterfacePrincipale view, ButtonGroup optionAnnotation){
		this.view = view;
		this.optionAnnotation = optionAnnotation;
		this.nbVoisinKNN = view.nbVoisinKNN;
		this.tab = view.param;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {	
			if(!view.getMotCle().getText().equals("") && !view.getNbTweet().getText().equals("")){
				if(optionAnnotation.getSelection().getMnemonic() == 0){
					
					//Annotation manuelle
					new InterfaceAnnotationTweet(
						new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText())), nbVoisinKNN,tab);
				
				}else if(optionAnnotation.getSelection().getMnemonic() == 2){
					
					//Annotation KNN
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					ControllerCSV db = new ControllerCSV(new File("db/"+view.getMotCle().getText()+".csv"));
					
					//KNN ssi 
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
					
						if(this.nbVoisinKNN==0){
							KNN.knn_annotation(tmp, (tweetDb.size()*2/3), tweetDb);	
						}else{
							KNN.knn_annotation(tmp, this.nbVoisinKNN, tweetDb);
						}
						
						elt.setAnnotation(tmp.getAnnotation());
						
					}
					
					new InterfaceAnnotationTweet(list,nbVoisinKNN,tab);
					
				}else if(optionAnnotation.getSelection().getMnemonic() == 3){
					
					//Annotation Bayesienne
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					ControllerCSV db = new ControllerCSV(new File("db/"+view.getMotCle().getText()+".csv"));
					
					//Baye ssi 
					if(db.getListTweet().size()<1){
						JOptionPane.showMessageDialog(null,"Aucune base de donnée sur ce sujet pour classifier le(s) tweet(s) !");
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
					
						new ClassifieurBayesien().bayesienne_annotation(tmp, new ClassifieurBayesien().precomputeProba(tmp.getQuery(),this.tab[1]), tweetDb, this.tab);
						
						elt.setAnnotation(tmp.getAnnotation());
						
					}
					
					new InterfaceAnnotationTweet(list,nbVoisinKNN,tab);
					
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
					
					new InterfaceAnnotationTweet(list,nbVoisinKNN,tab);
				}
					
			}else{
				new InterfaceErreur("Erreur de formulaire - Indiquez un sujet et un nombre de tweet");
			}
		} catch (Exception e) {	e.printStackTrace();}
		
	}
}
