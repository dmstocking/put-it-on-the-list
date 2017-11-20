package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.List;

@AutoValue
public abstract class ViewModel {

    public static ViewModel create(URI categoryImage, List<ListItemViewModel> items) {
        return new AutoValue_ViewModel(categoryImage, items);
    }

    @NonNull public abstract URI categoryImage();
    @NonNull public abstract List<ListItemViewModel> items();
}
