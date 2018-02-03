package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.FirestoreUtils;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class GroceryListRepository {

    private static final String TAG = "GroceryListRepository";

    @NonNull private final FirebaseFirestore fireStore;
    @NonNull private final FirestoreUtils firestoreUtils;
    @NonNull private final Log log;

    @Inject
    public GroceryListRepository(@NonNull FirebaseFirestore fireStore,
                                 @NonNull FirestoreUtils firestoreUtils,
                                 @NonNull Log log) {
        this.fireStore = fireStore;
        this.firestoreUtils = firestoreUtils;
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

    public Completable create(GroceryListId listId,
                              String category,
                              String name) {
        return Completable.create((emitter) -> {
            fireStore.collection("lists")
                    .document(listId.id())
                    .collection("items")
                    .add(new GroceryListItemDocument(category, name, false))
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }).andThen(updateNumberOfItems(listId));
    }

    public Completable update(GroceryListId listId,
                              GroceryListItemId itemId,
                              boolean purchased) {
        return Completable.create((emitter) -> {
            fireStore.collection("lists")
                    .document(listId.id())
                    .collection("items")
                    .document(itemId.id())
                    .update("purchased", purchased)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }).andThen(updateNumberOfItems(listId));
    }

    public Completable deletePurchased(GroceryListId listId) {
        return firestoreUtils.deleteInBatch(() -> {
            return fireStore.collection("lists")
                    .document(listId.id())
                    .collection("items")
                    .whereEqualTo("purchased", true)
                    .orderBy(FieldPath.documentId());
        }).andThen(updateNumberOfItems(listId));
    }

    private Completable updateNumberOfItems(GroceryListId listId) {
        return getGroceryListDocument(listId)
                .firstOrError()
                .flatMapCompletable(items -> {
                    int purchased = 0;
                    int total = 0;
                    for (GroceryListItemDocument item : items) {
                        total++;
                        if (item.isPurchased()) {
                            purchased++;
                        }
                    }
                    int finalPurchased = purchased;
                    int finalTotal = total;
                    return Completable.create((emitter) -> {
                        fireStore.collection("lists")
                                .document(listId.id())
                                .update("purchased", finalPurchased,
                                        "total", finalTotal)
                                .addOnSuccessListener(aVoid -> emitter.onComplete())
                                .addOnFailureListener(emitter::onError);
                    });
                });
    }
}
