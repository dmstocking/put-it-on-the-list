package com.github.dmstocking.putitonthelist.grocery_list.sort.firebase_database;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryId;
import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class CategoryRepositoryFirebaseDatabase implements CategoryRepository {

    private final static String TAG = "CategoryRepository";

    @NonNull private final FirebaseDatabase firebaseDatabase;
    @NonNull private final Log log;

    @Inject
    public CategoryRepositoryFirebaseDatabase(@NonNull FirebaseDatabase firebaseDatabase,
                                              @NonNull Log log) {
        this.firebaseDatabase = firebaseDatabase;
        this.log = log;
    }

    @Override@NonNull
    public Single<CategoryDocument> fetchCategory(@NonNull GroceryListId groceryListId, @NonNull String name) {
        return Single.create(emitter -> {
            Query query = firebaseDatabase.getReference("lists")
                    .child(groceryListId.id())
                    .child("categories")
                    .orderByChild("category")
                    .equalTo(name);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CategoryDocument> documents = toList(dataSnapshot);
                    if (documents.size() == 1) {
                        emitter.onSuccess(documents.get(0));
                    } else {
                        emitter.onError(new NoSuchElementException());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            };
            query.addValueEventListener(listener);
            emitter.setCancellable(() -> query.removeEventListener(listener));
        });
    }

    @Override@NonNull
    public Flowable<List<CategoryDocument>> fetchAllCategories(@NonNull GroceryListId groceryListId) {
        return Flowable.create(emitter -> {
            Query query = firebaseDatabase.getReference("lists")
                    .child(groceryListId.id())
                    .child("categories")
                    .orderByChild("order");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CategoryDocument> documents = toList(dataSnapshot);
                    Collections.reverse(documents);
                    emitter.onNext(documents);
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

    @Override@NonNull
    public Single<List<CategoryDocument>> getAllCategories(@NonNull GroceryListId groceryListId) {
        return Single.create(emitter -> {
            Query query = firebaseDatabase.getReference("lists")
                    .child(groceryListId.id())
                    .child("categories")
                    .orderByChild("order");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CategoryDocument> documents = toList(dataSnapshot);
                    Collections.reverse(documents);
                    emitter.onSuccess(documents);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            };
            query.addValueEventListener(listener);
            emitter.setCancellable(() -> query.removeEventListener(listener));
        });
    }

    @Override@NonNull
    public Completable setSortOrder(@NonNull GroceryListId groceryListId,
                                    @NonNull List<CategoryId> order) {
        return Completable.create(emitter -> {
            DatabaseReference reference = firebaseDatabase.getReference("lists")
                    .child(groceryListId.id())
                    .child("categories");
            int index = 0;
            for (CategoryId id : order) {
                int finalIndex = index;
                reference.child(id.id())
                        .updateChildren(new HashMap<String, Object>() {{
                            put("order", finalIndex);
                        }});
                index++;
            }
            emitter.onComplete();
        });
    }

    @Override public Completable createDefaultCategories(GroceryListId id) {
        return Completable.create(emitter -> {
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
                    addCategory("other", Color.GREY, Icon.OTHER);
                }
            };
            DatabaseReference reference = firebaseDatabase.getReference("lists")
                    .child(id.id())
                    .child("categories");
            for (CategoryDocument doc : documents) {
                DatabaseReference newValue = reference.push();
                newValue.setValue(doc);
            }
            emitter.onComplete();
        });
    }

    private List<CategoryDocument> toList(DataSnapshot dataSnapshot) {
        List<CategoryDocument> documents = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            CategoryDocument doc = child.getValue(CategoryDocument.class);
            doc.setId(child.getKey());
            documents.add(doc);
        }
        return documents;
    }
}
