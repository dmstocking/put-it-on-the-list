package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

@Singleton
public class GroceryListService {

    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final GroceryListRepository groceryListRepository;
    @NonNull private final GroceryListResources resources;

    @Inject
    public GroceryListService(@NonNull CategoryRepository categoryRepository,
                              @NonNull GroceryListRepository groceryListRepository,
                              @NonNull GroceryListResources resources) {
        this.categoryRepository = categoryRepository;
        this.groceryListRepository = groceryListRepository;
        this.resources = resources;
    }

    public Flowable<GroceryListViewModel> getModel(@NonNull GroceryListId id) {
        return Flowable.combineLatest(
                categoryRepository.getCategories(),
                groceryListRepository.getGroceryListDocument(id),
                CategoriesDocumentsPair::create)
                .map(pair -> {
                    List<String> categories = new ArrayList<>();
                    for (CategoryDocument doc : pair.categories()) {
                        categories.add(doc.getCategory());
                    }
                    Collections.sort(pair.groceryListItems(),
                                     (o1, o2) -> {
                                         int purchasedCmp = Boolean.compare(o1.isPurchased(), o2.isPurchased());
                                         if (purchasedCmp == 0) {
                                             int o1v = categories.indexOf(o1.getCategory());
                                             int o2v = categories.indexOf(o2.getCategory());
                                             return Integer.compare(o1v, o2v);
                                         }
                                         return purchasedCmp;
                                     });
                    return pair;
                })
                .map(pair -> {
                    List<GroceryListItemDocument> documents = pair.groceryListItems();
                    List<ListViewModel> list = new ArrayList<>();
                    for (GroceryListItemDocument item : documents) {
                        list.add(ListViewModel.create(
                                GroceryListItemId.create(item.getId()),
                                toUri(item.getCategory()),
                                item.getName(),
                                item.isPurchased(),
                                false));
                    }
                    return GroceryListViewModel.create(list);
                });
    }

    @NonNull
    private URI toUri(@Nullable String category) {
        if (category == null) {
            return resources.unknown();
        }

        switch (category) {
            case "fruit": return resources.apple();
            case "bakery": return resources.croissant();
            default: return resources.unknown();
        }
    }

    public Completable unpurchase(GroceryListId groceryListId,
                                  GroceryListItemId id) {
        return setPurchased(groceryListId, id, false);
    }

    public Completable purchase(GroceryListId groceryListId,
                                GroceryListItemId id) {
        return setPurchased(groceryListId, id, true);
    }

    public Completable setPurchased(GroceryListId listId, GroceryListItemId itemId, boolean purchased) {
        return groceryListRepository.update(listId, itemId, purchased);
    }

    @AutoValue
    abstract static class CategoriesDocumentsPair {

        public static CategoriesDocumentsPair create(List<CategoryDocument> categories,
                                                     List<GroceryListItemDocument> groceryListItems) {
            return new AutoValue_GroceryListService_CategoriesDocumentsPair(
                    categories,
                    groceryListItems);
        }

        @NonNull public abstract List<CategoryDocument> categories();
        @NonNull public abstract List<GroceryListItemDocument> groceryListItems();
    }
}
