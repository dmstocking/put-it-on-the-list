package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.uitl.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    @NonNull private final MainRepository mainRepository;
    @NonNull private final Log log;

    @NonNull private Disposable subscribe = Disposables.disposed();

    @Inject
    public MainPresenter(@NonNull MainRepository mainRepository,
                         @NonNull Log log) {
        this.mainRepository = mainRepository;
        this.log = log;
    }

    @Override
    public void attachView(MainContract.View view) {
        subscribe = mainRepository.getModel()
                .subscribe(view::render,
                           throwable -> log.e(TAG, "Problem while rendering.", throwable));
    }

    @Override
    public void detachView(MainContract.View view) {
        subscribe.dispose();
    }
}
