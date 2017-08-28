package com.github.dmstocking.putitonthelist.grocery_list;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

@Singleton
public class GroceryListPresenter implements GroceryListContract.Presenter {

    @NonNull private final GroceryListRepository groceryListRepository;

    @NonNull private Disposable subscribe = Disposables.disposed();

    @Inject
    public GroceryListPresenter(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    @Override
    public void attachView(GroceryListContract.View view) {
        subscribe = groceryListRepository.getModel()
                .subscribe(view::render);
    }

    @Override
    public void detachView(GroceryListContract.View view) {
        subscribe.dispose();
    }
}
