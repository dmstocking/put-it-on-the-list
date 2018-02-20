package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface AddGroceryListItemContract {

    interface View {
        void finish();
    }

    interface Presenter {
        void onDoneClicked();
        void onNameChanged(@NonNull String name);
        void onCategoryChanged(@NonNull String category);
        Observable<ViewModel> model();
        SavedState onSaveState();
        void restoreState(SavedState savedState);
    }
}
