package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AddGroceryListItemArgs implements Parcelable {

    public static AddGroceryListItemArgs create(@NonNull GroceryListId id) {
        return new AutoValue_AddGroceryListItemArgs(id);
    }

    @NonNull public abstract GroceryListId id();
}
