package com.github.dmstocking.putitonthelist.grocery_list;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

@Module
public class GroceryListModule {

    @NonNull private final GroceryListController groceryListController;

    public GroceryListModule(GroceryListController groceryListController) {
        this.groceryListController = groceryListController;
    }

    @Provides
    public GroceryListContract.Presenter providesMainContractPresenter(GroceryListPresenter presenter) {
        return presenter;
    }

    @Provides
    public OnGroceryListItemClicked providesOnGroceryListItemClicked() {
        return groceryListController;
    }
}
