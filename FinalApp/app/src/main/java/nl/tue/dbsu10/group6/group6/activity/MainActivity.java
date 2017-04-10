package nl.tue.dbsu10.group6.group6.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import nl.tue.dbsu10.group6.gestures.Gesture;
import nl.tue.dbsu10.group6.gestures.Gestures;
import nl.tue.dbsu10.group6.group6.GestureProcessor;
import nl.tue.dbsu10.group6.group6.R;
import nl.tue.dbsu10.group6.group6.api.APILink;
import nl.tue.dbsu10.group6.group6.command.*;
import nl.tue.dbsu10.group6.group6.ui.GridUpdater;
import nl.tue.id.oocsi.client.OOCSIClient;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    private static final long serialVersionUID = 4023918490128094232L;

    // Dirty hack
    public static MainActivity INSTANCE;

    // Members
    private CommandManager commandManager;
    private int width;
    private APILink apiLink;

    // UI Elements
    GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupApp();
        setupGestures();
        debug();
        (apiLink = new APILink(this)).setup();
        setupUIElements();

        INSTANCE = this;
    }

    private void setupApp() {
        commandManager = new CommandManager();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
    }

    private void setupUIElements() {
        // Grid
        grid = (GridLayout)findViewById(R.id.grid);
        grid.setColumnCount(2);
        new GridUpdater(this, grid, commandManager).update();
    }

    private void setupGestures() {
        OOCSIClient client = Gestures.newClient();
        Gestures.enable(this, client);

        GestureProcessor processor = new GestureProcessor(this);
        for (Gesture gestures : Gesture.defaultValues()) {
            gestures.addListener(processor);
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public APILink getApiLink() {
        return apiLink;
    }

    public int getWidth() {
        return width;
    }

    public GridLayout getGrid() {
        return grid;
    }

    public void add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, 0);
    }

    // TODO: Remove
    private void debug() {
//        commandManager.add(new Command(this,
//                new GestureInput(Gesture.ROLL_LEFT),
//                new RemoteOutput(this, RemoteOutput.PREVIOUS),
//                "Channel down\nRoll left"
//        ));
//
//        commandManager.add(new Command(this,
//                new GestureInput(Gesture.ROLL_RIGHT),
//                new RemoteOutput(this, RemoteOutput.NEXT),
//                "Channel up\nRoll right"
//        ));
//
//        commandManager.add(new Command(this,
//                new GestureInput(Gesture.FLIP_DOWN),
//                new CoffeeOutput(this),
//                "Coffee\nFlip Down"
//        ));
//
//        commandManager.add(new Command(this,
//                new GestureInput(Gesture.FLIP_UP),
//                new RandomPizzaOutput(),
//                "Pizza Button\nFlip Up"
//        ));
//
//        commandManager.add(new Command(this,
//                DummyInput.INSTANCE,
//                new KindWordToastOutput(),
//                "Kind words <3"
//        ));
    }
}
