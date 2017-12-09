package com.github.dmstocking.putitonthelist.main.widget;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Module;
import dagger.Provides;

@Module
public class WidgetModule {

    @NonNull private final WidgetService widgetService;

    public WidgetModule(@NonNull WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @Provides
    @PerController
    Context providesContext() {
        return widgetService.getApplicationContext();
    }
}
