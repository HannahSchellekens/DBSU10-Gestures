package nl.tue.dbsu10.group6.group6.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ruben Schellekens
 */
public class CommandManager implements Iterable<Command> {

    private List<Command> commands = new ArrayList<>();

    public List<Command> getCommands() {
        return commands;
    }

    public void add(Command command) {
        commands.add(command);
    }

    public void remove(Command command) {
        commands.remove(command);
    }

    public int size() {
        return commands.size();
    }

    @Override
    public Iterator<Command> iterator() {
        return commands.listIterator();
    }
}
