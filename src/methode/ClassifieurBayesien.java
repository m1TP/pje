package methode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csv.ControllerCSV;
import model.TweetSkeleton;
import util.Proba;
import util.Triplet;

/**
 * Class for the classification of the retrieved tweet, using Naive Bayes classifier 
 */
public class ClassifieurBayesien {

	public String [] mot_compose;
	
	public ClassifieurBayesien(){
		this.mot_compose = loadCSV("mot_compose.txt").split(",");
	}
	
	/**
	 * Precompute a map containing the proba for all the words and all the classes.
	 * 
	 * @param query The query used to get the tweet
	 * @return 
	 */
	public Proba precomputeProba(String query, boolean param)
	{
		Map<String,Triplet> res = new HashMap<String,Triplet>();
		ControllerCSV csv = new ControllerCSV(new File("db/"+query+".csv"));
		List<TweetSkeleton> lts = csv.StringToTweet();
		
		
		double nbPosTotal = 0;
		double nbNegTotal = 0;
		double nbNeuTotal = 0;
		
		//on compte les occurences
		for(TweetSkeleton ts : lts)
		{
			if(ts.getAnnotation()==0)
				nbNegTotal++;
			else if(ts.getAnnotation()==2)
				nbNeuTotal++;
			else
				nbPosTotal++;
		
			//Traitement des bi-gramme, tri-gramme ...
			String tmpText = ts.getText();
			
			if(param){
				
				for(int i=0;i<this.mot_compose.length;i++){
					
					while(tmpText.contains(this.mot_compose[i])){
	
						tmpText = tmpText.replaceFirst(this.mot_compose[i], "");
						tmpText = tmpText.replaceAll("  ", " ");
	
						if(res.containsKey(this.mot_compose[i]))
						{
							Triplet t = res.get(this.mot_compose[i]);
							if(ts.getAnnotation()==0)
								t.neg++;
							else if(ts.getAnnotation()==2)
								t.pos++;
							else
								t.neu++;
							res.replace(this.mot_compose[i], t);
						}
						else
						{
							Triplet t = new Triplet(0,0,0);
							if(ts.getAnnotation()==0)
								t.neg++;
							else if(ts.getAnnotation()==2)
								t.pos++;
							else
								t.neu++;
							res.put(this.mot_compose[i], t);
						}
					}
						
				}
			}
			
			String[] tab= tmpText.split(" ");
			
			for(String s : tab)
			{
				if(!s.equals("@") && !s.equals("RT") && !s.equals("URL") && !s.equals(".") && !s.equals("#") && !s.equals(",")
						&&	!s.equals("?") &&	!s.equals(":") &&	!s.equals("!") &&	!s.equals(" ")  &&	!s.equals("	") &&	!s.equals(")") &&	!s.equals("")    )
					if(res.containsKey(s))
					{
						Triplet t = res.get(s);
						if(ts.getAnnotation()==0)
							t.neg++;
						else if(ts.getAnnotation()==2)
							t.pos++;
						else
							t.neu++;
						res.replace(s, t);
					}
					else
					{
						Triplet t = new Triplet(0,0,0);
						if(ts.getAnnotation()==0)
							t.neg++;
						else if(ts.getAnnotation()==2)
							t.pos++;
						else
							t.neu++;
						res.put(s, t);
					}
			}
		}
		
		//on divise par le nombre total
		for(String s : res.keySet())
		{
			Triplet t = res.get(s);
			t.neg /= nbNegTotal;
			t.neu /= nbNeuTotal;
			t.pos /= nbPosTotal;
			
			res.replace(s, t);
		}
		
		return new Proba(
				(nbNegTotal/(nbNegTotal+nbNeuTotal+nbPosTotal)),
				(nbNeuTotal/(nbNegTotal+nbNeuTotal+nbPosTotal)),
				(nbPosTotal/(nbNegTotal+nbNeuTotal+nbPosTotal)),
				res);
	}
	
	public void bayesienne_annotation(TweetSkeleton ts, Proba proba, List<TweetSkeleton> learningDB, boolean [] param){
		
		double probPos = 0.0;
		double probNeu = 0.0;
		double probNeg = 0.0;
		
		Map<String,Triplet> res = proba.probaWord;
		
		//Traitement des bi-gramme, tri-gramme ...
		String tmpText = ts.getText();
		
		if(param[1]){
			
			for(int i=0;i<this.mot_compose.length;i++){
				
				while(tmpText.contains(this.mot_compose[i])){

					tmpText = tmpText.replaceFirst(this.mot_compose[i], "");
					tmpText = tmpText.replaceAll("  ", " ");

					if(res.containsKey(this.mot_compose[i])){
						Triplet t = res.get(this.mot_compose[i]);
						
						if(probPos == 0.0){
							probPos = t.pos;
						}else{
							probPos *= t.pos;
						}
						if(probNeu == 0.0){
							probNeu = t.neu;
						}else{
							probNeu *= t.neu;
						}
						if(probNeg == 0.0){
							probNeg = t.neg;
						}else{
							probNeg *= t.neg;
						}	
					}
				}
					
			}
		}
		
		String[] tabMot= tmpText.split(" ");
	
		for(String s : tabMot ){
			
			if(param[0]){
				//On ne prend en compte que les mots d'une longueur > à 3
				if(s.length()>3){
					//Pour chaque mot, on détermine la probabilité de la classe
					if(!s.equals("@") && !s.equals("RT") && !s.equals("URL") && !s.equals(".") && !s.equals("#") && !s.equals(",")
							&&	!s.equals("?") &&	!s.equals(":") &&	!s.equals("!") &&	!s.equals(" ")  &&	!s.equals("	") &&	!s.equals(")") &&	!s.equals("")    )
						if(res.containsKey(s)){
							Triplet t = res.get(s);
							
							if(probPos == 0.0){
								probPos = t.pos;
							}else{
								probPos *= t.pos;
							}
							if(probNeu == 0.0){
								probNeu = t.neu;
							}else{
								probNeu *= t.neu;
							}
							if(probNeg == 0.0){
								probNeg = t.neg;
							}else{
								probNeg *= t.neg;
							}	
						}
				}
			}
		}
		
		//P(t/c)*P(c)
		probNeg *= proba.probaNeg;
		probPos *= proba.probaPos;
		probNeu *= proba.probaNeu;

		if (probNeg>Math.max(probNeu,probPos))
			ts.setAnnotation(0);
		else if (probPos>Math.max(probNeu, probNeg))
			ts.setAnnotation(2);
		else
			ts.setAnnotation(1);
	}
	
	private String loadCSV(String file) {
		BufferedReader br = null;
		String line = "";
		String res = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) 
			{
				res+=line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}	
	
	
	
	
	
	/*	TEST !!!!!!!!
	 * public static void main(String[] args) {
	//	Proba test = precomputeProba("amour");
		TweetSkeleton newTweet = new TweetSkeleton(0000007, "SuperMoi", "mal", null,"amour");
		ControllerCSV db = new ControllerCSV(new File("db/amour.csv"));
		List<TweetSkeleton> tweetDb = new ArrayList<TweetSkeleton>();
		for(String tweet : db.getListTweet()){
			TweetSkeleton tmp = null;
			try {
				tmp = TweetSkeleton.converterCSVTweetSkeleton(tweet, "amour");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tweetDb.add(tmp);
		}
		bayesienne_annotation(newTweet, precomputeProba(newTweet.getQuery()), tweetDb);
		
		System.out.println("Tweet estimé : "+newTweet.getAnnotation());
	}*/

}
