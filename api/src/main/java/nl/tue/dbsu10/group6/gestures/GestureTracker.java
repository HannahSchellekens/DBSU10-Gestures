package nl.tue.dbsu10.group6.gestures;

import nl.tue.dbsu10.group6.gestures.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A gestures tracker will listen to the sensors to recognise gestures.
 *
 * @author Ruben Schellekens
 */
abstract class GestureTracker {

    /**
     * The MAC-address of the device.
     */
    private final String macAddress;

    /**
     * The gesture that the tracker is able to recognise.
     */
    private Gesture gesture;

    /**
     * All gesture handlers that must be notified whenever a gesture gets recognised.
     */
    private List<GestureHandler> listeners;

    /**
     * Already fetches the MAC-address of the device.
     */
    protected GestureTracker() {
        this.macAddress = Util.getMAC().orElse(Gestures.MAC_NOT_FOUND);
        this.listeners = new ArrayList<>();
    }

    /**
     * Set the gesture that must be recognised.
     *
     * @see GestureTracker#gesture
     */
    void setGesture(Gesture gesture) {
        this.gesture = gesture;
    }

    /**
     * Adds a gesture handler that must be notified whenever the given gesture gets recognised.
     *
     * @see GestureTracker#listeners
     */
    public void addListener(GestureHandler handler) {
        listeners.add(handler);
    }

    /**
     * Must be executed whenever the given gesture is recognised.
     * <p>
     * This will do the following things: <ol> <li>Notify all registered gesture handlers.</li>
     * <li>Send an OOCSI broadcast when no handler is cancelled {@link GestureEvent#cancelled}.</li>
     * <li>Vibrates when no handler protests {@link GestureEvent#feedback}.</li> </ol>
     */
    protected void activate() {
        boolean cancelled = false;
        boolean feedback = false;

        for (GestureHandler handler : listeners) {
            GestureEvent event = new DefaultGestureEvent(gesture, macAddress);
            handler.gesturePerformed(event);
            cancelled |= event.isCancelled();
            feedback |= event.doFeedback();
        }

        if (!cancelled) {
            sendOocsiMessage();
        }

        if (feedback) {
            Gestures.getVibrator().vibrate(200);
        }

        // Halt the program temporarily to prevent many messages being sent close to each other.
        // OOCSI or Android doesn't like this too much.
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts the detected gesture over OOCSI.
     * <p>
     * Sends {@code }{@link Gestures#KEY_GESTURE_ID}{@code :[ID], }{@link
     * Gestures#KEY_MAC_ADDRESS}{@code :[MAC]} Where {@code ID} is the unique id of the gesture and
     * {@code MAC} is the MAC-address of the device that performed the gesture.
     *
     * @see Gesture#id
     */
    private void sendOocsiMessage() {
        MessageBuilder messageBuilder = new MessageBuilder(gesture);
        MessageSender sender = new MessageSender(messageBuilder.build());
        sender.send(Gestures.getClient());
    }

    /**
     * Sets up the Tracker in such a way that it is able to recognise gestures.
     * <p>
     * This means that when the tracker recognises an event, all handlers should be notified using
     * {@link GestureTracker#activate()}. After they have been notified, the tracker should be able
     * to recognise further events too.
     */
    abstract void enable();

}
