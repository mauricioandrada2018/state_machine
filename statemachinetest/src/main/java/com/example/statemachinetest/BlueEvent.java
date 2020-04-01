package com.example.statemachinetest;

import android.os.Bundle;

import com.callforward.statemachine.Event;

class BlueEvent extends Event {

    BlueEvent(Bundle data) {
        super(2, 0, data);
    }
}
