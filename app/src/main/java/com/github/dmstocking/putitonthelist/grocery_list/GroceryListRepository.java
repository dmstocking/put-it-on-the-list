package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface GroceryListRepository {
    Flowable<List<GroceryListItemDocument>> getGroceryListDocument(@NonNull GroceryListId id);
    Completable create(GroceryListId listId, String category, String name);
    Completable update(GroceryListId listId, GroceryListItemId itemId, boolean purchased);
    Completable deletePurchased(GroceryListId listId);
}
