package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.dmstocking.putitonthelist.uitl.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = "MainPresenter";

    @NonNull private final MainService mainService;
    @NonNull private final Log log;

    @NonNull private Disposable subscribe = Disposables.disposed();

    @Nullable private MainViewModel model;
    @Nullable private MainContract.View view;

    @Inject
    public MainPresenter(@NonNull MainService mainService,
                         @NonNull Log log) {
        this.mainService = mainService;
        this.log = log;
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
        subscribe.dispose();
        subscribe = mainService.model()
                .subscribe(model -> {
                               this.model = model;
                               view.render(model);
                           },
                           throwable -> log.e(TAG, "Problem while rendering.", throwable));
    }


    @Override
    public void onClick(ListViewModel listModel) {
        if (model != null && model.isSelecting()) {
            if (listModel.selected()) {
                mainService.unmark(listModel.id());
            } else {
                mainService.mark(listModel.id());
            }
        } else if (view != null) {
            view.launchGroceryList(listModel.id());
        }
    }

    @Override
    public void onLongClick(ListViewModel listModel) {
        mainService.mark(listModel.id());
    }

    @Override
    public void onDelete() {
        mainService.delete();
    }

    @Override
    public void onCancelSelection() {
        mainService.cancel();
    }

    @Override
    public void detachView(MainContract.View view) {
        subscribe.dispose();
    }
}
