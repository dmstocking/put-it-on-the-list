package com.github.dmstocking.putitonthelist.uitl;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class UtilModule {

    @NonNull private final FirebaseAnalytics firebaseAnalytics;
    @NonNull private final FirebaseAuth firebaseAuth;
    @NonNull private final FirebaseFirestore firebaseFirestore;

    public UtilModule(FirebaseAnalytics firebaseAnalytics,
                      FirebaseAuth firebaseAuth,
                      FirebaseFirestore firebaseFirestore) {
        this.firebaseAnalytics = firebaseAnalytics;
        this.firebaseAuth = firebaseAuth;
        this.firebaseFirestore = firebaseFirestore;
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
    public FirebaseAuth providesFirebaseAuth() {
        return firebaseAuth;
    }

    @Provides
    @Singleton
    public FirebaseFirestore providesFirebasestore() {
        return firebaseFirestore;
    }

    @Provides
    @Singleton
    public Log providesLog(AndroidLog log) {
        return log;
    }
}
