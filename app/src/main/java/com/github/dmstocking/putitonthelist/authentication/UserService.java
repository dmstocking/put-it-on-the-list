package com.github.dmstocking.putitonthelist.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

@Singleton
public class UserService {

    @NonNull private final FirebaseAuth firebaseAuth;

    @Inject
    public UserService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Observable<Boolean> isLoggedIn() {
        return getUserId().map(uid -> !uid.isEmpty());
    }

    public Observable<String> getUserId() {
        return Observable.create((emitter) -> {
            FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    emitter.onNext(currentUser.getUid());
                } else {
                    emitter.onNext("");
                }
            };
            firebaseAuth.addAuthStateListener(authStateListener);
            emitter.setCancellable(() -> firebaseAuth.removeAuthStateListener(authStateListener));
        });
    }
}
