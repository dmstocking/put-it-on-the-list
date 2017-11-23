package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.net.URI;

@AutoValue
public abstract class SortItemViewModel {

    public static SortItemViewModel create(CategoryId id, @NonNull URI icon, @NonNull String name) {
        return new AutoValue_SortItemViewModel(id, icon, name);
    }

    public abstract CategoryId id();
    @NonNull public abstract URI icon();
    @NonNull public abstract String name();
}
