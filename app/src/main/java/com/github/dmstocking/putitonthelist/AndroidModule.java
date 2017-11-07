package com.github.dmstocking.putitonthelist;

import android.content.res.Resources;
import android.support.annotation.NonNull;

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
}
