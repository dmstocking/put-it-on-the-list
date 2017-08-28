package com.github.dmstocking.putitonthelist.grocery_list;

import dagger.Subcomponent;

@Subcomponent(modules = {
        GroceryListModule.class,
})
public interface GroceryListComponent {
    void inject(GroceryListController controller);
}
