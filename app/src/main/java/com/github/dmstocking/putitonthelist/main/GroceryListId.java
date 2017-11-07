package com.github.dmstocking.putitonthelist.main;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GroceryListId {

    public static GroceryListId create(String id) {
        return new AutoValue_GroceryListId(id);
    }

    public abstract String id();
}
