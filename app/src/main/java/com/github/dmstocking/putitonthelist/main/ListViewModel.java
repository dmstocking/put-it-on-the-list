package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ListViewModel {

    public static ListViewModel create(
            GroceryListId id,
            @NonNull String headline,
            @NonNull String trailingCaption,
            boolean selected) {
        return new AutoValue_ListViewModel(id, headline, trailingCaption, selected);
    }

    public abstract GroceryListId id();
    @NonNull public abstract String headline();
    @NonNull public abstract String trailingCaption();
    public abstract boolean selected();
}
