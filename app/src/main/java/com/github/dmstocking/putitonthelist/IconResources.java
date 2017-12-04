package com.github.dmstocking.putitonthelist;

import android.support.annotation.DrawableRes;
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

    public URI apple() {
        return from(R.drawable.ic_apple);
    }

    public URI babyPacifier() {
        return from(R.drawable.ic_baby_pacifier);
    }

    public URI bottle() {
        return from(R.drawable.ic_bottle);
    }

    public URI cake() {
        return from(R.drawable.ic_cake_whole);
    }

    public URI can() {
        return from(R.drawable.ic_food_can);
    }

    public URI cheese() {
        return from(R.drawable.ic_cheese);
    }

    public URI chickenDrumStick() {
        return from(R.drawable.ic_chicken_drum_stick);
    }

    public URI christmasSnowflake() {
        return from(R.drawable.ic_christmas_snowflake);
    }

    public URI firstAid() {
        return from(R.drawable.ic_first_aid_kit);
    }

    public URI flag() {
        return from(R.drawable.ic_flag);
    }

    public URI oven() {
        return from(R.drawable.ic_oven);
    }

    public URI paw() {
        return from(R.drawable.ic_paw);
    }

    public URI pretzel() {
        return from(R.drawable.ic_pretzel);
    }

    public URI refrigerator() {
        return from(R.drawable.ic_refrigerator);
    }

    public URI soap() {
        return from(R.drawable.ic_hand_soap);
    }

    public URI sodaCan() {
        return from(R.drawable.ic_soda_can);
    }

    public URI steak() {
        return from(R.drawable.ic_steak);
    }

    public URI questionMark() {
        return from(R.drawable.ic_question_mark);
    }

    public URI vacuum() {
        return from(R.drawable.ic_vaccum_cleaner);
    }

    public URI from(@DrawableRes int drawable) {
        return uriUtils.fromResource(drawable);
    }
}
