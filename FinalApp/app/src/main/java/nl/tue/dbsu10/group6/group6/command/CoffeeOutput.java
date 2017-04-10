package nl.tue.dbsu10.group6.group6.command;

import nl.tue.dbsu10.group6.gestures.Gestures;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.id.oocsi.client.OOCSIClient;
import nl.tue.id.oocsi.client.protocol.OOCSIMessage;

import java.util.Random;

/**
 * @author Ruben Schellekens
 */
public class CoffeeOutput implements Output {

    public static final String CHANNEL = "coffee_channel";
    public static final String KEY_WHO = "caffee_who";
    public static final String KEY_AMOUNT = "caffee_amount";
    public static final String KEY_WAIT = "caffee_time_to_wait";

    private MainActivity main;

    public CoffeeOutput(MainActivity main) {
        this.main = main;
    }

    @Override
    public void execute() {
        int orderId = new Random().nextInt();
        main.getApiLink().getCoffeeApi().getOrdered().add(orderId);

        OOCSIClient client = Gestures.getClient();
        OOCSIMessage message = new OOCSIMessage(client, CHANNEL);
        message.data(KEY_WHO, Integer.toString(orderId));
        message.data(KEY_AMOUNT, 1);
        message.data(KEY_WAIT, 0);
        message.send();

        System.out.println("Sent a coffee request via OOCSI");
    }

    @Override
    public String getMessage() {
        return "Sent coffee request";
    }
}
