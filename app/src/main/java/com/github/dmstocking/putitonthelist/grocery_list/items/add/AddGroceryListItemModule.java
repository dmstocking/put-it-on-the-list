package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Module;
import dagger.Provides;

@Module
public class AddGroceryListItemModule {

    @NonNull private final AddGroceryListItemArgs args;
    @NonNull private final AddGroceryListItemController controller;

    public AddGroceryListItemModule(@NonNull AddGroceryListItemArgs args,
                                    @NonNull AddGroceryListItemController controller) {
        this.args = args;
        this.controller = controller;
    }

    @Provides
    @PerController
    AddGroceryListItemArgs providesArgs() {
        return args;
    }

    @Provides
    @PerController
    AddGroceryListItemContract.View providesView() {
        return controller;
    }

    @Provides
    @PerController
    AddGroceryListItemContract.Presenter providesPresenter(AddGroceryListItemPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerController
    AddGroceryListItemOnClick providesAddGroceryListItemOnClick() {
        return controller;
    }
}
