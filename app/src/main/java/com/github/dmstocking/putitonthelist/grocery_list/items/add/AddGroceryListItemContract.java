package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.main.GroceryListId;

import io.reactivex.Observable;

public interface AddGroceryListItemContract {

    interface View {
        void finish();
    }

    interface Presenter {
        void onDoneClicked();
        void onNameChanged(@NonNull String name);
        void onCategoryChanged(@NonNull CategoryDocument category);
        Observable<ViewModel> model();
    }
}
