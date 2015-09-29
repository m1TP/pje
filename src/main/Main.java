package main;

import java.io.File;
import java.util.ArrayList;

import essai.ControllerCSV;
import essai.RetrieveTweet;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RetrieveTweet rt = new RetrieveTweet();
		ControllerCSV csv = new ControllerCSV(new File("db.csv"));
		//csv.doAll(rt.gogo());
		rt.gogo();
	
	}

}
