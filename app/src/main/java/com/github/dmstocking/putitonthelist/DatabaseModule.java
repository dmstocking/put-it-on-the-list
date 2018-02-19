package com.github.dmstocking.putitonthelist;

import android.support.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @NonNull private final FirebaseDatabase firebaseDatabase;
    @NonNull private final FirebaseFirestore firebaseFirestore;

    public DatabaseModule(@NonNull FirebaseDatabase firebaseDatabase,
                             @NonNull FirebaseFirestore firebaseFirestore) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseFirestore = firebaseFirestore;
    }

    @Provides
    @Singleton
    public FirebaseFirestore providesFirebasestore() {
        return firebaseFirestore;
    }

    @Provides
    @Singleton
    public FirebaseDatabase providesFirebaseDatabase() {
        return firebaseDatabase;
    }

}
