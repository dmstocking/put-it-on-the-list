package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CategoryEntity {

    public static CategoryEntity create(int id, @NonNull String name, int order) {
        return new AutoValue_CategoryEntity.Builder()
                .setId(id)
                .setName(name)
                .setOrder(order)
                .build();
    }

    public abstract int id();
    @NonNull public abstract String name();
    public abstract int order();

    abstract Builder toBuilder();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(int id);
        public abstract Builder setName(@NonNull String name);
        public abstract Builder setOrder(int order);

        public abstract CategoryEntity build();
    }
}
