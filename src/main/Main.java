package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import model.TweetSkeleton;
import essai.ControllerCSV;
import essai.KeywordsAnnotation;
import essai.RetrieveTweet;
import view.InterfacePrincipale;

@SuppressWarnings("unused")
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//RetrieveTweet rt = new RetrieveTweet();
		//ControllerCSV csv = new ControllerCSV(new File("db.csv"));
		//csv.doAll(rt.queryTwitter("rugby",20));
		//rt.queryTwitter("rugby",20);
		new InterfacePrincipale(0);
		
		/*
		KeywordsAnnotation ka = new KeywordsAnnotation();
		 
		TweetSkeleton ts1 = new TweetSkeleton(0, "", "lol ", new Date(), "");
		ka.annotateTweet(ts1);
		System.out.println(ts1.getAnnotation());
		//*/
		//TweetSkeleton ts = new TweetSkeleton(0, "", "", new Date(), "");
		//System.out.println(ts.cleanData("TOTO \t@a:\n @amlkdmls:::::!!!: \n ;z 22%z%2 ;zz\": #R!CWé\"aRé2015 !rr rr! r!r $5 $dd$32.1 €14 €1g 2 2% 22% 3.%  RT:dld #toto http://eslkfsldkfjslkd.com https://214.com"));
	}

}
