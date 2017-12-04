package com.github.dmstocking.putitonthelist.grocery_list;

import com.github.dmstocking.putitonthelist.Color;
import com.google.auto.value.AutoValue;

import java.net.URI;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class ListViewModel {

    public static ListViewModel create(GroceryListItemId id,
                                       @NonNull URI icon,
                                       @NonNull Color iconBackground,
                                       @NonNull String name,
                                       boolean purchased,
                                       boolean editing) {
        return new AutoValue_ListViewModel(id, icon, iconBackground, name, purchased, editing);
    }

    public abstract GroceryListItemId id();
    @NonNull public abstract URI icon();
    @NonNull public abstract Color iconBackground();
    @NonNull public abstract String name();
    public abstract boolean purchased();
    public abstract boolean editing();
}
