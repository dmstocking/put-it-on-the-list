package com.github.dmstocking.putitonthelist.uitl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;

import static android.content.ContentResolver.SCHEME_ANDROID_RESOURCE;

@Singleton
public class UriUtils {

    @NonNull private final String packageName;

    @Inject
    public UriUtils(@Named("package") String packageName) {
        this.packageName = packageName;
    }

    public URI fromResource(int resourceId) {
        try {
            return new URI(SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + resourceId);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
