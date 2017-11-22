package com.github.dmstocking.putitonthelist.uitl;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.IconResources;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IconUtils {

    @NonNull private final IconResources iconResources;

    @Inject
    public IconUtils(@NonNull IconResources iconResources) {
        this.iconResources = iconResources;
    }

    public URI iconToUri(@NonNull Icon icon) {
        switch (icon) {
            case FREEZER:
            case DELI:
            case BAKERY:
            case PRODUCE:
            case BEVERAGES:
            case DAIRY:
            case REFRIGERATED:
            case MEAT:
            case SNACKS:
            case CANNED:
            case CONDIMENTS:
            case BAKING:
            case INTERNATIONAL:
            case PETS:
            case BABY:
            case PERSONAL_CARE:
            case MEDICINE:
            case CLEANING:
            case UNKNOWN:
            case OTHER:
            default: return iconResources.unknown();
        }
    }
}
