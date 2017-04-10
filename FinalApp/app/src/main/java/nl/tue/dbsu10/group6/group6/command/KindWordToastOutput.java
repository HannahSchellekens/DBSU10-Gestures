package nl.tue.dbsu10.group6.group6.command;

import java.util.Random;

/**
 * @author Ruben Schellekens
 */
public class KindWordToastOutput extends ToastOutput {

    private static final String[] messages = {
            "You are awesome!", "I believe in you!", "You deserve a hug!", "You made my day!",
            "Take care of yourself!", "Hugs <3", "Thank you for existing!",
            "You are special <3", "Believe in yourself, because I do!", "Feel the love <3"
    };

    public KindWordToastOutput() {
        super("");
    }

    @Override
    public String getMessage() {
        return messages[new Random().nextInt(messages.length)];
    }
}
