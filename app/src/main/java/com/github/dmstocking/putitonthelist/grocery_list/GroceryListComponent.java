package com.github.dmstocking.putitonthelist.grocery_list;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Subcomponent;

@PerController
@Subcomponent(modules = {
        GroceryListModule.class,
})
public interface GroceryListComponent {
    void inject(GroceryListController controller);
}
