package nl.tue.dbsu10.group6.group6;

import nl.tue.dbsu10.group6.gestures.Gesture;
import nl.tue.dbsu10.group6.gestures.GestureEvent;
import nl.tue.dbsu10.group6.gestures.GestureHandler;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.dbsu10.group6.group6.command.Command;
import nl.tue.dbsu10.group6.group6.command.GestureInput;
import nl.tue.dbsu10.group6.group6.command.Input;

/**
 * @author Ruben Schellekens
 */
public class GestureProcessor implements GestureHandler {

    private MainActivity main;

    public GestureProcessor(MainActivity main) {
        this.main = main;
    }

    @Override
    public void gesturePerformed(GestureEvent gestureEvent) {
        Gesture gesture = gestureEvent.getGesture();
        if (gesture == null) {
            gestureEvent.setCancelled(true);
            gestureEvent.setFeedback(false);
            return;
        }

        boolean feedback = false;
        for (Command command : main.getCommandManager()) {
            Input input = command.getInput();
            if (!(input instanceof GestureInput)) {
                continue;
            }

            if (((GestureInput)input).getGesture().equals(gesture)) {
                command.execute();
                feedback = true;
            }
        }

        gestureEvent.setCancelled(feedback);
        gestureEvent.setFeedback(feedback);
    }
}
