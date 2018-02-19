package com.github.dmstocking.putitonthelist.main.firebase_database;

import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.main.MainRepository;
import com.github.dmstocking.putitonthelist.uitl.FirestoreUtils;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
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
public class MainRepositoryFirebaseDatabase implements MainRepository {

    private static final String TAG = "MainRepository";

    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final Log log;
    @NonNull private final FirebaseDatabase firebaseDatabase;

    @Inject
    public MainRepositoryFirebaseDatabase(CategoryRepository categoryRepository,
                                          Log log,
                                          FirebaseDatabase firebaseDatabase) {
        this.categoryRepository = categoryRepository;
        this.log = log;
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override public Single<GroceryListId> create(String authId, String name) {
        return Single.<GroceryListId>create(emitter -> {
            DatabaseReference newReference = firebaseDatabase.getReference("lists")
                    .push();
            GroceryListDocument doc = new GroceryListDocument(
                    new HashMap<String, Boolean>() {{
                        put(authId, true);
                    }},
                    Collections.emptyMap(),
                    name,
                    0,
                    0);
            doc.setId(newReference.getKey());
            newReference.setValue(doc)
                    .addOnSuccessListener(documentReference -> {
                        emitter.onSuccess(GroceryListId.create(doc.getId()));
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
            Query query = firebaseDatabase.getReference("lists")
                    .orderByChild("authIds/" + authId)
                    .equalTo(true);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        List<GroceryListDocument> items = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            GroceryListDocument value = snapshot.getValue(GroceryListDocument.class);
                            value.setId(snapshot.getKey());
                            items.add(value);
                        }
                        Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                        emitter.onNext(items);
                    } else {
                        emitter.onNext(Collections.emptyList());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            };
            query.addValueEventListener(listener);
            emitter.setCancellable(() -> query.removeEventListener(listener));
        }, BackpressureStrategy.LATEST);
    }

    @Override public Completable delete(String id) {
        return Completable.create(emitter -> {
            firebaseDatabase.getReference("lists")
                    .child(id)
                    .removeValue()
                    .addOnSuccessListener(value -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }
}
