package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.github.dmstocking.putitonthelist.PerController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;
import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

@PerController
public class SortItemTouchCallback extends ItemTouchHelper.Callback {

    @NonNull private final SortAdapter sortAdapter;

    @Inject
    public SortItemTouchCallback(@NonNull SortAdapter sortAdapter) {
        this.sortAdapter = sortAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeFlag(ACTION_STATE_IDLE, UP | DOWN)
                | makeFlag(ACTION_STATE_DRAG, UP | DOWN);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        List<SortItemViewModel> items = new ArrayList<>(sortAdapter.getModel());

        int prevPosition = viewHolder.getAdapterPosition();
        int newPosition = target.getAdapterPosition();
        Log.d("SortItemTouchCallback", "Moving " + prevPosition + " to " + newPosition);

        SortItemViewModel item = items.remove(prevPosition);
        items.add(newPosition, item);
        sortAdapter.update(items);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
