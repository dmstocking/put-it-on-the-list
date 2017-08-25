package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.dmstocking.putitonthelist.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;

@Singleton
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    @NonNull private List<GroceryListViewModel> model = new ArrayList<>();

    @Inject
    public MainAdapter() {
    }

    public void updateModel(List<GroceryListViewModel> newModel) {
        List<GroceryListViewModel> oldModel = model;
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
                return oldModel.get(oldItemPosition).id() == newModel.get(newItemPosition).id();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldModel.get(oldItemPosition) == newModel.get(newItemPosition);
            }
        }).dispatchUpdatesTo(this);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MainViewHolder(inflater.inflate(R.layout.main_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        GroceryListViewModel item = model.get(position);
        holder.name.setText(item.name());
        holder.items.setText(item.acquiredItems() + "/" + item.totalItems());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.items) TextView items;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
