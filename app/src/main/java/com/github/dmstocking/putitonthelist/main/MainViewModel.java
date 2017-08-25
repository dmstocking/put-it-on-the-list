package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public static MainViewModel create(@NonNull List<GroceryListViewModel> groceryLists) {
        return new AutoValue_MainViewModel(groceryLists);
    }

    @NonNull public abstract List<GroceryListViewModel> groceryLists();
}
