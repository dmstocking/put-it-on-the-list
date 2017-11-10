package com.github.dmstocking.putitonthelist.grocery_list;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GroceryListArguments implements Parcelable {

    public static GroceryListArguments create(@NonNull GroceryListId groceryListId) {
        return new AutoValue_GroceryListArguments(groceryListId);
    }

    @NonNull public abstract GroceryListId groceryListId();
}
