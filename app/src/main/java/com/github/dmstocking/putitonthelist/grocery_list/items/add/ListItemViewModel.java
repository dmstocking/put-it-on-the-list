package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.dmstocking.putitonthelist.Color;
import com.google.auto.value.AutoValue;

import java.net.URI;

@AutoValue
public abstract class ListItemViewModel {

    public static ListItemViewModel create(@NonNull String id,
                                           @NonNull URI image,
                                           @NonNull String name,
                                           @NonNull Color color,
                                           @NonNull CategoryDocument categoryDocument) {
        return new AutoValue_ListItemViewModel(id, image, name, color, categoryDocument);
    }

    @NonNull public abstract String id();
    @NonNull public abstract URI image();
    @NonNull public abstract String name();
    @NonNull public abstract Color color();
    @NonNull public abstract CategoryDocument category();
}
