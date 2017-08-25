package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.main.MainComponent;
import com.github.dmstocking.putitonthelist.main.MainModule;
import com.github.dmstocking.putitonthelist.uitl.UtilModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        UtilModule.class,
})
public interface CoreComponent {

    MainComponent mainComponent(MainModule mainModule);
}
