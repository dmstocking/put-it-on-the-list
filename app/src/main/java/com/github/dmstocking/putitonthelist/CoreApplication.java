package com.github.dmstocking.putitonthelist;

import android.app.Application;

import com.github.dmstocking.putitonthelist.uitl.UtilModule;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class CoreApplication extends Application {

    @Nullable private CoreComponent coreComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @NonNull
    public CoreComponent coreComponent() {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder()
                    .utilModule(new UtilModule(FirebaseAnalytics.getInstance(this)))
                    .build();
        }

        return coreComponent;
    }
}
