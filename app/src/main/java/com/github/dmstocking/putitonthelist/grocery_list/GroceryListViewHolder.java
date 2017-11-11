package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;

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
    @BindView(R.id.name) CheckedTextView name;
    @BindView(R.id.edit_name) EditText editName;

    @BindDimen(R.dimen.dp_0) int dp0;
    @BindDimen(R.dimen.dp_8) int dp8;

    @NonNull private final ImageLoadingService imageLoadingService;

    private ListViewModel model;

    public GroceryListViewHolder(@Provided ImageLoadingService imageLoadingService,
                                 @Provided OnGroceryListItemClicked onGroceryListItemClicked,
                                 View itemView) {
        super(itemView);
        this.imageLoadingService = imageLoadingService;
        ButterKnife.bind(this, itemView);
        background.setOnClickListener(view -> {
            onGroceryListItemClicked.onGroceryListItemClicked(model);
        });
    }

    public void bind(ListViewModel model) {
        this.model = model;
        ViewCompat.setElevation(background, model.purchased() ? dp0 : dp8);
        imageLoadingService.load(model.icon())
                .into(category);
        category.setAlpha(model.purchased() ? 0.54f : 0.87f);
        name.setText(model.name());
        name.setChecked(model.purchased());
        editName.setText(model.name());
    }
}
