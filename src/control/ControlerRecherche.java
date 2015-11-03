package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;

import model.TweetSkeleton;

import essai.KeywordsAnnotation;
import essai.RetrieveTweet;
import view.InterfacePrincipale;
import view.InterfaceRecherche;

public class ControlerRecherche implements ActionListener {


	public InterfacePrincipale view;
	public ButtonGroup optionAnnotation;
	
	public ControlerRecherche(InterfacePrincipale view, ButtonGroup optionAnnotation){
		this.view = view;
		this.optionAnnotation = optionAnnotation;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {	
			if(!view.getMotCle().getText().equals("") && !view.getNbTweet().getText().equals("")){
				if(optionAnnotation.getSelection().getMnemonic() == 0){
					//Annotation manuelle
					new InterfaceRecherche(
						new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText())));
				}else{
					//Annotation automatique
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					KeywordsAnnotation methode = new KeywordsAnnotation();
					
					for(TweetSkeleton elt : list)
					{
						TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
						//TweetSkeleton tmp = elt;
						tmp.setData((tmp.cleanData(tmp.getText())));
						methode.annotateTweet(tmp);
						elt.setAnnotation(tmp.getAnnotation());
					}
					
					new InterfaceRecherche(list);
				}
					
			}	
		} catch (Exception e) {	e.printStackTrace();}
		
	}
}
