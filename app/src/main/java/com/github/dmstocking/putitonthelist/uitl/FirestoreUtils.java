package com.github.dmstocking.putitonthelist.uitl;

import android.support.annotation.NonNull;

import com.github.dmstocking.optional.java.util.function.Supplier;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class FirestoreUtils {

    public static final String TAG = "FireStore";
    @NonNull private final Log log;

    @Inject
    public FirestoreUtils(@NonNull Log log) {
        this.log = log;
    }

    public Completable deleteDocument(final DocumentReference document) {
        return Completable.create(emitter -> {
            document.delete()
                    .addOnSuccessListener(documentReference -> {
                        emitter.onComplete();
                    }).addOnFailureListener(e -> {
                        emitter.onError(e);
                    });
        });
    }

    public Completable deleteCollection(final CollectionReference collection) {
        return deleteCollection(collection, 500);
    }

    public Completable deleteInBatch(Supplier<Query> querySupplier) {
        return deleteInBatch(querySupplier, 500);
    }

    private Completable deleteCollection(final CollectionReference collection,
                                         final int batchSize) {
        return deleteInBatch(() -> collection.orderBy(FieldPath.documentId()), batchSize);
    }

    private Completable deleteInBatch(Supplier<Query> querySupplier,
                                      final int batchSize) {
        return Completable.defer(() -> {
            Query query = querySupplier.get()
                    .limit(batchSize);

            List<DocumentSnapshot> deleted = deleteQueryBatch(query);
            while (deleted.size() >= batchSize) {
                DocumentSnapshot last = deleted.get(deleted.size() - 1);
                query = querySupplier.get()
                        .startAfter(last.getId())
                        .limit(batchSize);
                deleted = deleteQueryBatch(query);
            }

            return Completable.complete();
        });
    }

    private List<DocumentSnapshot> deleteQueryBatch(final Query query) throws Exception {
        QuerySnapshot querySnapshot = Tasks.await(query.get());
        WriteBatch batch = query.getFirestore().batch();
        for (DocumentSnapshot snapshot : querySnapshot) {
            batch.delete(snapshot.getReference());
        }

        batch.commit();
        return querySnapshot.getDocuments();
    }
}
