package com.github.dmstocking.putitonthelist.grocery_list;

import com.google.auto.value.AutoValue;

import java.util.List;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class GroceryListViewModel {

    public static GroceryListViewModel create(@NonNull List<ListViewModel> groceryList) {
        return new AutoValue_GroceryListViewModel(groceryList);
    }

    @NonNull public abstract List<ListViewModel> groceryList();
}
