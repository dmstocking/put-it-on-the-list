package com.github.dmstocking.putitonthelist.main;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface MainRepository {
    Single<GroceryListId> create(String authId, String name);
    Flowable<List<GroceryListDocument>> getModel(String authId);
    Completable delete(String id);
}
