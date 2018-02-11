package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Color;
import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.List;

@AutoValue
public abstract class ViewModel {

    public static ViewModel create(boolean doneEnabled, URI categoryImage, Color categoryColor, List<ListItemViewModel> items) {
        return new AutoValue_ViewModel(doneEnabled, categoryImage, categoryColor, items);
    }

    public abstract boolean doneEnabled();
    @NonNull public abstract URI categoryImage();
    @NonNull public abstract Color categoryColor();
    @NonNull public abstract List<ListItemViewModel> items();
}
