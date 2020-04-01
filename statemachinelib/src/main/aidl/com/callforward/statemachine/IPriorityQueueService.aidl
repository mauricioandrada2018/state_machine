// IPriorityQueueService.aidl
package com.callforward.statemachine;

import com.callforward.statemachine.Event;

interface IPriorityQueueService {

    /**
    * Used by the application to add events to the prioroty queue.
    */
    boolean addEvent(in Event event);
}
