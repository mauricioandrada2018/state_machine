package com.example.statemachinetest;

import android.os.Bundle;

import com.callforward.statemachine.Event;

class GreenEvent extends Event {

    GreenEvent(Bundle data) {
        super(1, 1, data);
    }
}
