package com.github.dmstocking.putitonthelist;

import android.content.ComponentName;
import android.content.res.Resources;
import android.support.annotation.NonNull;

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
    public Resources providesResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    @Named("package")
    public String providesPackageName() {
        return application.getPackageName();
    }

    @Provides
    @Singleton
    @Named("MainWidgetProvider")
    public ComponentName providesComponentName() {
        return new ComponentName(application, WidgetProvider.class);
    }
}
