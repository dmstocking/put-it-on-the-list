package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryId implements Parcelable {

    public static CategoryId create(String id) {
        return new AutoValue_CategoryId(id);
    }

    public abstract String id();
}

