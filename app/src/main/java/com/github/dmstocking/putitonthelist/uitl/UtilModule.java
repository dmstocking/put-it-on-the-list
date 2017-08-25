package com.github.dmstocking.putitonthelist.uitl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    @Provides
    @Singleton
    public Log providesLog(AndroidLog log) {
        return log;
    }
}
