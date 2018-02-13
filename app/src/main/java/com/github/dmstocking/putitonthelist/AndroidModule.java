package com.github.dmstocking.putitonthelist;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.github.dmstocking.putitonthelist.main.widget.WidgetProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    @NonNull private final CoreApplication application;

    public AndroidModule(@NonNull CoreApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    @Named("MainWidgetProvider")
    public ComponentName providesComponentName() {
        return new ComponentName(application, WidgetProvider.class);
    }

    @Provides
    @Singleton
    public FirebaseJobDispatcher providesFirebaseJobDispatcher() {
        return new FirebaseJobDispatcher(new GooglePlayDriver(application));
    }

    @Provides
    @Singleton
    public NotificationManager providesNotificationManager() {
        return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    @Named("package")
    public String providesPackageName() {
        return application.getPackageName();
    }

    @Provides
    @Singleton
    public Resources providesResources() {
        return application.getResources();
    }
}
