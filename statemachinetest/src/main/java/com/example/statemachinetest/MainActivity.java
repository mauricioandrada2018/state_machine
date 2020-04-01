package com.example.statemachinetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.callforward.statemachine.Event;
import com.callforward.statemachine.IPriorityQueueService;
import com.callforward.statemachine.PriorityQueueService;
import com.callforward.statemachine.util.Transition;
import com.callforward.statemachine.util.TransitionTableGenerator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a small app to demonstrate the use of the state machine.
 * The main Activity will generate a list of Events with all 6 possible sequences and dispatch them to the Service in milliseconds.
 * Each State is represented in the UI by a colored square with some text in it to indicate the current State.
 * Each state will receive the Event, send the data to the UI, and wait for 1 seconds before declaring itself idle.
 * This will demonstrate that the order each color shows up on screen is independent of the order the events are sent.
 * In other words, the square will be blue for 6 seconds, then green and then red.
 * It also illustrates how versatile the state machine can be by showing how a State can interact with the UI.
 */
public class MainActivity extends Activity implements ServiceConnection, Handler.Callback {

    private IPriorityQueueService priorityQueueService;
    private AppEventFactory appEventFactory;
    private Handler handler;
    private Messenger messenger;
    private Bundle data;
    private String[] stateNames = {"B","G","R"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(this);
        messenger = new Messenger(handler);
        data = new Bundle();

        data.putParcelable("messenger",messenger);

        findViewById(R.id.button).setEnabled(false);

        //Create the transition table
        Transition blue = new Transition(0,"*","B");
        Transition green = new Transition(1,"*","G");
        Transition red = new Transition(2,"*","R");

        ArrayList<Transition> transitions = new ArrayList<>();
        transitions.add(blue);
        transitions.add(green);
        transitions.add(red);

        HashMap transitiontable = TransitionTableGenerator.createTransitionTable(transitions);

        //Instantiate the state factory
        AppStateFactory stateFactory = new AppStateFactory();
        appEventFactory = new AppEventFactory();

        //Start the priority queue service
        Intent intent = new Intent();

        intent.setClass(this.getApplicationContext(),PriorityQueueService.class);
        intent.putExtra("stateFactory",stateFactory);
        intent.putExtra("transitionTable",transitiontable);

        bindService(intent,this,BIND_AUTO_CREATE);

    }

    public void onClick(View v) {

        /*
         * Here we'll generate and send multiple events. Observe that we'll create the list alternating between
         * the 3 types of events, starting with the events
         * with lowest priorities to illustrate how this implementation processes them in
         * in order of priority, not arrival.
         * In our implementation, the lower the priority number, the higher the event priority.
         */

        try {
            for (int i = 30; i > 0; i--) {

                Event event = appEventFactory.createEvent(stateNames[i % 3],data);
                priorityQueueService.addEvent(event);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        findViewById(R.id.button).setEnabled(true);
        priorityQueueService = IPriorityQueueService.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public boolean handleMessage(Message message) {

        TextView textView = findViewById(R.id.content);
        String text = ((Bundle) message.obj).getString("text");

        textView.append(text + "\n");

        return true;
    }
}
