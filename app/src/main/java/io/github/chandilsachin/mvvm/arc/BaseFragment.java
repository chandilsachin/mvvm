package io.github.chandilsachin.mvvm.arc;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.ReplaySubject;

public abstract class BaseFragment<E extends ViewModel> extends Fragment {

    /**
     * Observable for State of current fragment. For example: if Fragment has been CREATED / STARTED / RESUMED / DESTROYED.
     * This will help to observe these states and update view accordingly.
     */
    public ReplaySubject<Lifecycle.State> fragmentState = ReplaySubject.create();
    protected E mViewModel;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private boolean attachViewModelToActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutId(), container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (attachViewModelToActivity) {
            if (getActivity() != null) {
                mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getViewModelClass());
            }
        } else {
            mViewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        }
        initView(view);
        loadInitData();
        setUpViews(savedInstanceState);
        setEvents();
        fragmentState.onNext(Lifecycle.State.CREATED);
    }

    /**
     * returns layout id for layout to be inflated by the fragment.
     */
    protected abstract int getLayoutId();

    protected abstract Class<E> getViewModelClass();

    /**
     * Initializes view elements
     */
    protected abstract void initView(View view);


    /**
     * Loads data from database or any other sources.
     */
    protected abstract void loadInitData();

    /**
     * Sets up initial configuration for the views and loads data into views ̰
     *
     * @param savedInstanceState
     */
    protected abstract void setUpViews(Bundle savedInstanceState);

    /**
     * Sets event listeners onto the views.
     */
    protected void setEvents() {
        // empty block
    }

    /**
     * Returns true if viewModel allowed to be attached with activity.
     *
     * @return
     */
    public boolean isAttachViewModelToActivity() {
        return attachViewModelToActivity;
    }

    /**
     * allows fragment to attach viewModel to activity so that view model can persist over the activity lifecycle.
     *
     * @param attachViewModelToActivity a boolean
     */
    public void setAttachViewModelToActivity(boolean attachViewModelToActivity) {
        this.attachViewModelToActivity = attachViewModelToActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentState.onNext(Lifecycle.State.RESUMED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentState.onNext(Lifecycle.State.DESTROYED);
        compositeDisposable.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentState.onNext(Lifecycle.State.STARTED);
    }

    /**
     * Listens for Fragment Created event and calls passed action callback.
     *
     * @param action
     */
    public void callOnCreated(final Runnable action) {
        compositeDisposable.clear();
        compositeDisposable.add(fragmentState.subscribe(state -> {
            if (state == Lifecycle.State.CREATED) {
                action.run();
            }
        }));
    }
}
