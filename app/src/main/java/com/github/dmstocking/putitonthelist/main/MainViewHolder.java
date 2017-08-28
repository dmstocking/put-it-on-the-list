package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.R;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@AutoFactory
public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.background) View background;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.items) TextView items;

    @Nullable private ListViewModel model;

    public MainViewHolder(@Provided OnGroceryListClicked onGroceryListClicked, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        background.setOnClickListener(view -> {
            onGroceryListClicked.onGroceryListClicked(model);
        });
    }

    public void bind(@NonNull ListViewModel model) {
        this.model = model;
        name.setText(model.name());
        items.setText(model.acquiredItems() + "/" + model.totalItems());
    }
}
