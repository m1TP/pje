package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import csv.ControllerCSV;
import view.InterfaceAnnotationTweet;
import methode.ClassifieurBayesien;
import methode.KNN;
import methode.KeywordsAnnotation;
import model.TweetSkeleton;

public class ControlerRechercheUpdate implements ActionListener {

	private List<TweetSkeleton> list;
	private ButtonGroup optionAnnotation;
	private InterfaceAnnotationTweet ir;
	public int nbVoisinKNN;
	public boolean [] param;
	
	public ControlerRechercheUpdate(List<TweetSkeleton> list, ButtonGroup optionAnnotation, InterfaceAnnotationTweet ir) {
		this.list=list;
		this.optionAnnotation=optionAnnotation;
		this.ir=ir;
		this.nbVoisinKNN=ir.nbVoisinKNN;
		this.param=ir.tab;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(optionAnnotation.getSelection().getMnemonic() == 0){
			
			//Annotation manuelle
			ir.updateInterface(list,optionAnnotation.getSelection().getMnemonic());
		
		}else if(optionAnnotation.getSelection().getMnemonic() == 2){
			
			//Annotation KNN
			String sujet = list.get(0).getQuery();
			ControllerCSV db = new ControllerCSV(new File("db/"+sujet+".csv"));
			
			//KNN seulement 
			if(db.getListTweet().size()<1){
				JOptionPane.showMessageDialog(null,"Aucune base de donnée sur ce sujet pour classifier les tweets !");
				return;
			}
			
			//Recupération des tweets dans la base et création d'une list de TweetSkeleton
			List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
			for(String tweet : db.getListTweet()){
				TweetSkeleton tmp = null;
				try {
					tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, sujet);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tweetDb.add(tmp);
			}
			
			
			for(TweetSkeleton elt : list)
			{
				TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
				tmp.setData((tmp.cleanData(tmp.getText())));
				
				if(nbVoisinKNN>0)
					KNN.knn_annotation(tmp, nbVoisinKNN, tweetDb);
				else
					KNN.knn_annotation(tmp, tweetDb.size()/3 , tweetDb);
				
				elt.setAnnotation(tmp.getAnnotation());
			
			}
			
			ir.updateInterface(list,optionAnnotation.getSelection().getMnemonic());
			
		}else if(optionAnnotation.getSelection().getMnemonic() == 3){
			
			//Annotation Bayesienne
			String sujet = list.get(0).getQuery();
			ControllerCSV db = new ControllerCSV(new File("db/"+sujet+".csv"));
			
			//Baye ssi 
			if(db.getListTweet().size()<1){
				JOptionPane.showMessageDialog(null,"Aucune base de donnée sur ce sujet pour classifier le(s) tweet(s) !");
				return;
			}

			//Recupération des tweets dans la base et création d'une list de TweetSkeleton
			List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
			for(String tweet : db.getListTweet()){
				TweetSkeleton tmp = null;
				try {
					tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, sujet);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tweetDb.add(tmp);
			}
			
			
			for(TweetSkeleton elt : list)
			{
				TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
				tmp.setData((tmp.cleanData(tmp.getText())));
			
				new ClassifieurBayesien().bayesienne_annotation(tmp, new ClassifieurBayesien().precomputeProba(tmp.getQuery(),this.param[1]), tweetDb, this.param);
				
				elt.setAnnotation(tmp.getAnnotation());
				
			}
			
			ir.updateInterface(list,optionAnnotation.getSelection().getMnemonic());
			
		}else{
		
			//Annotation automatique
			KeywordsAnnotation methode = new KeywordsAnnotation();
			
			for(TweetSkeleton elt : list)
			{
				TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
				tmp.setData((tmp.cleanData(tmp.getText())));
				
				methode.annotateTweet(tmp);
				
				elt.setAnnotation(tmp.getAnnotation());
			}
			
			ir.updateInterface(list,optionAnnotation.getSelection().getMnemonic());
		}
		
	}

}
