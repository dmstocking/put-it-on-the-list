package com.github.dmstocking.putitonthelist.grocery_list.firebase_database;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.grocery_list.GroceryListItemDocument;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListItemId;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class GroceryListRepositoryFirebaseDatabase implements GroceryListRepository {

    private static final String TAG = "GroceryListRepository";

    @NonNull private final FirebaseDatabase firebaseDatabase;
    @NonNull private final Log log;

    @Inject
    public GroceryListRepositoryFirebaseDatabase(@NonNull FirebaseDatabase firebaseDatabase,
                                                 @NonNull Log log) {
        this.firebaseDatabase = firebaseDatabase;
        this.log = log;
    }

    @Override
    public Flowable<List<GroceryListItemDocument>> getGroceryListDocument(@NonNull GroceryListId id) {
        return Flowable.create(emitter -> {
            DatabaseReference reference = firebaseDatabase.getReference("lists")
                    .child(id.id())
                    .child("items");

            ValueEventListener valueListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        List<GroceryListItemDocument> docs = new ArrayList<>();
                        for (DataSnapshot doc : dataSnapshot.getChildren()) {
                            GroceryListItemDocument item = doc.getValue(GroceryListItemDocument.class);
                            item.setId(doc.getKey());
                            docs.add(item);
                        }
                        emitter.onNext(docs);
                    } else {
                        log.w(TAG, "No data");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            };
            reference.addValueEventListener(valueListener);

            emitter.setCancellable(() -> reference.removeEventListener(valueListener));
        }, BackpressureStrategy.LATEST);
    }

    @Override
    public Completable create(GroceryListId listId, String category, String name) {
        return Completable.create((emitter) -> {
            DatabaseReference reference = firebaseDatabase.getReference("lists")
                    .child(listId.id())
                    .child("items");
            DatabaseReference newItem = reference.push();
            newItem.setValue(new GroceryListItemDocument(category, name, false))
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }).andThen(updateNumberOfItems(listId));
    }

    @Override
    public Completable update(GroceryListId listId, GroceryListItemId itemId, boolean purchased) {
        return Completable.create((emitter) -> {
            DatabaseReference reference = firebaseDatabase.getReference("lists")
                    .child(listId.id())
                    .child("items")
                    .child(itemId.id());
            reference.updateChildren(new HashMap<String, Object>() {{
                put("purchased", purchased);
            }})
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        }).andThen(updateNumberOfItems(listId));
    }

    @Override
    public Completable deletePurchased(GroceryListId listId) {
        return Flowable.<List<GroceryListItemDocument>>create(emitter -> {
            Query query = firebaseDatabase.getReference("lists")
                    .child(listId.id())
                    .child("items")
                    .orderByChild("purchased")
                    .equalTo(true);
            ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        List<GroceryListItemDocument> docs = new ArrayList<>();
                        for (DataSnapshot doc : dataSnapshot.getChildren()) {
                            GroceryListItemDocument item = doc.getValue(GroceryListItemDocument.class);
                            item.setId(doc.getKey());
                            docs.add(item);
                        }
                        emitter.onNext(docs);
                    } else {
                        log.w(TAG, "No data");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        }, BackpressureStrategy.LATEST)
                .firstOrError()
                .flatMapCompletable(items -> {
                    DatabaseReference itemsReference = firebaseDatabase.getReference("lists")
                            .child(listId.id())
                            .child("items");

                    for (GroceryListItemDocument child : items) {
                        itemsReference.child(child.getId())
                                .removeValue();
                    }
                    return Completable.complete();
                })
                .andThen(updateNumberOfItems(listId));
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
                        firebaseDatabase.getReference("lists")
                                .child(listId.id())
                                .updateChildren(new HashMap<String, Object>() {{
                                    put("purchased", finalPurchased);
                                    put("total", finalTotal);
                                }}, (databaseError, databaseReference) -> {
                                    if (databaseError != null) {
                                        emitter.onError(databaseError.toException());
                                    } else {
                                        emitter.onComplete();
                                    }
                                });
                    });
                });
    }
}
