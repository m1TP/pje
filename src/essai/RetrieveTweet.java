package essai;


import java.util.ArrayList;
import java.util.List;

import model.TweetSkeleton;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class RetrieveTweet {
	
	/**
	 * overload the method and never allow retweet in the query
	 */
	public List<TweetSkeleton> queryTwitter(String sujetTweet, int nbTweet)
	{
		return queryTwitter(sujetTweet,nbTweet,false);
	}
	
	/**
	 * Query the Twitter website to retrieve data linked to a specified subject.
	 * @param subjectTweet The subject we want to get data about.
	 * @param nbTweet The number of different tweet we want to get. The number is capped at 100.
	 * @param retweetAllowed A boolean showing if the data we get will contain retweet or not. (we suggest to disallow it)
	 * @return A list of tweets and their respective data. 
	 */
	public List<TweetSkeleton> queryTwitter(String subjectTweet, int nbTweet, boolean retweetAllowed){
	    Twitter twitter = TwitterFactory.getSingleton();
	    String qquery = subjectTweet;
	    String tmpQuery = qquery;
	    if (!retweetAllowed)
	    	tmpQuery = qquery + " +exclude:retweets";
	    
	    Query query = new Query(qquery);
	    query.count(nbTweet>100?100:nbTweet); //on limite le nombre de query, l'api recommande 100 max

	    QueryResult result = null;
		try {
			RateLimitStatus s = twitter.getRateLimitStatus("search").get("/search/tweets");
			System.out.println(s.getRemaining());
			if(!(s.getRemaining() > 0))
			{
				System.out.println("ERROR: RATE EXCEEDED, wait "+s.getSecondsUntilReset()+" seconds");
				System.exit(0);
			}
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		List<TweetSkeleton> listTweet = new ArrayList<TweetSkeleton>();
		for (Status status : result.getTweets()) {
			TweetSkeleton ts = new TweetSkeleton(
					status.getId(),
					status.getUser().getName(),
					status.getText(),
					status.getCreatedAt(),
					qquery);
	        listTweet.add(ts);
	        System.out.println(ts.cleanData(ts.toString()));

	        System.out.println(ts);
		}

		return listTweet;
		
	}	
}
