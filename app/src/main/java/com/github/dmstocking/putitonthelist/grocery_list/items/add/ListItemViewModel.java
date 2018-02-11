package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Color;
import com.google.auto.value.AutoValue;

import java.net.URI;

@AutoValue
public abstract class ListItemViewModel {

    public static ListItemViewModel create(@NonNull String id,
                                           @NonNull URI image,
                                           @NonNull String name,
                                           @NonNull Color textColor,
                                           @NonNull Color backgroundColor,
                                           @NonNull CategoryDocument categoryDocument) {
        return new AutoValue_ListItemViewModel(id, image, name, textColor, backgroundColor, categoryDocument);
    }

    @NonNull public abstract String id();
    @NonNull public abstract URI image();
    @NonNull public abstract String name();
    @NonNull public abstract Color textColor();
    @NonNull public abstract Color backgroundColor();
    @NonNull public abstract CategoryDocument category();
}
