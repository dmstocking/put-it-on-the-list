package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SortArguments implements Parcelable {

    public static SortArguments create(@NonNull GroceryListId groceryListId) {
        return new AutoValue_SortArguments(groceryListId);
    }

    @NonNull public abstract GroceryListId groceryListId();
}
