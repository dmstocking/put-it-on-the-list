package com.github.dmstocking.putitonthelist.grocery_list;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.uitl.UriUtils;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;

@Singleton
public class GroceryListResources {

    @NonNull private final UriUtils uriUtils;

    @Inject
    public GroceryListResources(UriUtils uriUtils) {
        this.uriUtils = uriUtils;
    }

    public URI food() {
        return uriUtils.fromResource(R.drawable.ic_food);
    }

    public URI apple() {
        return uriUtils.fromResource(R.drawable.ic_apple);
    }

    public URI croissant() {
        return uriUtils.fromResource(R.drawable.ic_croissant);
    }

    public URI unknown() {
        return uriUtils.fromResource(R.drawable.ic_unknown);
    }
}
