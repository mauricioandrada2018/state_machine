package com.callforward.statemachine;

import java.io.InvalidObjectException;

/**
 * This class represents a state.
 * <br>It must be extended to represent all the possible States in your application.
 * The constructor is package-private on purpose; States must be instantiated only by the EventHandler.
 * Similarly, the enter() method is also package-private since it's supposed to only be called by the EventHandler.
 * Finally, as a convenience, this class implements Runnable; this way the developer can launch the state as Thread if the application requires.
 */
public abstract class State implements Runnable {

    protected EventHandler eventHandler;
    private String stateName;

    protected State(EventHandler eventHandler, String stateName) {
        this.eventHandler = eventHandler;
        this.stateName = stateName;
    }

    @Override
    public void run() {

    }

    /**
     * Must be overriden by the app.
     * @param event a new event dispatched to this state
     */
    protected abstract void execute(Event event);

    /**
     * Must be implemented by the application to initialize the state.
     * @param event the Event that triggered the transition to this state
     */
    protected abstract void enter(Event event);

    final void newEvent(Event event) {

        execute(event);
        idle();
    }

    /**
     * Implemented by the application to cleanup the state as needed (e.g. release memory).
     */
    protected abstract void cleanUp();

    final void exit() {

        cleanUp();
    }

    /**
     * Used internally to notify the EventHandler the State is idle() and ready for a new event or state transition.
     */
    private void idle() {

        if (eventHandler != null) {
            eventHandler.idle();
        }
    }

    @Override
    public boolean equals(Object obj) {

        return obj instanceof State && stateName.equals(((State) obj).getStateName());

    }

    protected final String getStateName() {
        return stateName;
    }
}
