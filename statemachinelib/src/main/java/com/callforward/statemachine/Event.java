package com.callforward.statemachine;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * This class represents an Event.
 * <br> Events can be generated either by any comopnent in your app - Activities, Services or BroadcastReceivers -
 * as a response to user actions or other in-app and system events.<br>
 * It can be used as is but it's recommended to create specialized classes inheriting from this with their appropriate priority.
 */
public class Event implements Parcelable,Comparable<Event> {

    private int priority;
    private int eventId;
    private Bundle data;

    public Event(int priority, int eventId, Bundle data) {
        this.priority = priority;
        this.eventId = eventId;
        this.data = data;
    }

    protected Event(Parcel in) {
        priority = in.readInt();
        data = in.readBundle(Bundle.class.getClassLoader());
        eventId = in.readInt();

    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Bundle getData() {
        return data;
    }

    int getEventId() {
        return eventId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(priority);
        parcel.writeBundle(data);
        parcel.writeInt(eventId);
    }

    @Override
    public int compareTo(@NonNull Event event) {

        int result = 0;

        if (this.priority > event.priority)
            result = 1;
        else if (this.priority < event.priority)
            result = -1;

        return result;
    }

}
