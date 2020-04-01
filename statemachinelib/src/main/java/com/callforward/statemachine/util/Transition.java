package com.callforward.statemachine.util;

public class Transition {

    private int eventId;
    private String currentStateName;
    private String nextStateName;


    public Transition(int eventId, String currentStateName, String nextStateName) {
        this.eventId = eventId;
        this.currentStateName = currentStateName;
        this.nextStateName = nextStateName;
    }

    public int getEventId() {
        return eventId;
    }

    public String getCurrentStateName() {
        return currentStateName;
    }

    public String getNextStateName() {
        return nextStateName;
    }
}
