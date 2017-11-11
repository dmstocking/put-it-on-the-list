package com.github.dmstocking.putitonthelist.grocery_list;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class GroceryListPresenter implements GroceryListContract.Presenter {

    @NonNull private final GroceryListService groceryListService;

    @NonNull private Disposable subscribe = Disposables.disposed();

    @Inject
    public GroceryListPresenter(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @Override
    public void attachView(@NonNull GroceryListContract.View view,
                           @NonNull GroceryListArguments groceryListArguments) {
        subscribe = groceryListService.getModel(groceryListArguments.groceryListId())
                .subscribe(view::render);
    }

    @Override
    public void detachView(@NonNull GroceryListContract.View view) {
        subscribe.dispose();
    }
}
