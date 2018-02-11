package com.github.dmstocking.putitonthelist.main;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.R;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

@AutoFactory
public class MainViewHolderItem extends MainViewHolder {

    @BindView(R.id.background) CardView background;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.items) TextView items;

    @BindDimen(R.dimen.dp_16) float dp16;
    @BindDimen(R.dimen.dp_8) float dp8;

    @Nullable private ListViewModel model;
    private final Resources resources;

    public MainViewHolderItem(@Provided OnGroceryListClicked onGroceryListClicked,
                              @Provided OnGroceryListLongClicked onGroceryListLongClicked,
                              View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        resources = itemView.getResources();
        background.setOnClickListener(view -> {
            onGroceryListClicked.onGroceryListClicked(model);
        });
        background.setOnLongClickListener(view -> {
            onGroceryListLongClicked.onGroceryListLongClicked(model);
            return true;
        });
    }

    @Override
    public void bind(@NonNull ListViewModel model) {
        this.model = model;
        if (model.selected()) {
            background.setCardBackgroundColor(resources.getColor(R.color.colorAccent));
            name.setTextColor(resources.getColor(R.color.md_white_1000));
            items.setTextColor(resources.getColor(R.color.md_white_1000));
        } else {
            background.setCardBackgroundColor(resources.getColor(R.color.md_white_1000));
            name.setTextColor(resources.getColor(R.color.black_1000_87));
            items.setTextColor(resources.getColor(R.color.black_1000_87));
        }
        name.setText(model.headline());
        items.setText(model.trailingCaption());
    }
}
