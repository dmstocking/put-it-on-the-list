package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.ColorResources;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.uitl.ImageLoadingService;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindView;
import butterknife.ButterKnife;

@AutoFactory
public class AddGroceryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.background) CardView background;
    @BindView(R.id.category) ImageView category;
    @BindView(R.id.name) TextView name;

    @NonNull private final ImageLoadingService imageLoadingService;
    @NonNull private final ColorResources colorResources;
    @NonNull private final AddGroceryListItemOnClick addGroceryListItemOnClick;

    public AddGroceryViewHolder(@Provided @NonNull ColorResources colorResources,
                                @Provided @NonNull ImageLoadingService imageLoadingService,
                                @Provided @NonNull AddGroceryListItemOnClick addGroceryListItemOnClick,
                                View itemView) {
        super(itemView);
        this.imageLoadingService = imageLoadingService;
        this.colorResources = colorResources;
        this.addGroceryListItemOnClick = addGroceryListItemOnClick;
        ButterKnife.bind(this, itemView);
    }

    public void bind(ListItemViewModel model) {
        background.setOnClickListener((view) -> {
            addGroceryListItemOnClick.onCategoryClicked(model.category());
        });
        name.setText(model.name());
        imageLoadingService.load(model.image())
                .into(category);
        name.setTextColor(colorResources.color(model.color()));
        category.setColorFilter(colorResources.color(model.color()), PorterDuff.Mode.SRC_IN);
    }
}
