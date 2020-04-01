package com.example.statemachinetest;

import android.os.Parcel;
import android.os.Parcelable;

import com.callforward.statemachine.EventHandler;
import com.callforward.statemachine.State;
import com.callforward.statemachine.util.StateFactory;

public class AppStateFactory implements StateFactory {

    @Override
    public State createState(EventHandler eventHandler, String stateName) {

        State result = null;

        switch (stateName) {

            case "B":

                result = new BlueState(eventHandler);

            break;

            case "G":

                    result = new GreenState(eventHandler);

                break;

            case "R":

                    result = new RedState(eventHandler);

                break;
        }

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<AppStateFactory> CREATOR = new Parcelable.Creator<AppStateFactory>() {
        @Override
        public AppStateFactory createFromParcel(Parcel parcel) {

            return new AppStateFactory();
        }

        @Override
        public AppStateFactory[] newArray(int i) {
            return new AppStateFactory[i];
        }
    };
}
