package com.github.dmstocking.putitonthelist.main.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.PerController;

import javax.inject.Named;

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

    @Provides
    @PerController
    AppWidgetManager providesAppWidgetManager(Context context) {
        return (AppWidgetManager) context.getSystemService(Context.APPWIDGET_SERVICE);
    }

    @Provides
    @PerController
    @Named("packageName")
    String providesPackageName(Context context) {
        return context.getPackageName();
    }
}
