package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.Icon;
import com.github.dmstocking.putitonthelist.IconResources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class AddGroceryListItemService {

    @NonNull private final CategoryRepository categoryRepository;
    @NonNull private final IconResources iconResources;

    @Inject
    public AddGroceryListItemService(@NonNull CategoryRepository categoryRepository,
                                     @NonNull IconResources iconResources) {
        this.categoryRepository = categoryRepository;
        this.iconResources = iconResources;
    }

    public Flowable<ViewModel> model() {
        return categoryRepository.getCategories()
                .map(documents -> {
                    List<ListItemViewModel> listViewModels = new ArrayList<>();
                    for (CategoryDocument doc : documents) {
                        listViewModels.add(ListItemViewModel.create(
                                doc.getId(),
                                iconToUri(doc.getIcon()),
                                doc.getCategory(),
                                doc.getColor()));
                    }
                    return ViewModel.create(URI.create("no://no.no"), listViewModels);
                });
    }

    private URI iconToUri(@NonNull Icon icon) {
       switch (icon) {
           case FREEZER:
           case DELI:
           case BAKERY:
           case PRODUCE:
           case BEVERAGES:
           case DAIRY:
           case REFRIGERATED:
           case MEAT:
           case SNACKS:
           case CANNED:
           case CONDIMENTS:
           case BAKING:
           case INTERNATIONAL:
           case PETS:
           case BABY:
           case PERSONAL_CARE:
           case MEDICINE:
           case CLEANING:
           case UNKNOWN:
           case OTHER:
           default: return iconResources.unknown();
       }
    }
}
