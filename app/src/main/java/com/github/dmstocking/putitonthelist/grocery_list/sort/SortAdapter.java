package com.github.dmstocking.putitonthelist.grocery_list.sort;

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
public class SortAdapter extends RecyclerView.Adapter<SortViewHolder> {

    @NonNull private List<SortItemViewModel> model = new ArrayList<>();

    @NonNull private final SortViewHolderFactory viewHolderFactory;

    @Inject
    public SortAdapter(@NonNull SortViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public void update(@NonNull List<SortItemViewModel> newModel) {
        List<SortItemViewModel> oldModel = model;
        model = newModel;
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
    public SortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grocery_list_sort_list_item, parent, false);
        return viewHolderFactory.create(view);
    }

    @Override
    public void onBindViewHolder(SortViewHolder holder, int position) {
        holder.bind(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @NonNull
    public List<SortItemViewModel> getModel() {
        return model;
    }
}
