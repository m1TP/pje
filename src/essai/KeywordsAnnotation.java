package essai;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import model.TweetSkeleton;

public class KeywordsAnnotation {
	String[] positive;
	String[] negative;
	
	public KeywordsAnnotation(String filePositive, String fileNegative){
		this.positive = loadCSV(filePositive).split(",");
		this.negative = loadCSV(fileNegative).split(",");
	}
	
	
	public KeywordsAnnotation(){
		this("positiv.txt","negativ.txt");
	}
	
	/**
	 * Annotate a tweet according to a frequency of positives and negatives words coming from a dictionnary
	 * @param ts The structure containing the tweet
	 */
	public void annotateTweet(TweetSkeleton ts){
		String data = ts.getText();
		int countPositive=countWords(positive, data);
		int countNegative=countWords(negative, data);
		int res = -1;
		if(countPositive>countNegative)
			res = 4;
		else if (countPositive<countNegative)
			res = 0;
		else 
			res = 2;
		ts.setAnnotation(res);
	}
	
	public int countWords(String[] tabExpression, String text){
		int res=0;
		for(int i=0;i<tabExpression.length;i++){
			if(Pattern.compile(tabExpression[i], Pattern.CASE_INSENSITIVE | Pattern.LITERAL).matcher(text).find())
			{
				res++;
				System.out.println(tabExpression[i]);
			}
		}
		return res;
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

	
	
}
