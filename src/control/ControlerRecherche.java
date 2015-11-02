package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;

import model.TweetSkeleton;
import essai.ControllerCSV;
import essai.KNN;
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
	
	public Date convertirDate(String date) throws ParseException{
		int year = Integer.parseInt(date.substring(date.length()-4));
		
		String month = date.substring(4, 7);
		System.out.println(date.substring(4, 7));
		int day = Integer.parseInt(date.substring(8, 10));
		System.out.println(date.substring(8, 10));
		int hh = Integer.parseInt(date.substring(11,13));
		System.out.println(date.substring(11, 13));
		int mm = Integer.parseInt(date.substring(14,16));
		System.out.println(date.substring(14, 16));
		int ss = Integer.parseInt(date.substring(17,19));
		System.out.println(date.substring(17, 19) + "\n");
		
		String monthNum = "01";
		switch(month){
			case "Jan": monthNum = "01";
						break;
			case "Feb": monthNum = "02";
						break;
			case "Mar": monthNum = "03";
						break;
			case "Apr": monthNum = "04";
						break;
			case "May": monthNum = "05";
						break;
			case "Jun": monthNum = "06";
						break;
			case "Jul": monthNum = "07";
						break;
			case "Aug": monthNum = "08";
						break;
			case "Sep": monthNum = "09";
						break;
			case "Oct": monthNum = "10";
						break;
			case "Nov": monthNum = "11";
						break;
			case "Dec": monthNum = "12";
						break;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = day+"-"+monthNum+"-"+year+" "+hh+":"+mm+":"+ss;

		return sdf.parse(dateInString);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {	
			if(!view.getMotCle().getText().equals("") && !view.getNbTweet().getText().equals("")){
				if(optionAnnotation.getSelection().getMnemonic() == 0){
					
					//Annotation manuelle
					new InterfaceRecherche(
						new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText())));
				
				}else if(optionAnnotation.getSelection().getMnemonic() == 2){
					
					//Annotation KNN
					List<TweetSkeleton> list =  new RetrieveTweet().queryTwitter(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
					ControllerCSV db = new ControllerCSV(new File("db/"+view.getMotCle().getText()+".csv"));
					System.out.println("Je suis passer par l� !");
					
					//Recup�ration des tweets dans la base et cr�ation d'une list de TweetSkeleton
					List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
					for(String tweet : db.getListTweet()){
						/*System.out.println("Ligne Tweet : "+tweet);
						System.out.println("Id Tweet : "+tweet.substring(0, 18));
						System.out.println("User Tweet : "+tweet.substring(19, 19+tweet.substring(19, tweet.length()-38).lastIndexOf(";")));
						System.out.println("Text Tweet : "+tweet.substring(19, tweet.length()-38).substring(tweet.substring(19, tweet.length()-33-view.getMotCle().getText().length()).indexOf(";")+1));
						System.out.println("Date Tweet : "+tweet.substring(tweet.length()-32-view.getMotCle().getText().length(), tweet.length()-3-view.getMotCle().getText().length()));
						System.out.println("Query Tweet : "+tweet.substring(tweet.length()-2-view.getMotCle().getText().length(), tweet.length()-2));
						System.out.println("Annotation Tweet :"+tweet.substring(tweet.lastIndexOf(";")+1));
						System.out.println(' ');*/
						
						Date d = convertirDate(tweet.substring(tweet.length()-32-view.getMotCle().getText().length(), tweet.length()-3-view.getMotCle().getText().length()));
						
						TweetSkeleton tmp = new TweetSkeleton(
								Long.parseLong(tweet.substring(0, 18)), 
								tweet.substring(19, 19+tweet.substring(19, tweet.length()-38).lastIndexOf(";")), 
								tweet.substring(19, tweet.length()-38).substring(tweet.substring(19, tweet.length()-32-view.getMotCle().getText().length()).indexOf(";")), 
								d,
								tweet.substring(tweet.length()-2-view.getMotCle().getText().length(), tweet.length()-2)
								);
						
						tweetDb.add(tmp);
					}
					
					
					for(TweetSkeleton elt : list)
					{
						TweetSkeleton tmp = new TweetSkeleton(elt.getId(), elt.getUser(), elt.getText(), elt.getDate(), elt.getQuery());
						tmp.setData((tmp.cleanData(tmp.getText())));
						
						KNN.knn_annotation(tmp, (tweetDb.size()*2/3), tweetDb);
						elt.setAnnotation(tmp.getAnnotation());
						System.out.println(elt);
						System.out.println(tmp);
					}
					
					new InterfaceRecherche(list);
					
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
						System.out.println(elt);
						System.out.println(tmp);
					}
					
					new InterfaceRecherche(list);
				}
					
			}	
		} catch (Exception e) {	e.printStackTrace();}
		
	}
}
