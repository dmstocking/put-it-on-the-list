package com.github.dmstocking.putitonthelist.main.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.authentication.UserService;
import com.github.dmstocking.putitonthelist.main.MainRepository;
import com.github.dmstocking.putitonthelist.uitl.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WidgetService extends RemoteViewsService {

    public static final String TAG = "WidgetService";

    @Inject AppWidgetManager appWidgetManager;
    @Inject Log log;
    @Inject MainRepository mainRepository;
    @Inject MainViewsFactoryFactory mainViewsFactoryFactory;
    @Inject UserService userService;
    @Inject ComponentName componentName;

    @Override
    public void onCreate() {
        super.onCreate();
        ((CoreApplication) getApplicationContext())
                .coreComponent()
                .widgetComponent(new WidgetModule(this))
                .inject(this);

        mainRepository.getModel(userService.getUserId())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    appWidgetManager.notifyAppWidgetViewDataChanged(
                            appWidgetManager.getAppWidgetIds(componentName),
                            R.id.list);
                }, throwable -> {
                    log.e(TAG, "Error while notifying widget dataset update", throwable);
                });
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return mainViewsFactoryFactory.create(intent);
    }
}
