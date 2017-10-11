package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.R;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindView;
import butterknife.ButterKnife;

@AutoFactory
public class SortViewHolder extends RecyclerView.ViewHolder {

    @NonNull private final SortItemTouchHelper sortItemTouchHelper;

    @BindView(R.id.item) View item;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.category) TextView name;

    public SortViewHolder(View itemView,
                          @Provided @NonNull SortItemTouchHelper sortItemTouchHelper) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.sortItemTouchHelper = sortItemTouchHelper;
    }

    public void bind(SortItemViewModel model) {
        name.setText(model.name());
        item.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                sortItemTouchHelper.startDrag(this);
            }
            return false;
        });
    }
}
