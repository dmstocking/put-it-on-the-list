package com.github.dmstocking.putitonthelist.grocery_list;

public interface GroceryListContract {

    interface Presenter {
        void attachView(View view, GroceryListArguments args);
        void detachView(View view);

        void onGroceryListItemClicked(ListViewModel item);

        void onDeletePurchased();
    }

    interface View {
        void render(GroceryListViewModel viewModel);
    }
}
