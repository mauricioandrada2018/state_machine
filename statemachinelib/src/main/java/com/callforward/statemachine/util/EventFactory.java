package com.callforward.statemachine.util;

import android.os.Bundle;

import com.callforward.statemachine.Event;

public interface EventFactory {

    Event createEvent(String eventName, Bundle data);
}
