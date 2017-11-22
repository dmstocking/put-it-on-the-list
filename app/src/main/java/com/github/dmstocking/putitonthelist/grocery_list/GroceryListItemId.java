package com.github.dmstocking.putitonthelist.grocery_list;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class GroceryListItemId {

    public static GroceryListItemId create(String id) {
        return new AutoValue_GroceryListItemId(id);
    }

    public abstract String id();
}

