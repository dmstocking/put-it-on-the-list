package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.grocery_list.GroceryListComponent;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListModule;
import com.github.dmstocking.putitonthelist.main.MainActivity;
import com.github.dmstocking.putitonthelist.main.MainComponent;
import com.github.dmstocking.putitonthelist.main.MainModule;
import com.github.dmstocking.putitonthelist.uitl.UtilModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        UtilModule.class,
})
public interface CoreComponent {

    void inject(MainActivity mainActivity);

    MainComponent mainComponent(MainModule mainModule);

    GroceryListComponent groceryListComponent(GroceryListModule groceryListModule);
}
