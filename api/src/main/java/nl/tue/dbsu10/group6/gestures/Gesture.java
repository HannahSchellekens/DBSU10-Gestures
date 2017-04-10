package nl.tue.dbsu10.group6.gestures;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A gesture is a certain phone movement.
 * <p>
 * A gesture consist of an ID (the id that will be broadcasted over oocsi) and a {@link
 * GestureTracker} that is able to recognise the gesture.
 *
 * @author Ruben Schellekens
 */
public class Gesture {

    /**
     * A small but firm roll to the left.
     * <p>
     * If you hold the phone in your hand regularly, a left wrist movement activates this gesture.
     */
    public static final Gesture ROLL_LEFT =
            new Gesture("roll_left", new RollTracker(RollTracker.X_AXIS, RollTracker.LEFT));

    /**
     * A small but firm roll to the right.
     * <p>
     * If you hold the phone in your hand regularly, a right wrist movement activates this gesture.
     */
    public static final Gesture ROLL_RIGHT =
            new Gesture("roll_right", new RollTracker(RollTracker.X_AXIS, RollTracker.RIGHT));

    /**
     * A small, but form rotation towards you.
     * <p>
     * If you hold your phone in your hand regularly, a firm wrist movement towards you activates
     * this gesture.
     */
    public static final Gesture FLIP_UP =
            new Gesture("flip_up", new RollTracker(RollTracker.Y_AXIS, RollTracker.UP));

    /**
     * A small, but form rotation away from you.
     * <p>
     * If you hold your phone in your hand regularly, a firm wrist movement away from you activates
     * this gesture.
     */
    public static final Gesture FLIP_DOWN =
            new Gesture("flip_down", new RollTracker(RollTracker.Y_AXIS, RollTracker.DOWN));

    /**
     * Contains all the gestures that the Gestures module contains by default.
     */
    private static final Set<Gesture> DEFAULTS = new HashSet<>();
    static {
        Collections.addAll(DEFAULTS, ROLL_LEFT, ROLL_RIGHT, FLIP_DOWN, FLIP_UP);
    }

    /**
     * The ID of the gesture.
     * <p>
     * This is the ID that will be sent over the oocsi network.
     */
    private String id;

    /**
     * The tracker that is able to recognise the gesture.
     */
    private GestureTracker tracker;

    /**
     * Automatically binds the gesture to the tracker and does not enable the tracker.
     */
    public Gesture(String id, GestureTracker tracker) {
        this.id = id;

        this.tracker = tracker;
        tracker.setGesture(this);
    }

    /**
     * Activates the trackers of all default gestures.
     *
     * @see Gesture#defaultValues()
     */
    static void enableDefaultGestures() {
        for (Gesture gesture : DEFAULTS) {
            gesture.tracker.enable();
        }
    }

    /**
     * Enables the given gesture, making the program track it.
     *
     * @param gesture
     *         The gesture to enable and track.
     */
    public static void enableGesture(Gesture gesture) {
        gesture.tracker.enable();
    }

    /**
     * Get the unmodifiable set of all default gestures.
     *
     * @return An unmodifiable set of all default gestures.
     * @see Gesture#DEFAULTS
     */
    public static Set<Gesture> defaultValues() {
        return Collections.unmodifiableSet(DEFAULTS);
    }

    /**
     * Adds a listener to this event that gets triggered when the gesture is enabled and being
     * recognised.
     */
    public void addListener(GestureHandler listener) {
        tracker.addListener(listener);
    }

    /**
     * Get the ID of the gesture on the ooci network.
     *
     * @see Gesture#id
     */
    public String getId() {
        return id;
    }

    /**
     * @return Format: {@code "id"} (without quotes)
     * @see Gesture#id
     */
    @Override
    public String toString() {
        return id;
    }
}
