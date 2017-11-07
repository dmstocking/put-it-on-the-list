package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class MainViewModel {

    public static MainViewModel create(@NonNull String actionTitle,
                                       @NonNull List<ListViewModel> groceryLists,
                                       boolean isSelecting) {
        return new AutoValue_MainViewModel(actionTitle, groceryLists, isSelecting);
    }

    @NonNull public abstract String actionTitle();
    @NonNull public abstract List<ListViewModel> groceryLists();
    public abstract boolean isSelecting();
}
