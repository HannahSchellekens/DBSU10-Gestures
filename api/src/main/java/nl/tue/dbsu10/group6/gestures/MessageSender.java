package nl.tue.dbsu10.group6.gestures;

import nl.tue.id.oocsi.OOCSIEvent;
import nl.tue.id.oocsi.client.OOCSIClient;
import nl.tue.id.oocsi.client.protocol.OOCSIMessage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A MessageSender obviously sends messages.
 *
 * @author Ruben Schellekens
 */
public class MessageSender {

    /**
     * The data to put in the message.
     */
    private final Map<String, String> data;

    /**
     * @param data
     *         The data to put into the message. The map maps the keys to the values.
     */
    public MessageSender(Map<String, String> data) {
        this.data = data;
    }

    /**
     * Sends a message with the given data.
     *
     * @param data
     *         The data to send. The array must consist out of key-valuye pairs. That means that the
     *         first element is the first key, the second element is the second value, the third
     *         element is the second key etc. This means that the amount of elements in the array
     *         must be even.
     * @throws IllegalArgumentException
     *         When the data array has an odd amount of elements.
     */
    public MessageSender(String... data) throws IllegalArgumentException {
        if (data.length % 2 == 1) {
            throw new IllegalArgumentException("data must be an even array");
        }

        this.data = new HashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            this.data.put(data[i], data[i + 1]);
        }
    }

    /**
     * Sends the message over the given client.
     * <p>
     * Also prints the sent data to the standard error stream {@link System#err}.
     */
    public void send(final OOCSIClient client) {
        final OOCSIMessage message = new OOCSIMessage(client, Gestures.OOCSI_CHANNEL);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            message.data(entry.getKey(), entry.getValue());
        }

        try {
            Field field = OOCSIEvent.class.getDeclaredField("data");
            field.setAccessible(true);
            Map<String, Object> data = (Map<String, Object>)field.get(message);
            System.err.println("Data that is being sent: " + data + "@" +
                    Gestures.OOCSI_DEFAULT_HOST + ":" + Gestures.OOCSI_DEFAULT_PORT + "#" +
                    message.getChannel());
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();

            message.send();
        }
    }
}