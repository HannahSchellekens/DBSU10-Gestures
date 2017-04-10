package nl.tue.dbsu10.group6.group6.command;

import android.view.View;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.dbsu10.group6.group6.Util;
import nl.tue.dbsu10.group6.group6.ui.CommandViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Schellekens
 */
public class Command {

    private static long previousStamp = -1;

    private MainActivity main;
    private Input input;
    private List<Output> output;
    private String name;

    public Command(MainActivity main, Input input, Output output, String name) {
        this.main = main;
        this.input = input;
        this.output = new ArrayList<>();
        if (output != null) {
            this.output.add(output);
        }
        this.name = name;
    }

    public void execute() {
        if (ignore()) {
            return;
        }

        for (Output output : this.output) {
            output.execute();
            Util.toast(main, output.getMessage());
        }
    }

    private boolean ignore() {
        if (previousStamp <= 0) {
            return false;
        }

        long now = System.currentTimeMillis();
        long delta = now - previousStamp;
        if (delta >= 1000) {
            previousStamp = now;
            return false;
        }

        return true;
    }

    public void add(Output output) {
        this.output.add(output);
    }

    public Input getInput() {
        return input;
    }

    public List<Output> getOutput() {
        return output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View makeView(MainActivity main) {
        return new CommandViewCreator(this).create(main);
    }

    @Override
    public String toString() {
        return getName();
    }
}
