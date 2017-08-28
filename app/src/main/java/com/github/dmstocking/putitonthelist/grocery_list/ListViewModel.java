package com.github.dmstocking.putitonthelist.grocery_list;

import com.google.auto.value.AutoValue;

import java.net.URI;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class ListViewModel {

    public static ListViewModel create(int id, @NonNull URI icon, @NonNull String name) {
        return new AutoValue_ListViewModel(id, icon, name);
    }

    public abstract int id();
    @NonNull public abstract URI icon();
    @NonNull public abstract String name();
}
