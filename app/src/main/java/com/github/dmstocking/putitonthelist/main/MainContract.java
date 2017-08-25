package com.github.dmstocking.putitonthelist.main;

public interface MainContract {

    interface Presenter {
        void attachView(View view);
        void detachView(View view);
    }

    interface View {
        void render(MainViewModel viewModel);
    }
}
