package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.main.widget.WidgetModule;
import com.github.dmstocking.putitonthelist.main.widget.WidgetService;

import dagger.Subcomponent;

@PerController
@Subcomponent(modules = {
        WidgetModule.class,
})
public interface WidgetComponent {
    void inject(WidgetService widgetService);
}
