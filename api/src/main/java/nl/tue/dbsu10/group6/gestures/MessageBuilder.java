package nl.tue.dbsu10.group6.gestures;

import nl.tue.dbsu10.group6.gestures.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Dumps all data of a given gesture into a hashmap of key-value pairs.
 * <p>
 * These key-value pairs correspond to the pairs that have to be sent in an {@link
 * nl.tue.id.oocsi.client.protocol.OOCSIMessage}.
 *
 * @author Ruben Schellekens
 */
class MessageBuilder {

    /**
     * The gesture to encode to a message.
     */
    private Gesture gesture;

    /**
     * @param gesture
     *         The gesture to encode in the message.
     */
    public MessageBuilder(Gesture gesture) {
        this.gesture = gesture;
    }

    /**
     * Builds a new hashmap containing the gesture id and the mac address.
     * <p>
     * This will also look up the device's mac address. When no mac address could be found, {@link
     * Gestures#MAC_NOT_FOUND} is used.
     */
    public Map<String, String> build() {
        final String macAddress = Util.getMAC().orElse(Gestures.MAC_NOT_FOUND);

        return new HashMap<String, String>() {{
            put(Gestures.KEY_GESTURE_ID, gesture.getId());
            put(Gestures.KEY_MAC_ADDRESS, macAddress);
        }};
    }

}
