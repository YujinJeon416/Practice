package main.java.co.sveinung.twitter.scraper;

import twitter4j.TwitterException;

/**
 * Created by sveinung on 6/12/15.
 */
public class Main {

    private static final String CONSUMER_KEY = "QkaSYSKopjUvp81q67jItXeGK";
    private static final String CONSUMER_SECRET = "9j8vOhTssOrmn7ZgwFypXaOUVPL4Ex8veZoNrcG81sRk2ZTl0t"
;

    public static void main(String[] args) {
        try {
            final TwitterScraper twitterScraper = new TwitterScraper(CONSUMER_KEY, CONSUMER_SECRET, new ImageProcessor(args[1]));
            twitterScraper.search(args[0]);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}


