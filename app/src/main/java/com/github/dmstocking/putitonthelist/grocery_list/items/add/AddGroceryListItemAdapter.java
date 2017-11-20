package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmstocking.putitonthelist.PerController;
import com.github.dmstocking.putitonthelist.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerController
public class AddGroceryListItemAdapter extends RecyclerView.Adapter<AddGroceryViewHolder> {

    @NonNull private final AddGroceryViewHolderFactory viewHolderFactory;

    @NonNull private List<ListItemViewModel> model = new ArrayList<>();

    @Inject
    public AddGroceryListItemAdapter(@NonNull AddGroceryViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public void update(List<ListItemViewModel> model) {
        List<ListItemViewModel> newModel = model;
        List<ListItemViewModel> oldModel = this.model;
        this.model = newModel;
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldModel.size();
            }

            @Override
            public int getNewListSize() {
                return newModel.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldModel.get(oldItemPosition).id().equals(newModel.get(newItemPosition).id());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldModel.get(oldItemPosition).equals(newModel.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }

    @Override
    public AddGroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grocery_list__item__add__category_item, parent, false);
        return viewHolderFactory.create(view);
    }

    @Override
    public void onBindViewHolder(AddGroceryViewHolder holder, int position) {
        holder.bind(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
