package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class SortService {

    @NonNull private final CategoryRepository categoryRepository;

    @Inject
    public SortService(@NonNull CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Observable<List<SortItemViewModel>> fetchModel() {
        return categoryRepository.onCategoriesChanged()
                .mergeWith(Flowable.just(Uri.EMPTY))
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .toObservable()
                .flatMap(ignored -> categoryRepository.fetchAllCategories().toObservable())
                .map(categories -> {
                    Collections.sort(categories,
                                     (o1, o2) -> Integer.compare(o1.order(), o2.order()));
                    return categories;
                })
                .map(categories -> {
                    List<SortItemViewModel> model = new ArrayList<>();
                    for (CategoryEntity category : categories) {
                        model.add(SortItemViewModel.create(
                                category.id(),
                                URI.create("nothing://nope.com"),
                                category.name()));
                    }
                    return model;
                });
    }

    public void reorder(List<Integer> ids) {
        categoryRepository.setSortOrder(ids)
                .blockingAwait();
    }
}
