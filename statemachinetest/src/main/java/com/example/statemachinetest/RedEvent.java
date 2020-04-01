package com.example.statemachinetest;

import android.os.Bundle;

import com.callforward.statemachine.Event;

class RedEvent extends Event {

    RedEvent(Bundle data) {
        super(0, 2, data);
    }
}
