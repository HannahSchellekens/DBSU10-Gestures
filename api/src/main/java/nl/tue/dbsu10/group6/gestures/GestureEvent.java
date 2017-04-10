package nl.tue.dbsu10.group6.gestures;

/**
 * A gesture events gets called when a certain gesture has been registered.
 *
 * @author Ruben Schellekens
 */
public abstract class GestureEvent {

    /**
     * Marks if an oocsi broadcast must be sent or not.
     * <p>
     * {@code true} if an oocsi broadcast must be sent, {@code false} when no such broadcast must be
     * sent.
     */
    private boolean cancelled;

    /**
     * Marks if the phone should vibrate when the gesture is recognised.
     * <p>
     * {@code true} to vibrate, {@code false} not to vibrate.
     */
    private boolean feedback;

    /**
     * Defaults with oocsi broadcast and vibration feedback enabled.
     */
    public GestureEvent() {
        this.cancelled = false;
        this.feedback = true;
    }

    /**
     * Get the gesture that has been recognised.
     */
    public abstract Gesture getGesture();

    /**
     * Get the MAC-address of the device that made the gesture.
     */
    public abstract String getMAC();

    /**
     * Sets whether there must be an oocsi broadcast or not.
     *
     * @param cancelled
     *         {@code true} if an oocsi broadcast must be sent, {@code false} when no such broadcast
     *         must be sent.
     * @see GestureEvent#cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Checks whether there must be an oocsi broadcast or not.
     *
     * @return {@code true} if an oocsi broadcast must be sent, {@code false} when no such broadcast
     * must be sent.
     * @see GestureEvent#cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets whether the phone should vibrate or not.
     *
     * @param feedback
     *         {@code true} to vibrate, {@code false} not to vibrate.
     * @see GestureEvent#feedback
     */
    public void setFeedback(boolean feedback) {
        this.feedback = feedback;
    }

    /**
     * Checks whether the phone should vibrate or not.
     *
     * @return {@code true} to vibrate, {@code false} not to vibrate.
     * @see GestureEvent#feedback
     */
    public boolean doFeedback() {
        return feedback;
    }
}
