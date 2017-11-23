package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.PerController;
import com.github.dmstocking.putitonthelist.main.GroceryListId;

import javax.inject.Inject;

import io.reactivex.Observable;

@PerController
public class AddGroceryListItemPresenter implements AddGroceryListItemContract.Presenter {

    @NonNull private final AddGroceryListItemService service;

    @Inject
    public AddGroceryListItemPresenter(@NonNull AddGroceryListItemService service) {
        this.service = service;
    }

    @Override
    public Observable<ViewModel> model(GroceryListId id) {
        return service.model(id).toObservable();
    }
}
