package nl.tue.dbsu10.group6.group6.command;

/**
 * @author Ruben Schellekens
 */
public class ToastOutput implements Output {

    private final String message;

    public ToastOutput(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Toasts message");
    }

    @Override
    public String getMessage() {
        return message;
    }
}
