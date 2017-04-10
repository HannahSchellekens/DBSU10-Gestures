package nl.tue.dbsu10.group6.group6.command;

import nl.tue.dbsu10.group6.gestures.Gesture;

/**
 * @author Ruben Schellekens
 */
public class GestureInput implements Input {

    private final Gesture gesture;

    public GestureInput(Gesture gesture) {
        this.gesture = gesture;
    }

    public Gesture getGesture() {
        return gesture;
    }
}
