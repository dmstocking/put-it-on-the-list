package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class CategoryRepository {

    private final static String TAG = "CategoryRepository";

    @NonNull private final FirebaseFirestore firestore;
    @NonNull private final Log log;

    @Inject
    public CategoryRepository(@NonNull FirebaseFirestore firestore,
                              @NonNull Log log) {
        this.firestore = firestore;
        this.log = log;
    }

    @NonNull
    public Single<CategoryDocument> fetchCategory(@NonNull GroceryListId groceryListId, @NonNull String name) {
        return Single.create(emitter -> {
            firestore.collection("lists")
                    .document(groceryListId.id())
                    .collection("categories")
                    .whereEqualTo("category", name)
                    .get()
                    .addOnFailureListener(throwable -> {
                        emitter.onError(throwable);
                    })
                    .addOnSuccessListener(snapshot -> {
                        List<CategoryDocument> documents = snapshot.toObjects(CategoryDocument.class);
                        if (documents.size() == 1) {
                            CategoryDocument doc = documents.get(0);
                            doc.setId(documents.get(0).getId());
                            emitter.onSuccess(doc);
                        } else {
                            emitter.onError(new NoSuchElementException());
                        }
                    });
        });
    }

    @NonNull
    public Flowable<List<CategoryDocument>> fetchAllCategories(@NonNull GroceryListId groceryListId) {
        return Flowable.create(emitter -> {
            ListenerRegistration registration = firestore.collection("lists")
                    .document(groceryListId.id())
                    .collection("categories")
                    .orderBy("order", Query.Direction.ASCENDING)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null) {
                            emitter.onError(e);
                        } else if (querySnapshot != null) {
                            List<CategoryDocument> docs = new ArrayList<>();
                            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                                CategoryDocument item = doc.toObject(CategoryDocument.class);
                                item.setId(doc.getId());
                                docs.add(item);
                            }
                            emitter.onNext(docs);
                        } else {
                            log.w(TAG, "No data");
                        }
                    });
            emitter.setCancellable(registration::remove);
        }, BackpressureStrategy.LATEST);
    }

    @NonNull
    public Single<List<CategoryDocument>> getAllCategories(@NonNull GroceryListId groceryListId) {
        return Single.create(emitter -> {
            firestore.collection("lists")
                    .document(groceryListId.id())
                    .collection("categories")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<CategoryDocument> docs = new ArrayList<>();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CategoryDocument item = doc.toObject(CategoryDocument.class);
                            item.setId(doc.getId());
                            docs.add(item);
                        }
                        emitter.onSuccess(docs);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @NonNull
    public Completable setSortOrder(@NonNull GroceryListId groceryListId,
                                    @NonNull List<CategoryId> order) {
        return Completable.create(emitter -> {
            firestore.runTransaction((Transaction.Function<Void>) transaction -> {
                CollectionReference collection = firestore.collection("lists")
                        .document(groceryListId.id())
                        .collection("categories");
                int index = 0;
                for (CategoryId id : order) {
                    collection.document(id.id())
                            .update("order", index);
                    index++;
                }
                return null;
            })
            .addOnSuccessListener(aVoid -> {
                emitter.onComplete();
            })
            .addOnFailureListener(emitter::onError);
        });
    }

    public Completable createDefaultCategories(GroceryListId id) {
        return Completable.create(emitter -> {
            firestore.runTransaction((Transaction.Function<Void>) transaction -> {
                List<CategoryDocument> documents = new ArrayList<CategoryDocument>() {

                    private int id = 0;

                    private void addCategory(String category, Color color, Icon icon) {
                        id++;
                        add(new CategoryDocument("", category, color, icon, id));
                    }

                    {
                        addCategory("deli", Color.PINK, Icon.DELI);
                        addCategory("bakery", Color.BROWN, Icon.BAKERY);
                        addCategory("produce", Color.GREEN, Icon.PRODUCE);
                        addCategory("beverages", Color.LIGHT_BLUE, Icon.BEVERAGES);
                        addCategory("snacks", Color.INDIGO, Icon.SNACKS);
                        addCategory("condiments", Color.ORANGE, Icon.CONDIMENTS);
                        addCategory("canned", Color.LIME, Icon.CANNED);
                        addCategory("baking", Color.DEEP_ORANGE, Icon.BAKING);
                        addCategory("international", Color.AMBER, Icon.INTERNATIONAL);
                        addCategory("meat", Color.RED, Icon.MEAT);
                        addCategory("refrigerated", Color.BROWN, Icon.REFRIGERATED);
                        addCategory("dairy", Color.LIGHT_BLUE, Icon.DAIRY);
                        addCategory("freezer", Color.BLUE, Icon.FREEZER);
                        addCategory("pets", Color.LIGHT_GREEN, Icon.PETS);
                        addCategory("baby", Color.PINK, Icon.BABY);
                        addCategory("personal care", Color.LIGHT_GREEN, Icon.PERSONAL_CARE);
                        addCategory("medicine", Color.TEAL, Icon.MEDICINE);
                        addCategory("cleaning", Color.CYAN, Icon.CLEANING);
                        addCategory("unknown", Color.GREY, Icon.UNKNOWN);
                        addCategory("other", Color.GREY, Icon.OTHER);
                    }
                };
                CollectionReference collection = firestore.collection("lists")
                        .document(id.id())
                        .collection("categories");
                for (CategoryDocument doc : documents) {
                    collection.add(doc);
                }
                return null;
            })
                    .addOnSuccessListener(aVoid -> {
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
