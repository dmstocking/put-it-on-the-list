package com.github.dmstocking.putitonthelist.main;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GroceryListId implements Parcelable {

    public static GroceryListId create(String id) {
        return new AutoValue_GroceryListId(id);
    }

    public abstract String id();
}
