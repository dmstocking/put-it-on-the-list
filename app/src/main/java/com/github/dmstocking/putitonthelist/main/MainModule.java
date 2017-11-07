package com.github.dmstocking.putitonthelist.main;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class MainModule {

    @NonNull private final MainController mainController;

    public MainModule(MainController mainController) {
        this.mainController = mainController;
    }

    @Provides
    public MainContract.Presenter providesMainContractPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    public OnGroceryListClicked providesOnGroceryListClicked() {
        return mainController;
    }

    @Provides
    public OnGroceryListLongClicked providesOnGroceryListLongClicked() {
        return mainController;
    }
}
