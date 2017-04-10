package nl.tue.dbsu10.group6.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import nl.tue.dbsu10.group6.gestures.*;
import nl.tue.id.oocsi.client.OOCSIClient;

/**
 * @author Ruben Schellekens
 */
public class MainActivity extends AppCompatActivity implements GestureHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure gestures
        OOCSIClient client = Gestures.newClient();
        Gestures.enable(this, client);

        // Listen for events
        Gesture.ROLL_LEFT.addListener(this);
        Gesture.ROLL_RIGHT.addListener(this);
        Gesture.FLIP_DOWN.addListener(this);
        Gesture.FLIP_UP.addListener(this);
    }

    @Override
    public void gesturePerformed(GestureEvent event) {
//        System.out.println("HANDLING " + event.getGesture() + " w/ MAC " + event.getMAC());
    }
}