package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SavedState implements Parcelable {

    public static SavedState create(String category, String name) {
        return new AutoValue_SavedState(category, name);
    }

    @NonNull public abstract String category();
    @NonNull public abstract String name();
}
