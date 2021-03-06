package com.github.dmstocking.putitonthelist.main.cloud_firestore;

import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.main.MainRepository;
import com.github.dmstocking.putitonthelist.uitl.FirestoreUtils;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

@Singleton
public class MainRepositoryCloudFirestore implements MainRepository {

    private static final String TAG = "MainRepository";

    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final CollectionReference listRef;
    @NonNull private final FirestoreUtils firestoreUtils;
    @NonNull private final Log log;

    @Inject
    public MainRepositoryCloudFirestore(CategoryRepository categoryRepository,
                                        Log log,
                                        FirestoreUtils firestoreUtils,
                                        FirebaseFirestore firestore) {
        this.categoryRepository = categoryRepository;
        this.log = log;
        this.firestoreUtils = firestoreUtils;
        this.listRef = firestore.collection("lists");
    }

    @Override public Single<GroceryListId> create(String authId, String name) {
        return Single.<GroceryListId>create(emitter -> {
            GroceryListDocument doc = new GroceryListDocument(
                    new HashMap<String, Boolean>() {{
                        put(authId, true);
                    }},
                    Collections.emptyMap(),
                    name,
                    0,
                    0);
            listRef.add(doc)
                    .addOnSuccessListener(documentReference -> {
                        emitter.onSuccess(GroceryListId.create(documentReference.getId()));
                    }).addOnFailureListener(e -> {
                log.e(TAG, "Error while adding Grocery list.", e);
                emitter.onError(e);
            });
        })
                .flatMap(id -> {
                    return categoryRepository.createDefaultCategories(id)
                            .toSingleDefault(id);
                });
    }

    @Override public Flowable<List<GroceryListDocument>> getModel(String authId) {
        return Flowable.create(emitter -> {
            ListenerRegistration registration = listRef
                    .whereEqualTo("authIds." + authId, true)
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null) {
                            emitter.onError(e);
                        } else if (snapshot != null) {
                            List<GroceryListDocument> items = snapshot.toObjects(GroceryListDocument.class);
                            for (int i=0; i < items.size(); i++) {
                                items.get(i).setId(snapshot.getDocuments().get(i).getId());
                            }
                            Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                            emitter.onNext(items);
                        } else {
                            log.w(TAG, "No data");
                        }

                    });

            emitter.setCancellable(registration::remove);
        }, BackpressureStrategy.LATEST);
    }

    @Override public Completable delete(String id) {
        DocumentReference document = listRef.document(id);
        return Completable.mergeArray(
                firestoreUtils.deleteCollection(document.collection("categories")),
                firestoreUtils.deleteCollection(document.collection("items")))
                .andThen(firestoreUtils.deleteDocument(document));
    }

}
