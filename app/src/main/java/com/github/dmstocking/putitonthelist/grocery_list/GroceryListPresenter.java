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
    public void attachView(@NonNull GroceryListContract.View view,
                           @NonNull GroceryListArguments groceryListArguments) {
        subscribe = groceryListRepository.getModel(groceryListArguments.groceryListId())
                .subscribe(view::render);
    }

    @Override
    public void detachView(@NonNull GroceryListContract.View view) {
        subscribe.dispose();
    }
}
