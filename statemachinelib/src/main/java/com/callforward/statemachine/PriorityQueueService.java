package com.callforward.statemachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.callforward.statemachine.IPriorityQueueService.Stub;
import com.callforward.statemachine.util.StateFactory;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class provides the core functionality for managing Events.
 * <br>It's a Service that must be launched as soon as the app starts (e.g. from the main Activity).<br>
 * Your app must extend class {@link StateFactory} and provide an instance of it to the Service, along with and a HashMap with the transition table.<br>
 * The transition table can be generated using the TransitionTableGenerator utility.<br>
 * Here is a sample code: <br>
 * <code>
 *         HashMap transitiontable = TransitionTableGenerator.createTransitionTable(transitions);
 *
 *         //Instantiate the state factory
 *         AppStateFactory stateFactory = new AppStateFactory();
 *
 *         //Start the priority queue service
 *         Intent intent = new Intent();
 *
 *         intent.setClass(this.getApplicationContext(),PriorityQueueService.class);
 *         intent.putExtra("stateFactory",stateFactory);
 *         intent.putExtra("transitionTable",transitiontable);
 *
 *         bindService(intent,this,BIND_AUTO_CREATE);
 * </code>
 * 
 * The PriorityBlockingQueue is initialized with size 200 but you can change it to a value appropriate to your application.<br>
 * Components in your app that need to communicate with the service must bind to it and use {@link IPriorityQueueService#addEvent(Event)}
 */
public class PriorityQueueService extends Service {

    private PriorityBlockingQueue<Event> eventPriorityQueue;

    public PriorityQueueService() {

        eventPriorityQueue = new PriorityBlockingQueue<>(200);
    }

    @Override
    public IBinder onBind(Intent intent) {

        StateFactory stateFactory = intent.getParcelableExtra("stateFactory");
        HashMap transitionTable = (HashMap) intent.getSerializableExtra("transitionTable");
        new EventHandler(transitionTable, eventPriorityQueue, stateFactory);

        Log.v("StatemachineTest","onBind = "+PriorityQueueService.class.getName());

        return new IPriorityQueueService();
    }

    private class IPriorityQueueService extends Stub {

        @Override
        public boolean addEvent(Event event) {

            if (event == null)
                return false;

            return eventPriorityQueue.offer(event);
        }
    }
}
