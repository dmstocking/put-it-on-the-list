package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.authentication.UserService;
import com.github.dmstocking.putitonthelist.comeback.ComeBackScheduler;
import com.github.dmstocking.putitonthelist.comeback.android.ComeBackNotification;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListComponent;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListModule;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.AddGroceryListItemComponent;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.AddGroceryListItemModule;
import com.github.dmstocking.putitonthelist.grocery_list.sort.SortComponent;
import com.github.dmstocking.putitonthelist.grocery_list.sort.SortModule;
import com.github.dmstocking.putitonthelist.main.MainActivity;
import com.github.dmstocking.putitonthelist.main.MainComponent;
import com.github.dmstocking.putitonthelist.main.MainModule;
import com.github.dmstocking.putitonthelist.main.widget.WidgetModule;
import com.github.dmstocking.putitonthelist.notification.NotificationInitializer;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.github.dmstocking.putitonthelist.uitl.UtilModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        UtilModule.class,
})
public interface CoreComponent {

    void inject(MainActivity mainActivity);

    ComeBackNotification comeBackNotification();
    ComeBackScheduler comeBackScheduler();
    Log log();
    NotificationInitializer notificationInitializer();
    UserService userService();

    MainComponent mainComponent(MainModule mainModule);
    GroceryListComponent groceryListComponent(GroceryListModule groceryListModule);
    AddGroceryListItemComponent addGroceryListItemComponent(AddGroceryListItemModule groceryListItemModule);
    SortComponent sortComponent(SortModule sortModule);

    WidgetComponent widgetComponent(WidgetModule widgetModule);
}
