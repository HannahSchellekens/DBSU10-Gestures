package nl.tue.dbsu10.group6.group6.command;

import nl.tue.dbsu10.group6.group6.activity.MainActivity;

/**
 * @author Ruben Schellekens
 */
public class RemoteOutput implements Output {

    public static final int NEXT = 1;
    public static final int PREVIOUS = -1;

    private MainActivity main;
    private int channelChange = 0;

    public RemoteOutput(MainActivity main, int channelChange) {
        this.channelChange = channelChange;
    }

    @Override
    public void execute() {
        System.out.println("Changed TV channel by " + channelChange);
    }

    @Override
    public String getMessage() {
        return channelChange >= NEXT ? "Channel up" : "Channel down";
    }
}
