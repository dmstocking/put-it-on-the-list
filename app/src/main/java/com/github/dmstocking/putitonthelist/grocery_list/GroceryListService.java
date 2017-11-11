package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.dmstocking.putitonthelist.main.GroceryListId;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class GroceryListService {

    @NonNull private final GroceryListRepository repository;
    @NonNull private final GroceryListResources resources;

    @Inject
    public GroceryListService(@NonNull GroceryListRepository repository,
                              @NonNull GroceryListResources resources) {
        this.repository = repository;
        this.resources = resources;
    }

    public Flowable<GroceryListViewModel> getModel(@NonNull GroceryListId id) {
        return repository.getGroceryListDocument(id)
                .map(documents -> {
                    List<ListViewModel> list = new ArrayList<>();
                    for (GroceryListItemDocument item : documents) {
                        list.add(ListViewModel.create(
                                item.getId(),
                                toUri(item.getCategory()),
                                item.getName(),
                                item.isPurchased(),
                                false));
                    }
                    return GroceryListViewModel.create(list);
                });
    }

    @NonNull
    private URI toUri(@Nullable String category) {
        if (category == null) {
            return resources.unknown();
        }

        switch (category) {
            case "fruit": return resources.apple();
            case "bakery": return resources.croissant();
            default: return resources.unknown();
        }
    }
}
