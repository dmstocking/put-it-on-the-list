package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class MainRepository {

    @Inject
    public MainRepository() {
    }

    public Flowable<MainViewModel> getModel() {
        return Flowable.defer(() -> {
            return Flowable.just(buildModel());
        });
    }

    @NonNull
    private MainViewModel buildModel() {
        return MainViewModel.create(
                new ArrayList<ListViewModel>() {{
                    add(ListViewModel.create(1, "Kroger", 013, 37));
                }}
        );
    }
}
