package io.github.chandilsachin.mvvm.arc.models;

/**
 * Created by sachin on 02/05/18.
 */

public class Event<T> {
    private T data;
    private boolean eventHandled;

    public Event(T data) {
        this.data = data;
        eventHandled = false;
    }

    /**
     * Returns null if event has been handled. This is used to ensure an event is not re-occurred accidentally.
     *
     * @return
     */
    public T getDataIfNotHandled() {
        if (eventHandled) {
            return null;
        } else {
            eventHandled = true;
            return this.data;
        }
    }

    /**
     * Returns event data each time.
     *
     * @return
     */
    public T peepData() {
        return this.data;
    }
}
