package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import model.TweetSkeleton;

import view.InterfacePrincipale;
import essai.ControllerCSV;
import essai.RetrieveTweet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RetrieveTweet rt = new RetrieveTweet();
		//ControllerCSV csv = new ControllerCSV(new File("db.csv"));
		//csv.doAll(rt.queryTwitter("rugby",20));
		rt.queryTwitter("rugby",2);
		//new InterfacePrincipale();
		
		//TweetSkeleton ts = new TweetSkeleton(0, "", "", new Date(), "");
		//ts.nettoyage("TOTO \t@a:\n @amlkdmls:::::!!!: \n ;z 22%z%2 ;zz\": !rr rr! r!r $5 $dd$32.1 €14 €1g 2 2% 22% 3.%  RT:dld #toto");
	}

}
