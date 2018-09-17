package io.github.chandilsachin.mvvm.arc.models;

/**
 * Represents the state of the View.
 */
public enum State {
    /**
     * Requested operation is successful. This state is used to inform UI that operation is completed.
     */
    SUCCESS,
    /**
     * Requested operation has been failed. An exception object must be passed to ViewState object to inform UI of what went wrong. see @{@link ViewState}
     */
    FAILED,
    /**
     * Requested operation is still pending. This state is used to inform UI that let user know that operation is going on.
     */
    LOADING,
    /**
     * Requested operation has been stopped in between. This is specific to RxJava.
     * An RxJava observable chain must be disposed, when ViewModel is about to be cleared because lifecycle of its Fragment or Activity is about to end.
     */
    DISPOSED
}
