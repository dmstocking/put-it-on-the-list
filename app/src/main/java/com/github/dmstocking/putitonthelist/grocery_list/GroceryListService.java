package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.main.GroceryListItem;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

import static android.content.ContentResolver.SCHEME_ANDROID_RESOURCE;

@Singleton
public class GroceryListService {

    private static final String TAG = "GroceryListRepository";

    @NonNull private final GroceryListRepository repository;

    @Inject
    public GroceryListService(@NonNull GroceryListRepository repository) {
        this.repository = repository;
    }

    public Flowable<GroceryListViewModel> getModel(@NonNull GroceryListId id) {
        return repository.getGroceryListDocument(id)
                .map(document -> {
                    List<ListViewModel> list = new ArrayList<>();
                    for (GroceryListItem item : document.getGroceryListItems()) {
                        list.add(ListViewModel.create(
                                item.getName().hashCode(),
                                URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food),
                                item.getName()));
                    }
                    return GroceryListViewModel.create(list);
                });
    }
}
