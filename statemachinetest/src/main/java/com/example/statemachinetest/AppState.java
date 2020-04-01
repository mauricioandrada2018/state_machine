package com.example.statemachinetest;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.callforward.statemachine.Event;
import com.callforward.statemachine.EventHandler;
import com.callforward.statemachine.State;

/**
 * States derived from this class will showcase how flexible a state machine can be.
 * <br>Upon creation, the event will carry an instance of a Messenger class, allowing the State
 * to send data back to the event generator - in this case the app Activity.
 * <br>This state will send a Bundle with a message to be displayed by the main activity.
 */
public class AppState extends State {

    String message;
    private int messageCounter;

    private Messenger messenger;

    AppState(EventHandler eventHandler, String stateName) {
        super(eventHandler, stateName);
    }

    @Override
    protected void enter(Event event) {

        Log.v("StateMachine", "entered state " + getStateName());

        Bundle bundle = event.getData();

        if (bundle != null) {
            messenger = bundle.getParcelable("messenger");
        }

        Message m = Message.obtain();
        Bundle content = new Bundle();
        content.putString("text", "Message #" + messageCounter + ": Entered state " + getStateName());
        m.obj = content;
        messageCounter++;

        try {
            messenger.send(m);

            synchronized (this) {
                wait(1000);
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }

        eventHandler.idle();
    }

    @Override
    protected void execute(Event event) {

        Bundle bundle = event.getData();

        if (bundle != null) {
            messenger = bundle.getParcelable("messenger");
        }

        Message m = Message.obtain();
        Bundle content = new Bundle();
        content.putString("text", "Message #" + messageCounter + this.message);
        m.obj = content;
        messageCounter++;

        try {
            messenger.send(m);
            synchronized (this) {
                wait(1000);
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanUp() {

        messenger = null;
    }
}
