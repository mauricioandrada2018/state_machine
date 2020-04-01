package com.callforward.statemachine.util;

import android.os.Parcelable;

import com.callforward.statemachine.EventHandler;
import com.callforward.statemachine.State;

/**
 * This interface allows the EventHandler to instantiate new States based on the state name.
 * State factories are used to create the application-specific states used by the app.
 * Since it is used by a service, the application-specific StateFactory must be passed to the service when it's bound.
 * In order to do so, the application's StateFactory must be Parcelable.
 */
public interface StateFactory extends Parcelable {

    State createState(EventHandler eventHandler, String stateName);
}
