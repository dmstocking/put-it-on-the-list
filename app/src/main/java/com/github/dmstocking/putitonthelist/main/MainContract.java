package com.github.dmstocking.putitonthelist.main;

public interface MainContract {

    interface Presenter {
        void attachView(View view);
        void detachView(View view);

        void onClick(ListViewModel model);
        void onLongClick(ListViewModel model);

        void onDelete();

        void onCancelSelection();
    }

    interface View {
        void render(MainViewModel viewModel);

        void launchGroceryList(GroceryListId id);
    }
}
