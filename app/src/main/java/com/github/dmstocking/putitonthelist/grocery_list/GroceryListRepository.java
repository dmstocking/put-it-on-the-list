package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.GroceryListDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.main.GroceryListItem;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import static android.content.ContentResolver.SCHEME_ANDROID_RESOURCE;

@Singleton
public class GroceryListRepository {

    private static final String TAG = "GroceryListRepository";

    @NonNull private final FirebaseFirestore firestore;
    @NonNull private final Log log;

    @Inject
    public GroceryListRepository(@NonNull FirebaseFirestore firestore,
                                 @NonNull Log log) {
        this.firestore = firestore;
        this.log = log;
    }

    public Flowable<GroceryListDocument> getGroceryListDocument(@NonNull GroceryListId id) {
        return Flowable.create(emitter -> {
            ListenerRegistration listener = firestore.collection("lists")
                    .document(id.id())
                    .addSnapshotListener((documentSnapshot, e) -> {
                        if (e != null) {
                            emitter.onError(e);
                        } else if (documentSnapshot != null) {
                            GroceryListDocument item = documentSnapshot.toObject(GroceryListDocument.class);
                            item.setId(documentSnapshot.getId());
                            emitter.onNext(item);
                        } else {
                            log.w(TAG, "No data");
                        }
                    });

            emitter.setCancellable(listener::remove);
        }, BackpressureStrategy.LATEST);
    }
}
