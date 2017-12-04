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
            case FREEZER: return iconResources.christmasSnowflake();
            case DELI: return iconResources.steak();
            case BAKERY: return iconResources.cake();
            case PRODUCE: return iconResources.apple();
            case BEVERAGES: return iconResources.sodaCan();
            case DAIRY: return iconResources.cheese();
            case REFRIGERATED: return iconResources.refrigerator();
            case MEAT: return iconResources.chickenDrumStick();
            case SNACKS: return iconResources.pretzel();
            case CANNED: return iconResources.can();
            case CONDIMENTS: return iconResources.bottle();
            case BAKING: return iconResources.oven();
            case INTERNATIONAL: return iconResources.flag();
            case PETS: return iconResources.paw();
            case BABY: return iconResources.babyPacifier();
            case PERSONAL_CARE: return iconResources.soap();
            case MEDICINE: return iconResources.firstAid();
            case CLEANING: return iconResources.vacuum();
            case OTHER:
            default: return iconResources.questionMark();
        }
    }
}
