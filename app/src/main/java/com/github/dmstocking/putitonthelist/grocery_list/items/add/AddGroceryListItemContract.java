package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.main.GroceryListId;

import io.reactivex.Observable;

public interface AddGroceryListItemContract {

    interface View {
    }

    interface Presenter {

        Observable<ViewModel> model(GroceryListId id);
    }
}
