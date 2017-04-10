package nl.tue.dbsu10.group6.group6.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruben Schellekens
 */
public class CoffeeApi {

    private List<Integer> ordered = new ArrayList<>();

    public List<Integer> getOrdered() {
        return ordered;
    }
}
