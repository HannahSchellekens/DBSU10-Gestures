package nl.tue.dbsu10.group6.group6.ui;

import android.view.View;
import android.widget.GridLayout;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.dbsu10.group6.group6.command.Command;
import nl.tue.dbsu10.group6.group6.command.CommandManager;

/**
 * @author Ruben Schellekens
 */
public class GridUpdater {

    private final MainActivity main;
    private final GridLayout grid;
    private final CommandManager commands;

    public GridUpdater(MainActivity main, GridLayout grid, CommandManager commands) {
        this.main = main;
        this.grid = grid;
        this.commands = commands;
    }

    public void update() {
        // Clear grid
        grid.removeAllViews();

        // Update row count
        grid.setRowCount(Math.max(1, commands.size() / 2));

        // Add tiles
        for (Command command : commands) {
            CommandViewCreator maker = new CommandViewCreator(command);
            View view = maker.create(main);
            grid.addView(view, maker.getLayoutParams());
        }
    }
}
