package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ListViewModel {

    public enum Type { AD_BANNER, ITEM }

    public static ListViewModel create(
            @NonNull Type type,
            @NonNull GroceryListId id,
            @NonNull String headline,
            @NonNull String trailingCaption,
            boolean selected) {
        return new AutoValue_ListViewModel(type, id, headline, trailingCaption, selected);
    }

    @NonNull public abstract Type type();
    @NonNull public abstract GroceryListId id();
    @NonNull public abstract String headline();
    @NonNull public abstract String trailingCaption();
    public abstract boolean selected();
}
