package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Module;
import dagger.Provides;

@Module
public class AddGroceryListItemModule {

    @Provides
    @PerController
    AddGroceryListItemContract.Presenter providesPresenter(AddGroceryListItemPresenter presenter) {
        return presenter;
    }
}
