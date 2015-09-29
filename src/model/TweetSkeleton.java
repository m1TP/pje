package model;

import java.util.Date;

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
	 *  1 is neutral
	 *  2 is good */
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
	
	public void setAnnotation(int n)
	{
		if(n<-1 && n>2)
			throw new Error("bad number");
		else
			this.annotation=n;
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
		
		return sb.toString().replaceAll("\\s"," "); //remove whitespace and non visible char
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
