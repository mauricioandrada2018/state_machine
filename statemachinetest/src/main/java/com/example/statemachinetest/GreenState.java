package com.example.statemachinetest;

import com.callforward.statemachine.EventHandler;

class GreenState extends AppState {

    GreenState(EventHandler eventHandler) {

        super(eventHandler, "G");
        message = "Green state received new event";
    }

}
