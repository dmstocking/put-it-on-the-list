package com.github.dmstocking.putitonthelist.main;

import dagger.Subcomponent;

@Subcomponent(modules = {
        MainModule.class,
})
public interface MainComponent {

    void inject(MainController mainController);
}
