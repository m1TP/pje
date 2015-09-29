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
	
	public List<TweetSkeleton> gogo(){
		// The factory instance is re-useable and thread safe.
	    Twitter twitter = TwitterFactory.getSingleton();
	    String qquery = "rugby";
	    Query query = new Query(qquery);
	    //query.setLang("fr_FR");
	    query.count(50); //on limite le nombre de query, l'api recommande 100 max
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
					status.getUser().getDescription(),
					status.getText(),
					status.getCreatedAt(),
					qquery);
	        listTweet.add(ts);
	        //System.out.println(ts);
	    }
		return listTweet;
		
	}	
}
