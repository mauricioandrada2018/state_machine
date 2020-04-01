package com.example.statemachinetest;

import com.callforward.statemachine.EventHandler;

class BlueState extends AppState {

    BlueState(EventHandler eventHandler) {
        super(eventHandler, "B");
        message = "Blue state received new event";
    }
}
