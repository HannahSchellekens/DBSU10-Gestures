package nl.tue.dbsu10.group6.group6.command;

import nl.tue.dbsu10.group6.gestures.Gestures;
import nl.tue.id.oocsi.client.OOCSIClient;
import nl.tue.id.oocsi.client.protocol.OOCSIMessage;

/**
 * @author Ruben Schellekens
 */
public class RandomPizzaOutput implements Output {

    @Override
    public void execute() {
        OOCSIClient client = Gestures.getClient();
        new OOCSIMessage(client, "choosePizza").data("buttonPress", "").send();

        System.out.println("Send a Choose Pizza request.");
    }

    @Override
    public String getMessage() {
        return "Order a random pizza";
    }
}
