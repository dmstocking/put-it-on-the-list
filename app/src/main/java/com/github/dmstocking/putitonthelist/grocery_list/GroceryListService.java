package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;

import com.github.dmstocking.optional.java.util.Optional;
import com.github.dmstocking.optional.java.util.function.Function;
import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.CategoryDocument;
import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.main.GroceryListId;
import com.github.dmstocking.putitonthelist.uitl.IconUtils;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class GroceryListService {

    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final GroceryListRepository groceryListRepository;
    @NonNull private final IconUtils iconUtils;

    @Inject
    public GroceryListService(@NonNull CategoryRepository categoryRepository,
                              @NonNull GroceryListRepository groceryListRepository,
                              @NonNull IconUtils iconUtils) {
        this.categoryRepository = categoryRepository;
        this.groceryListRepository = groceryListRepository;
        this.iconUtils = iconUtils;
    }

    public Flowable<GroceryListViewModel> getModel(@NonNull GroceryListId id) {
        return Flowable.combineLatest(
                getCategories(id),
                groceryListRepository.getGroceryListDocument(id),
                CategoriesDocumentsPair::create)
                .map(pair -> {
                    Function<GroceryListItemDocument, Integer> orderValue = (category) ->
                            Optional.ofNullable(pair.categories().get(category.getCategory()))
                                    .map(CategoryDocument::getOrder)
                                    .orElse(Integer.MAX_VALUE);
                    Collections.sort(pair.groceryListItems(), (o1, o2) -> {
                        int purchasedCmp = Boolean.compare(o1.isPurchased(), o2.isPurchased());
                        if (purchasedCmp == 0) {
                            int o1v = orderValue.apply(o1);
                            int o2v = orderValue.apply(o2);
                            int categoryCmp = Integer.compare(o1v, o2v);
                            if (categoryCmp == 0) {
                                return o1.getName().compareTo(o2.getName());
                            }
                            return categoryCmp;
                        }
                        return purchasedCmp;
                    });

                    List<GroceryListItemDocument> documents = pair.groceryListItems();
                    List<ListViewModel> list = new ArrayList<>();
                    for (GroceryListItemDocument item : documents) {
                        Icon icon = Optional.ofNullable(pair.categories().get(item.getCategory()))
                                .map(CategoryDocument::getIcon)
                                .orElse(Icon.OTHER);
                        list.add(ListViewModel.create(
                                GroceryListItemId.create(item.getId()),
                                iconUtils.iconToUri(icon),
                                item.getName(),
                                item.isPurchased(),
                                false));
                    }
                    return GroceryListViewModel.create(list);
                });
    }

    private Flowable<Map<String, CategoryDocument>> getCategories(GroceryListId id) {
        return categoryRepository.fetchAllCategories(id)
                .map(documents -> {
                    Map<String, CategoryDocument> categories = new HashMap<>();
                    for (CategoryDocument doc : documents) {
                        categories.put(doc.getCategory(), doc);
                    }
                    return categories;
                });
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

        public static CategoriesDocumentsPair create(Map<String, CategoryDocument> categories,
                                                     List<GroceryListItemDocument> groceryListItems) {
            return new AutoValue_GroceryListService_CategoriesDocumentsPair(
                    categories,
                    groceryListItems);
        }

        @NonNull public abstract Map<String, CategoryDocument> categories();
        @NonNull public abstract List<GroceryListItemDocument> groceryListItems();
    }
}
