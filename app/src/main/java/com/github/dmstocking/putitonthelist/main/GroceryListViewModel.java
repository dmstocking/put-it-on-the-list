package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GroceryListViewModel {

    public static GroceryListViewModel create(
            int id,
            @NonNull String name,
            int acquiredItems,
            int totalItems) {
        return new AutoValue_GroceryListViewModel(id, name, acquiredItems, totalItems);
    }

    public abstract int id();
    @NonNull public abstract String name();
    public abstract int acquiredItems();
    public abstract int totalItems();
}
