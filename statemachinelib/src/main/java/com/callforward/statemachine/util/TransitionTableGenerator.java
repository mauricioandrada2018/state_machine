package com.callforward.statemachine.util;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is an utility class used to generate the Hashmap with the transition table.
 * <br>Besides adding the proper entry in the table, it also checks if the entry already exists and alerts the application.
 * The transitions are provided by the application as an ArrayList of Transitions.
 */
public class TransitionTableGenerator {

    public static HashMap<String, String> createTransitionTable(@NonNull ArrayList<Transition> transitions) {

        HashMap<String,String> transitionTable = new HashMap<>();

        for (Transition transition:transitions) {

            String pair = transition.getEventId() + transition.getCurrentStateName();

            if (transitionTable.containsKey(pair)) {
                throw new IllegalArgumentException();
            }

            transitionTable.put(pair,transition.getNextStateName());
        }

        return transitionTable;
    }
}
