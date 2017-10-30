package com.github.dmstocking.putitonthelist.uitl;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class UtilModule {

    @NonNull private final FirebaseAnalytics firebaseAnalytics;

    public UtilModule(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Provides
    @Singleton
    public FirebaseAnalytics providesFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    @Provides
    @Singleton
    public Analytics providesAnalytics(FirebaseAnalyticsImpl firebaseAnalyticsImpl) {
        return firebaseAnalyticsImpl;
    }

    @Provides
    @Singleton
    public Log providesLog(AndroidLog log) {
        return log;
    }
}
