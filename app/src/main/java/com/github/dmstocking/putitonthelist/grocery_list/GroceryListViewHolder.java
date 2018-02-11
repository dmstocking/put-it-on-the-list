package com.github.dmstocking.putitonthelist.grocery_list;

import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.dmstocking.putitonthelist.ColorResources;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.uitl.ImageLoadingService;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

@AutoFactory
public class GroceryListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.background) CardView background;
    @BindView(R.id.category) ImageView category;
    @BindView(R.id.category_background) ImageView categoryBackground;
    @BindView(R.id.name) CheckedTextView name;

    @BindDimen(R.dimen.dp_0) int dp0;
    @BindDimen(R.dimen.dp_8) int dp8;

    @NonNull private final ColorResources colorResources;
    @NonNull private final ImageLoadingService imageLoadingService;

    private ListViewModel model;

    public GroceryListViewHolder(@Provided ColorResources colorResources,
                                 @Provided ImageLoadingService imageLoadingService,
                                 @Provided OnGroceryListItemClicked onGroceryListItemClicked,
                                 View itemView) {
        super(itemView);
        this.colorResources = colorResources;
        this.imageLoadingService = imageLoadingService;
        ButterKnife.bind(this, itemView);
        background.setOnClickListener(view -> {
            onGroceryListItemClicked.onGroceryListItemClicked(itemView, model);
        });
    }

    public void bind(ListViewModel model) {
        this.model = model;
        ViewCompat.setElevation(background, model.purchased() ? dp0 : dp8);
        imageLoadingService.load(model.icon())
                .into(category);
        category.setAlpha(model.purchased() ? 0.54f : 0.87f);
        categoryBackground.setColorFilter(colorResources.color(model.iconBackground()),
                                          PorterDuff.Mode.SRC_IN);
        name.setText(model.name());
        name.setChecked(model.purchased());
    }
}
