package main.java.co.sveinung.twitter.scraper;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by sveinung on 6/12/15.
 */
public class TwitterScraper {
    private final Processor[] processors;
    private final String consumerKey;
    private final String consumerSecret;
    private OAuth2Token token;
    private Twitter twitter;
    public TwitterScraper(String consumerKey, String consumerSecret, final Processor ... processors) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.processors = processors;
    }

    public void search(final String query) throws TwitterException {
        authenticate();
        findTweets(query);
    }

    private void authenticate() throws TwitterException {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setApplicationOnlyAuthEnabled(true);
        twitter = new TwitterFactory(builder.build()).getInstance();

        // exercise & verify
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        token = twitter.getOAuth2Token();
        System.out.println("Got token " + token);
    }

    private void findTweets(final String query) throws TwitterException {
        QueryResult queryResult = twitter.search(new Query(query));
        while (queryResult.hasNext()) {
            System.out.println(String.format("Found %d tweets.", queryResult.getCount()));
            processTweets(queryResult.getTweets());
            queryResult = twitter.search(queryResult.nextQuery());
        }
    }

    private void processTweets(List<Status> tweets) {
        for (Status tweet : tweets) {
            for (Processor processor : processors) {
                processor.processTweet(tweet);
            }
        }
    }

    public interface Processor {
        public void processTweet(final Status tweet);
    }
}
