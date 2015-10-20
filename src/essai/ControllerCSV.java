package essai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.TweetSkeleton;
/**
 * 
 * ControllerCSV
 * 
 * Handle reading parsing and writing new tweets to a specified csv file.
 *
 */
public class ControllerCSV {

	protected File file;
	protected Set<Long>  set;
	protected List<String> listTweet;
	protected List<String> listNewTweet;
	
	
	public ControllerCSV(File file)
	{
		this.file=file;
		this.set = new HashSet<Long>();
		this.listTweet = new ArrayList<String>();
		this.listNewTweet = new ArrayList<String>();
		readCSV();
	}
	
	/** Open the csv file specified, and reads its content.
	 * Builds a set of id, and a list of tweets.
	 * The usecase for the set is to compare the id of new tweet to detect doublon.
	 * 
	 */
	public void readCSV()
	{
		BufferedReader br = null;
		String separator = ";";
		String line = "";
		try {
			this.file.createNewFile(); //create the file if it didnt exists before, does nothing ig the file already exist
			br = new BufferedReader(new FileReader(this.file));
			while ((line = br.readLine()) != null) 
			{
				String[] tweet = line.split(separator);
				set.add(Long.parseLong(tweet[0]));
				listTweet.add(line);
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
	}
	
	/**
	 * Check if tweet already are in the database, and if they arent, add them to a list,
	 * waiting to be written in the csv. 
	 *  
	 * @param listNewTweet The list of tweet to add to the csv
	 */
	public void addNewTweetToCSV(List<TweetSkeleton> listNewTweetSkeleton)
	{
		for (TweetSkeleton elt : listNewTweetSkeleton)
		{
			if(!set.contains(elt.getId()))
			{	
				set.add(elt.getId());
				elt.setData(elt.cleanData(elt.getText())); //on clean la data 
				this.listNewTweet.add(elt.toString());
			}
			else
			{
				System.out.println("DOUBLON--->" + elt.toString());
			}
		}
	}
	
	/**
	 * Write the sorted data to the csv file
	 */
	public void writeToCSV()
	{
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(this.file, true);
			 
			for (String s : this.listNewTweet) {
				fw.write(s+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * wrapping up addNewTweetToCSV and writeToCSV
	 * @param l 
	 */
	public void doAll(List<TweetSkeleton> l)
	{
		addNewTweetToCSV(l);
		writeToCSV();
	}
	
}
