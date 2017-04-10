package nl.tue.dbsu10.group6.group6.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import nl.tue.dbsu10.group6.group6.R;
import nl.tue.dbsu10.group6.group6.activity.MainActivity;
import nl.tue.dbsu10.group6.group6.command.Command;

import java.util.Iterator;
import java.util.List;

/**
 * @author Ruben Schellekens
 */
public class CommandViewCreator {

    private static final int[] COLOURS = new int[] {
            Color.rgb(210, 255, 132),
            Color.rgb(132, 220, 255),
            Color.rgb(255, 199, 132),
            Color.rgb(255, 248, 132),
            Color.rgb(255, 132, 132)

    };
    private static int COLOUR_POINTER = 0;

    private final Command command;
    private GridLayout.LayoutParams layoutParams;

    public CommandViewCreator(Command command) {
        this.command = command;
    }

    public View create(final MainActivity main) {
        final int tileSize = main.getWidth() / 2;

        Button button = new Button(main);

        GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        layoutParams = new GridLayout.LayoutParams(rowSpan, colSpan);
        layoutParams.width = tileSize;
        layoutParams.height = tileSize;

        button.setLayoutParams(layoutParams);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        button.setText(command.getName());
        button.getBackground().setColorFilter(nextColour(), PorterDuff.Mode.MULTIPLY);
        button.setTextSize(16f);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return holdButton(main, command);
            }
        });

        return button;
    }

    private boolean holdButton(final MainActivity main, final Command command) {
        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder.setTitle(R.string.delete_input);
        builder.setMessage(String.format(main.getString(R.string.delete_input_confirm),
                command.getName()));

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Command> cmds = main.getCommandManager().getCommands();
                for (Iterator<Command> it = cmds.iterator(); it.hasNext(); ) {
                    Command cmd = it.next();
                    if (cmd == command) {
                        it.remove();
                    }
                }

                new GridUpdater(main, main.getGrid(), main.getCommandManager()).update();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

        return true;
    }

    private int nextColour() {
        int colour = COLOURS[COLOUR_POINTER++];
        COLOUR_POINTER %= COLOURS.length;
        return colour;
    }

    public GridLayout.LayoutParams getLayoutParams() {
        return layoutParams;
    }
}
