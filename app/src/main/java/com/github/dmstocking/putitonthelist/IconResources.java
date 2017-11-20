package com.github.dmstocking.putitonthelist;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.uitl.UriUtils;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IconResources {

    @NonNull private final UriUtils uriUtils;

    @Inject
    public IconResources(@NonNull UriUtils uriUtils) {
        this.uriUtils = uriUtils;
    }

    public URI unknown() {
        return uriUtils.fromResource(R.drawable.ic_unknown);
    }
}
