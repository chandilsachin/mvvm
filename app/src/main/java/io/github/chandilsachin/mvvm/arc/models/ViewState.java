package io.github.chandilsachin.mvvm.arc.models;


/**
 * Represents view state of the UI
 * Example: Suppose a click event fires a network request and its response is pending(Current state is LOADING and progress should be displayed to the user).
 * When response is received or request has been failed, respective state must be triggered and view must be updated.
 *
 * @param <T>
 */
public class ViewState<T> {

    public State state;
    public T data;

    public ViewState(State state, T data) {
        this.state = state;
        this.data = data;
    }

    public ViewState(State state) {
        this.state = state;
    }

    public boolean isDataPresent() {
        return data != null;
    }
}
