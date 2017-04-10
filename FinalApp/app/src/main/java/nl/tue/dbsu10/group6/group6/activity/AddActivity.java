package nl.tue.dbsu10.group6.group6.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import nl.tue.dbsu10.group6.gestures.Gesture;
import nl.tue.dbsu10.group6.group6.R;
import nl.tue.dbsu10.group6.group6.command.*;
import nl.tue.dbsu10.group6.group6.ui.GridUpdater;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EditText txtName;
    private Spinner spinInput;
    private Spinner spinOutput1;
    private Spinner spinOutput2;
    private Spinner spinOutput3;
    private MainActivity main;
    private String tweetText;
    private Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txtName = (EditText)findViewById(R.id.txtName);
        spinInput = (Spinner)findViewById(R.id.spinInput);
        spinOutput1 = (Spinner)findViewById(R.id.spinOutput1);
        spinOutput2 = (Spinner)findViewById(R.id.spinOutput2);
        spinOutput3 = (Spinner)findViewById(R.id.spinOutput3);
        main = MainActivity.INSTANCE;
    }

    public void add(View view) {
        // Check name
        String name = txtName.getText().toString();
        if (name == null || name.isEmpty()) {
            showErrorDialog(R.string.enter_name);
            return;
        }

        // Get input
        String input = (String)spinInput.getSelectedItem();
        Input inputObject = getInputFromName(input);

        // Get outputs (would be more generalised in a larger scope project ofc).
        List<Output> outputs = new ArrayList<>();
        String outputString1 = (String)spinOutput1.getSelectedItem();
        String outputString2 = (String)spinOutput2.getSelectedItem();
        String outputString3 = (String)spinOutput3.getSelectedItem();
        addOutputFromName(outputString1, outputs);
        addOutputFromName(outputString2, outputs);
        addOutputFromName(outputString3, outputs);

        if (outputs.isEmpty()) {
            showErrorDialog(R.string.enter_output);
            return;
        }

        // Create command
        command = new Command(main, inputObject, null, name);
        command.getOutput().addAll(outputs);

        // If twitter is present, do twitter thingy.
        TwitterOutput twitterOutput;
        if ((twitterOutput = containsTwitter(outputs)) != null) {
            promptTweet(twitterOutput);
        }
        else {
            finishUp();
        }
    }

    public void finishUp() {
        if (command == null) {
            return;
        }

        main.getCommandManager().add(command);
        new GridUpdater(main, main.grid, main.getCommandManager()).update();

        finish();
    }

    public void cancel(View view) {
        finish();
    }

    private TwitterOutput containsTwitter(List<Output> outputs) {
        for (Output output : outputs) {
            if (output instanceof TwitterOutput) {
                return (TwitterOutput)output;
            }
        }

        return null;
    }

    private Input getInputFromName(String name) {
        String[] inputs = getResources().getStringArray(R.array.input_array);

        if (name.equals(inputs[0])) {
            return DummyInput.INSTANCE;
        }
        else if (name.equals(inputs[1])) {
            return new GestureInput(Gesture.ROLL_LEFT);
        }
        else if (name.equals(inputs[2])) {
            return new GestureInput(Gesture.ROLL_RIGHT);
        }
        else if (name.equals(inputs[3])) {
            return new GestureInput(Gesture.FLIP_UP);
        }
        else if (name.equals(inputs[4])) {
            return new GestureInput(Gesture.FLIP_DOWN);
        }
        else {
            return null;
        }
    }

    private Output getOutputFromName(String name) {
        String[] outputs = getResources().getStringArray(R.array.output_array);

        if (name.equals(outputs[0])) {
            return null;
        }
        else if (name.equals(outputs[1])) {
            return new CoffeeOutput(main);
        }
        else if (name.equals(outputs[2])) {
            return new RandomPizzaOutput();
        }
        else if (name.equals(outputs[3])) {
            return new TwitterOutput(null);
        }
        else {
            return null;
        }
    }

    private String promptTweet(final TwitterOutput output) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tweet);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tweetText = input.getText().toString();
                output.setTweet(tweetText);
                finishUp();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tweetText = null;
                dialog.cancel();
                command = null;
            }
        });

        builder.show();

        return tweetText;
    }

    private void addOutputFromName(String name, List<Output> outputs) {
        Output output = getOutputFromName(name);
        if (output != null) {
            outputs.add(output);
        }
    }

    private void showErrorDialog(int stringId) {
        new AlertDialog.Builder(this)
                .setMessage(stringId)
                .setTitle(R.string.wrong_input)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing.
                    }
                })
                .show();
    }
}
