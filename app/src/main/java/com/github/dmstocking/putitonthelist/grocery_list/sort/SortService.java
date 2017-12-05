package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.Analytics;
import com.github.dmstocking.putitonthelist.uitl.IconUtils;
import com.github.dmstocking.putitonthelist.uitl.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class SortService {

    public static final String TAG = "SortService";

    @NonNull private final Analytics analytics;
    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final IconUtils iconUtils;
    @NonNull private final Log log;

    @Inject
    public SortService(@NonNull Analytics analytics,
                       @NonNull CategoryRepository categoryRepository,
                       @NonNull IconUtils iconUtils,
                       @NonNull Log log) {
        this.analytics = analytics;
        this.categoryRepository = categoryRepository;
        this.iconUtils = iconUtils;
        this.log = log;
    }

    public Single<List<SortItemViewModel>> fetchModel(@NonNull GroceryListId groceryListId) {
        return categoryRepository.getAllCategories(groceryListId)
                .map(categories -> {
                    Collections.sort(categories,
                                     (o1, o2) -> Integer.compare(o1.getOrder(), o2.getOrder()));
                    return categories;
                })
                .map(categories -> {
                    List<SortItemViewModel> model = new ArrayList<>();
                    for (CategoryDocument category : categories) {
                        model.add(SortItemViewModel.create(
                                CategoryId.create(category.getId()),
                                iconUtils.iconToUri(category.getIcon()),
                                category.getCategory()));
                    }
                    return model;
                });
    }

    public void reorder(@NonNull GroceryListId groceryListId, @NonNull List<CategoryId> ids) {
        categoryRepository.setSortOrder(groceryListId, ids)
                .timeout(10, TimeUnit.SECONDS)
                .subscribe(() -> {
                    analytics.editedSortOrder();
                }, throwable -> {
                    log.e(TAG, "Error while updating sort order", throwable);
                });
    }
}
