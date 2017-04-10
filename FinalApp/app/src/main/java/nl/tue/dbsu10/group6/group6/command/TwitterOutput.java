package nl.tue.dbsu10.group6.group6.command;

import nl.tue.dbsu10.group6.gestures.Gestures;
import nl.tue.id.oocsi.client.OOCSIClient;
import nl.tue.id.oocsi.client.protocol.OOCSIMessage;

/**
 * @author Ruben Schellekens
 */
public class TwitterOutput implements Output {

    public static final String CHANNEL = "tweetBot";
    public static final String KEY_TWEET = "tweet";

    private String tweet;

    public TwitterOutput(String tweet) {
        this.tweet = tweet;
    }

    @Override
    public void execute() {
        OOCSIClient client = Gestures.getClient();
        OOCSIMessage message = new OOCSIMessage(client, CHANNEL);
        message.data(KEY_TWEET, tweet);
        message.send();

        System.out.println("Sent a tweet via OOCSI");
    }

    @Override
    public String getMessage() {
        return "Sent tweet: " + tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
