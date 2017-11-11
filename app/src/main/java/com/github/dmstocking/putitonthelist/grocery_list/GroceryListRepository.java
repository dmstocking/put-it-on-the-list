package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

@Singleton
public class GroceryListRepository {

    private static final String TAG = "GroceryListRepository";

    @NonNull private final FirebaseFirestore fireStore;
    @NonNull private final Log log;

    @Inject
    public GroceryListRepository(@NonNull FirebaseFirestore fireStore,
                                 @NonNull Log log) {
        this.fireStore = fireStore;
        this.log = log;
    }

    public Flowable<List<GroceryListItemDocument>> getGroceryListDocument(@NonNull GroceryListId id) {
        return Flowable.create(emitter -> {
            ListenerRegistration listener = fireStore.collection("lists")
                    .document(id.id())
                    .collection("items")
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null) {
                            emitter.onError(e);
                        } else if (querySnapshot != null) {
                            List<GroceryListItemDocument> docs = new ArrayList<>();
                            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                                GroceryListItemDocument item = doc.toObject(GroceryListItemDocument.class);
                                item.setId(doc.getId());
                                docs.add(item);
                            }
                            emitter.onNext(docs);
                        } else {
                            log.w(TAG, "No data");
                        }
                    });

            emitter.setCancellable(listener::remove);
        }, BackpressureStrategy.LATEST);
    }
}
