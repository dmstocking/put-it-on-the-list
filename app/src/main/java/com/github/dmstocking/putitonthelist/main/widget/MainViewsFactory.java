package com.github.dmstocking.putitonthelist.main.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.github.dmstocking.putitonthelist.main.MainService;
import com.github.dmstocking.putitonthelist.main.MainViewModel;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

@AutoFactory
class MainViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = "MainViewsFactory";

    @NonNull private final Context context;
    @NonNull private final Log log;
    @NonNull private final MainService mainService;
    private final int appWidgetId;

    @NonNull private Disposable subscribe = Disposables.disposed();
    private MainViewModel model;

    public MainViewsFactory(@Provided @NonNull Context context,
                            @Provided @NonNull Log log,
                            @Provided @NonNull MainService mainService,
                            Intent intent) {
        this.context = context;
        this.log = log;
        this.mainService = mainService;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                         AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        subscribe.dispose();
        subscribe = mainService.model()
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    this.model = model;
                }, throwable -> {
                    log.e(TAG, "Error while loading model", throwable);
                });
    }

    @Override
    public void onDestroy() {
        subscribe.dispose();
    }

    @Override
    public int getCount() {
        return model.groceryLists().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
