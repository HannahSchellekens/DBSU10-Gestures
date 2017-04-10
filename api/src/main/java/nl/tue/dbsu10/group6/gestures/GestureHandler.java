package nl.tue.dbsu10.group6.gestures;

/**
 * A gesture handler will execute some routine when a gesture gets recognised.
 *
 * @author Ruben Schellekens
 */
public interface GestureHandler {

    /**
     * Gets called when a gesture gets recognised by the system.
     *
     * @param event
     *         The information about event.
     */
    void gesturePerformed(GestureEvent event);
}
