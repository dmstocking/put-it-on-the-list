package com.github.dmstocking.putitonthelist;

import android.support.multidex.MultiDexApplication;

import com.github.dmstocking.putitonthelist.uitl.UtilModule;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.plugins.RxJavaPlugins;

public class CoreApplication extends MultiDexApplication {

    @Nullable private CoreComponent coreComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.setErrorHandler(e -> {
            coreComponent().log().e("RxJavaError", "", e);
        });
        MobileAds.initialize(this, "ca-app-pub-9356857454818788~3361766063");
        coreComponent().notificationInitializer()
                .init();
    }

    @NonNull
    public CoreComponent coreComponent() {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder()
                    .androidModule(new AndroidModule(this))
                    .databaseModule(new DatabaseModule(
                            FirebaseDatabase.getInstance(),
                            FirebaseFirestore.getInstance()))
                    .utilModule(new UtilModule(FirebaseAnalytics.getInstance(this),
                                               FirebaseAuth.getInstance()))
                    .build();
        }

        return coreComponent;
    }
}
