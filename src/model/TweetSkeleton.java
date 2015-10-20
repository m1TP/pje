package model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TweetSkeleton {
	
	/** The id of the tweet, given by twitter */
	protected long id;
	
	/** The user emetting the tweet */
	protected String user;
	
	/** The body of the tweet */
	protected String text;
	
	/** The date the tweet was put on twitter */
	protected Date date;
	
	/** The query we used for our search */
	protected String query;
	
	/** The note of the tweet
	 * 	-1 is not annotated
	 *  0 is bad mood
	 *  2 is neutral
	 *  4 is good */
	protected int annotation; 
	
	/**
	 * annotation is by default -1
	 */
	public TweetSkeleton(long id,String user,String text,Date date,String query)
	{
		this.id = id;
		this.user=user;
		this.text=text;
		this.date=date;
		this.query=query;
		this.annotation=-1; 
	}
	
	/**
	 * Clean a body of text of various bad input such as:
	 * -replace \n \t \r par un espace 
	 * -remove username
	 * -remove add white space before and after punctuation
	 * -replace dollar/euro value with variable
	 * -replace percentage value with variable
	 * @param text  The text to be cleaned
	 * @return The new String with the replacement done
	 */
	public String cleanData(String text)
	{
		String regURL             = "http\\w?:\\S*"; //filter out URL
		String regWhiteSpace      = "\\s";
		String regSpecialSymbol   = "(@|#|RT)[\\p{Alnum}\\p{Punct}éèêàûôï]*"; //filter for #,@ and RT
		String regPunctuation     = "([\"!:?.;,])"; //find a special char to put space around it
		String regCurrency        = "(\\p{Sc})\\d[.\\d]*"; //look for monetary symbol
		String regPercentage      = "\\d{1,2}%"; 
		
		
		StringBuffer sb = new StringBuffer(text); 
		
		sb = replacePattern(sb, regURL, "URL");
		sb = replacePattern(sb, regWhiteSpace, " ");
		sb = replacePattern(sb, regSpecialSymbol,"$1");
		sb = replacePattern(sb, regPunctuation, " $1 ");
		sb = replacePattern(sb, regCurrency, "$1XXX");
		sb = replacePattern(sb, regPercentage, "XX%");
		
		//System.out.println(sb);
		sb.append(" "); //pour l'annotation automatique on a besoin d'un espace en fin de chaine, au cas ou.
		return sb.toString();
	}
	
	/**
	 * Search a motif according to a specified regex in a text, and replace occurences of found motif with a specified string
	 * @param text The text the search is made in
	 * @param reg  The regex to use to look for motif
	 * @param replace The String to replace the motif with 
	 * @return The new String with the replacement done
	 */
	private StringBuffer replacePattern(StringBuffer text, String reg, String replace)
	{
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(text);
		StringBuffer sb = new StringBuffer();
		while(m.find())
			   m.appendReplacement(sb, replace);
		
		return m.appendTail(sb);
	}
	
	public void setAnnotation(int n)
	{
		if(n<-1 && n>2)
			throw new Error("bad number");
		else
			this.annotation=n;
	}
	
	public void setData(String text)
	{
		this.text = text;
	}
	
	public String toString()
	{
		String separator = ";";
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(separator);
		sb.append(user);
		sb.append(separator);
		sb.append(text);
		sb.append(separator);
		sb.append(date.toString());
		sb.append(separator);
		sb.append(query);
		sb.append(separator);
		sb.append(annotation);
		
		
		return sb.toString(); 
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public String getQuery() {
		return query;
	}

	public int getAnnotation() {
		return annotation;
	}
	
}
