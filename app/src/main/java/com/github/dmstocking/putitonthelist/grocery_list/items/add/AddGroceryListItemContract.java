package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import io.reactivex.Observable;

public interface AddGroceryListItemContract {

    interface View {
    }

    interface Presenter {

        Observable<ViewModel> model(Observable<Object> onDoneClicked);
    }
}
