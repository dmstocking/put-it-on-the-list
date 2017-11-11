package com.github.dmstocking.putitonthelist.grocery_list;

import com.google.auto.value.AutoValue;

import java.net.URI;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class ListViewModel {

    public static ListViewModel create(String id,
                                       @NonNull URI icon,
                                       @NonNull String name,
                                       boolean purchased,
                                       boolean editing) {
        return new AutoValue_ListViewModel(id, icon, name, purchased, editing);
    }

    public abstract String id();
    @NonNull public abstract URI icon();
    @NonNull public abstract String name();
    public abstract boolean purchased();
    public abstract boolean editing();
}
