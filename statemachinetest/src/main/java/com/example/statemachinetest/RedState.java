package com.example.statemachinetest;

import com.callforward.statemachine.EventHandler;


class RedState extends AppState {

    RedState(EventHandler eventHandler) {
        super(eventHandler, "R");
        message = "Red state received new event";
    }

}
