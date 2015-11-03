package essai;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.TweetSkeleton;

import util.Proba;
import util.Triplet;

/**
 * Class for the classification of the retrieved tweet, using Naive Bayes classifier 
 */
public class ClassifieurBayesien {
	
	/**
	 * Precompute a map containing the proba for all the words and all the classes.
	 * 
	 * @param query The query used to get the tweet
	 * @return 
	 */
	public static Proba precomputeProba(String query)
	{
		Map<String,Triplet> res = new HashMap<String,Triplet>();
		ControllerCSV csv = new ControllerCSV(new File(query));
		List<TweetSkeleton> lts = csv.StringToTweet();
		
		
		int nbPosTotal = 0;
		int nbNegTotal = 0;
		int nbNeuTotal = 0;
		
		
		//on compte les occurences
		for(TweetSkeleton ts : lts)
		{
			if(ts.getAnnotation()==0)
				nbNegTotal++;
			else if(ts.getAnnotation()==2)
				nbNeuTotal++;
			else
				nbPosTotal++;
			
			String[] tab= ts.getText().split(" ");
			for(String s : tab)
			{
				if(res.containsKey(s))
				{
					Triplet t = res.get(s);
					if(ts.getAnnotation()==0)
						t.neg++;
					else if(ts.getAnnotation()==2)
						t.neu++;
					else
						t.pos++;
					res.replace(s, t);
				}
				else
				{
					Triplet t = new Triplet(0,0,0);
					if(ts.getAnnotation()==0)
						t.neg++;
					else if(ts.getAnnotation()==2)
						t.neu++;
					else
						t.pos++;
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
		return new Proba(nbNegTotal,nbNeuTotal,nbPosTotal,res);
	}

}
