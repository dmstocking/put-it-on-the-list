package com.github.dmstocking.putitonthelist.grocery_list;

public interface GroceryListContract {

    interface Presenter {
        void attachView(View view);
        void detachView(View view);
    }

    interface View {
        void render(GroceryListViewModel viewModel);
    }
}
