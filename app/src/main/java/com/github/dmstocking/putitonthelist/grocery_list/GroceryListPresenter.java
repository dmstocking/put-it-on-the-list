package com.github.dmstocking.putitonthelist.grocery_list;

import com.github.dmstocking.putitonthelist.uitl.Analytics;
import com.github.dmstocking.putitonthelist.uitl.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class GroceryListPresenter implements GroceryListContract.Presenter {

    public static final String TAG = "GroceryListPresenter";

    @NonNull private final Analytics analytics;
    @NonNull private final GroceryListRepository groceryListRepository;
    @NonNull private final GroceryListService groceryListService;
    @NonNull private final Log log;

    @NonNull private Disposable subscribe = Disposables.disposed();
    @Nullable private GroceryListArguments groceryListArguments;

    @Inject
    public GroceryListPresenter(Analytics analytics,
                                GroceryListRepository groceryListRepository,
                                GroceryListService groceryListService,
                                Log log) {
        this.analytics = analytics;
        this.groceryListRepository = groceryListRepository;
        this.groceryListService = groceryListService;
        this.log = log;
    }

    @Override
    public void attachView(@NonNull GroceryListContract.View view,
                           @NonNull GroceryListArguments groceryListArguments) {
        this.groceryListArguments = groceryListArguments;
        subscribe = groceryListService.getModel(this.groceryListArguments.groceryListId())
                .subscribe(view::render, throwable -> {
                    log.e(TAG, "Error while getting model.", throwable);
                });
    }

    @Override
    public void onGroceryListItemClicked(ListViewModel item) {
        if (groceryListArguments != null) {
            Completable completable;
            if (item.purchased()) {
                completable = groceryListService.unpurchase(groceryListArguments.groceryListId(),
                                                            item.id())
                        .doOnComplete(() -> analytics.unpurchasedItem(item.id().id()));
            } else {
                completable = groceryListService.purchase(groceryListArguments.groceryListId(),
                                                          item.id())
                        .doOnComplete(() -> analytics.purchasedItem(item.id().id()));
            }
            completable.subscribe();
        }
    }

    @Override
    public void onDeletePurchased() {
        if (groceryListArguments != null) {
            groceryListRepository.deletePurchased(groceryListArguments.groceryListId())
                    .subscribeOn(Schedulers.io())
                    .subscribe(() -> {
                        analytics.deletedPurchased();
                    }, throwable -> {
                        log.e(TAG, "Error while deleting purchased items.", throwable);
                    });
        }
    }

    @Override
    public void detachView(@NonNull GroceryListContract.View view) {
        subscribe.dispose();
    }
}
