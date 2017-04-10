package nl.tue.dbsu10.group6.gestures;

/**
 * Just a normal event that gets fired when a gesture gets recognised.
 *
 * @author Ruben Schellekens
 */
class DefaultGestureEvent extends GestureEvent {

    /**
     * The recognised gesture.
     */
    private final Gesture gesture;

    /**
     * The MAC address of the device that made the gesture.
     * <p>
     * This is {@link Gestures#MAC_NOT_FOUND} when the mac address could not be found.
     */
    private final String macAddress;

    DefaultGestureEvent(Gesture gesture, String macAddress) {
        this.gesture = gesture;
        this.macAddress = macAddress;
    }

    @Override
    public Gesture getGesture() {
        return gesture;
    }

    @Override
    public String getMAC() {
        return macAddress;
    }
}
