package io.github.chandilsachin.mvvm.arc;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.github.chandilsachin.mvvm.arc.models.Event;
import io.github.chandilsachin.mvvm.arc.models.State;
import io.github.chandilsachin.mvvm.arc.models.ViewState;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sachin on 21/03/18.
 */

public class BaseViewModel extends ViewModel {

    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<Event<ViewState<Integer>>> viewState = new MutableLiveData<>();

    protected void setLoading() {
        viewState.setValue(new Event<>(new ViewState<>(State.LOADING)));
    }

    protected void setSuccess() {
        viewState.setValue(new Event<>(new ViewState<>(State.SUCCESS)));
    }

    protected void setSuccess(int stateValue) {
        viewState.setValue(new Event<>(new ViewState<>(State.SUCCESS, stateValue)));
    }

    protected <E extends Throwable> void setFailed(E e) {
        viewState.setValue(new Event<>(new ViewState<>(State.FAILED)));
    }

    protected void setDisposed() {
        viewState.setValue(new Event<>(new ViewState<>(State.DISPOSED)));
    }

    @Override
    public void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    protected <T> SingleTransformer<T, T> getViewStateLifecycle() {
        return upstream -> upstream.doOnSubscribe(__ -> setLoading())
                .doOnSuccess(__ -> setSuccess())
                .doOnDispose(this::setDisposed)
                .doOnError(this::setFailed);
    }
}
