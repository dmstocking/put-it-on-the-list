package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.R;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

@AutoFactory
public class GroceryListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.background) CardView background;
    @BindView(R.id.category) ImageView category;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.edit_name) EditText editName;

    private ListViewModel model;

    public GroceryListViewHolder(@Provided OnGroceryListItemClicked onGroceryListItemClicked,
                                 View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        background.setOnClickListener(view -> {
            onGroceryListItemClicked.onGroceryListItemClicked(model);
        });
    }

    public void bind(ListViewModel model) {
        this.model = model;
        name.setText(model.name());
        editName.setText(model.name());
    }
}
