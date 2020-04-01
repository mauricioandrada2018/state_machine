package com.example.statemachinetest;

import android.os.Bundle;

import com.callforward.statemachine.Event;
import com.callforward.statemachine.util.EventFactory;

public class AppEventFactory implements EventFactory {
    @Override
    public Event createEvent(String eventName, Bundle data) {

        Event event = null;

        switch (eventName) {

            case "B":

                event = new BlueEvent(data);

                break;

            case "G":

                event = new GreenEvent(data);

                break;

            case "R":

                event = new RedEvent(data);

                break;
        }

        return event;
    }
}
