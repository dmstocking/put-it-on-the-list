package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmstocking.putitonthelist.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListViewHolder> {

    @NonNull private final GroceryListViewHolderFactory groceryListViewHolderFactory;

    @NonNull private List<ListViewModel> model = new ArrayList<>();

    @Inject
    public GroceryListAdapter(GroceryListViewHolderFactory groceryListViewHolderFactory) {
        this.groceryListViewHolderFactory = groceryListViewHolderFactory;
    }

    public void updateModel(List<ListViewModel> newModel) {
        List<ListViewModel> oldModel = model;
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
    public GroceryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View root = layoutInflater.inflate(R.layout.grocery_list_list_item, parent, false);
        return groceryListViewHolderFactory.create(root);
    }

    @Override
    public void onBindViewHolder(GroceryListViewHolder holder, int position) {
        holder.bind(this.model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

}
