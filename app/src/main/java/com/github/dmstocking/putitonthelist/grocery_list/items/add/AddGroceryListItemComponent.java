package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Subcomponent;

@PerController
@Subcomponent(modules = {
        AddGroceryListItemModule.class,
})
public interface AddGroceryListItemComponent {

    void inject(AddGroceryListItemController controller);
}
