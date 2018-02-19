package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CategoryRepository {
    @NonNull
    Single<CategoryDocument> fetchCategory(@NonNull GroceryListId groceryListId,
                                           @NonNull String name);
    @NonNull
    Flowable<List<CategoryDocument>> fetchAllCategories(@NonNull GroceryListId groceryListId);
    @NonNull
    Single<List<CategoryDocument>> getAllCategories(@NonNull GroceryListId groceryListId);
    @NonNull
    Completable setSortOrder(@NonNull GroceryListId groceryListId,
                             @NonNull List<CategoryId> order);
    @NonNull
    Completable createDefaultCategories(GroceryListId id);
}
