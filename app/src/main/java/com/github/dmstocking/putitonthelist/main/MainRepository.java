package com.github.dmstocking.putitonthelist.main;

import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

@Singleton
public class MainRepository {

    private static final String TAG = "MainRepository";

    @NonNull private final CollectionReference listRef;
    @NonNull private final Log log;

    @Inject
    public MainRepository(Log log,
                          FirebaseFirestore firestore) {
        this.log = log;
        this.listRef = firestore.collection("lists");
    }

    public Completable create(String authId, String name) {
        return Completable.create(emitter -> {
            GroceryListDocument doc = new GroceryListDocument(
                    new HashMap<String, Boolean>() {{
                        put(authId, true);
                    }},
                    name,
                    Collections.emptyList());
            listRef.add(doc)
                    .addOnSuccessListener(documentReference -> {
                        emitter.onComplete();
                    }).addOnFailureListener(e -> {
                        log.e(TAG, "Error while adding Grocery list.", e);
                        emitter.onError(e);
                    });
        });
    }

    public Flowable<List<GroceryListDocument>> getModel(String authId) {
        return Flowable.create(emitter -> {
            ListenerRegistration registration = listRef
                    .whereEqualTo("authIds." + authId, true)
                    .addSnapshotListener((snapshot, e) -> {
                if (e != null) {
                    emitter.onError(e);
                } else if (snapshot != null) {
                    List<GroceryListDocument> items = snapshot.toObjects(GroceryListDocument.class);
                    Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    emitter.onNext(items);
                } else {
                    log.w(TAG, "No data");
                }

            });

            emitter.setCancellable(registration::remove);
        }, BackpressureStrategy.LATEST);
    }
}
