package com.github.dmstocking.putitonthelist.main;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    public MainContract.Presenter providesMainContractPresenter(MainPresenter presenter) {
        return presenter;
    }
}
