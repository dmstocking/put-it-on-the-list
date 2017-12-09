package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.main.widget.WidgetService;

import dagger.Subcomponent;

@PerController
@Subcomponent()
public interface WidgetComponent {
    void inject(WidgetService widgetService);
}
