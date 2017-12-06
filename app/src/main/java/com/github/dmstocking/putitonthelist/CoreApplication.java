package com.github.dmstocking.putitonthelist;

import android.support.multidex.MultiDexApplication;

import com.github.dmstocking.putitonthelist.uitl.UtilModule;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class CoreApplication extends MultiDexApplication {

    @Nullable private CoreComponent coreComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "ca-app-pub-9356857454818788~3361766063");
    }

    @NonNull
    public CoreComponent coreComponent() {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder()
                    .androidModule(new AndroidModule(this))
                    .utilModule(new UtilModule(FirebaseAnalytics.getInstance(this),
                                               FirebaseAuth.getInstance(),
                                               FirebaseFirestore.getInstance()))
                    .build();
        }

        return coreComponent;
    }
}
