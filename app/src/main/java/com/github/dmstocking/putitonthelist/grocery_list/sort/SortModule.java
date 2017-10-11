package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.v7.widget.helper.ItemTouchHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class SortModule {

    @Provides
    public ItemTouchHelper.Callback providesItemTouchHelperCallback(SortItemTouchCallback callback) {
        return callback;
    }
}
