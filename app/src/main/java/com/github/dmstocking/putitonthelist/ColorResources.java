package com.github.dmstocking.putitonthelist;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ColorResources {

    @NonNull private final Resources resources;

    @Inject
    public ColorResources(@NonNull Resources resources) {
        this.resources = resources;
    }

    @ColorInt
    public int color(Color color) {
        switch (color) {
            case RED: return resources.getColor(R.color.md_red_500);
            case PINK: return resources.getColor(R.color.md_pink_500);
            case PURPLE: return resources.getColor(R.color.md_purple_500);
            case DEEP_PURPLE: return resources.getColor(R.color.md_deep_purple_500);
            case INDIGO: return resources.getColor(R.color.md_indigo_500);
            case BLUE: return resources.getColor(R.color.md_blue_500);
            case LIGHT_BLUE: return resources.getColor(R.color.md_light_blue_500);
            case CYAN: return resources.getColor(R.color.md_cyan_500);
            case TEAL: return resources.getColor(R.color.md_teal_500);
            case GREEN: return resources.getColor(R.color.md_green_500);
            case LIGHT_GREEN: return resources.getColor(R.color.md_light_green_500);
            case LIME: return resources.getColor(R.color.md_lime_500);
            case YELLOW: return resources.getColor(R.color.md_yellow_500);
            case AMBER: return resources.getColor(R.color.md_amber_500);
            case ORANGE: return resources.getColor(R.color.md_orange_500);
            case DEEP_ORANGE: return resources.getColor(R.color.md_deep_orange_500);
            case BROWN: return resources.getColor(R.color.md_brown_500);
            default:
            case GREY: return resources.getColor(R.color.md_grey_500);
        }
    }
}
