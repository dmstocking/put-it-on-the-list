package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.processors.PublishProcessor;

@Singleton
public class CategoryRepository {

    private List<CategoryEntity> categories = new ArrayList<CategoryEntity>() {{
        add(CategoryEntity.create(1, "Deli", 1));
        add(CategoryEntity.create(2, "Chips", 2));
        add(CategoryEntity.create(3, "Frozen", 3));
        add(CategoryEntity.create(4, "Produce", 4));
        add(CategoryEntity.create(5, "Ethnic", 5));
    }};

    @NonNull private final PublishProcessor<Uri> subject = PublishProcessor.create();

    @Inject
    public CategoryRepository() {
    }

    @NonNull
    public Flowable<Uri> onCategoriesChanged() {
        return subject;
    }

    @NonNull
    public Single<List<CategoryEntity>> fetchIdEq(@NonNull List<Integer> ids) {
        return Single.defer(() -> Single.just(categories));
    }

    @NonNull
    public Completable setSortOrder(@NonNull List<Integer> order) {
        return Completable.defer(() -> {
            Observable.fromIterable(order)
                    .map(String::valueOf)
                    .reduce((c,n) -> c + "," + n)
                    .subscribe(s -> Log.d("CategoryRepository", "Items: " + s));
            List<CategoryEntity> newCategories = new ArrayList<>();
            int orderInt = 0;
            for (Integer id: order) {
                for (CategoryEntity category : categories) {
                    if (category.id() == id) {
                        CategoryEntity cat = category.toBuilder()
                                .setOrder(orderInt++)
                                .build();
                        newCategories.add(cat);
                        break;
                    }
                }
            }
            Collections.sort(newCategories, (o1, o2) -> Integer.compare(o1.order(), o2.order()));
            categories = newCategories;
            subject.onNext(Uri.EMPTY);
            return Completable.complete();
        });
    }

    @NonNull
    public Single<List<CategoryEntity>> fetchAllCategories() {
        return Single.just(categories);
    }
}
