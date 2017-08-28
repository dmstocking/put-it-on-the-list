package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.R;

import java.net.URI;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

import static android.content.ContentResolver.SCHEME_ANDROID_RESOURCE;

@Singleton
public class GroceryListRepository {

    @Inject
    public GroceryListRepository() {
    }

    public Flowable<GroceryListViewModel> getModel() {
        return Flowable.defer(() -> {
            return Flowable.just(buildModel());
        });
    }

    @NonNull
    private GroceryListViewModel buildModel() {
        return GroceryListViewModel.create(
                new ArrayList<ListViewModel>() {{
                    add(ListViewModel.create(1, URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food), "Bread"));
                    add(ListViewModel.create(2, URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food), "Milk"));
                    add(ListViewModel.create(3, URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food), "Eggs"));
                    add(ListViewModel.create(4, URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food), "Cheese"));
                    add(ListViewModel.create(5, URI.create(SCHEME_ANDROID_RESOURCE + "://" + R.drawable.ic_food), "Cereal"));
                }}
        );
    }
}
