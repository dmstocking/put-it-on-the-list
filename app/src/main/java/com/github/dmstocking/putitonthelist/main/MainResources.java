package com.github.dmstocking.putitonthelist.main;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainResources {

    @NonNull private final Resources resources;

    @Inject
    public MainResources(@NonNull Resources resources) {
        this.resources = resources;
    }

    public String getActionTitle(int selected) {
        return resources.getString(R.string.main_action_title, selected);
    }
}
