package com.github.dmstocking.putitonthelist.grocery_list;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class GroceryListPresenter implements GroceryListContract.Presenter {

    @NonNull private final GroceryListService groceryListService;

    @NonNull private Disposable subscribe = Disposables.disposed();
    @Nullable private GroceryListArguments groceryListArguments;

    @Inject
    public GroceryListPresenter(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @Override
    public void attachView(@NonNull GroceryListContract.View view,
                           @NonNull GroceryListArguments groceryListArguments) {
        this.groceryListArguments = groceryListArguments;
        subscribe = groceryListService.getModel(this.groceryListArguments.groceryListId())
                .subscribe(view::render);
    }

    @Override
    public void onGroceryListItemClicked(ListViewModel item) {
        if (groceryListArguments != null) {
            Completable completable;
            if (item.purchased()) {
                completable = groceryListService.unpurchase(groceryListArguments.groceryListId(),
                                                            item.id());
            } else {
                completable = groceryListService.purchase(groceryListArguments.groceryListId(),
                                                          item.id());
            }
            completable.subscribe();
        }
    }

    @Override
    public void detachView(@NonNull GroceryListContract.View view) {
        subscribe.dispose();
    }
}
