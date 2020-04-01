package com.callforward.statemachine;

import com.callforward.statemachine.util.StateFactory;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class is responsible for sending events to the current state and execute state transitions
 * defined in the transition table.
 * It uses a thread to handle new events to avoid locking the State because method
 * {@link PriorityBlockingQueue#take()} will block until a new event is available in the event queue.
 */
public class EventHandler implements Runnable {

    private HashMap transitionTable;
    private State currentState;


    private final PriorityBlockingQueue<Event> events;
    private StateFactory stateFactory;

    EventHandler(HashMap transitionTable, PriorityBlockingQueue<Event> events, StateFactory stateFactory) {
        this.transitionTable = transitionTable;

        this.events = events;
        this.stateFactory = stateFactory;

        idle();
    }

    public void idle() {

        (new Thread(this)).start();
    }

    @Override
    public void run() {

        Event event = null;

        try {
            event = events.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (event == null) return;

        if (currentState != null) {

            String nextStateName = (String) transitionTable.get(event.getEventId()+currentState.getStateName());

            if (nextStateName == null) {
                /*
                 * The wildcard value ("*") can be used as an origin State for a transition when the event
                 * forces a transition regardless of the current state.<br>
                 * It is useful for example, for handling
                 * critical errors or to define the first State of the state machine.
                 */
                nextStateName = (String) transitionTable.get(event.getEventId()+"*");
            }

            if (nextStateName == null || nextStateName.equals(currentState.getStateName())) {

                currentState.newEvent(event);

            } else {

                State nextState = stateFactory.createState(this,nextStateName);

                if (nextState != null) {

                    currentState.exit();

                    currentState = nextState;

                    currentState.enter(event);
                }
            }

        } else {

            String nextStateName = (String) transitionTable.get(event.getEventId() + "*");
            currentState = stateFactory.createState(this,nextStateName);
            currentState.enter(event);
        }
    }
}
