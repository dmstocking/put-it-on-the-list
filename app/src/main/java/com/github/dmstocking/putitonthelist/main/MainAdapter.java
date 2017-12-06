package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.dmstocking.putitonthelist.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    @NonNull private final MainViewHolderItemFactory mainViewHolderItemFactory;

    @NonNull private List<ListViewModel> model = new ArrayList<>();

    @Inject
    public MainAdapter(@NonNull MainViewHolderItemFactory mainViewHolderItemFactory) {
        this.mainViewHolderItemFactory = mainViewHolderItemFactory;
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
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (ListViewModel.Type.values()[viewType]) {
            default:
            case ITEM: {
                View view = inflater.inflate(R.layout.main_list_item, parent, false);
                return mainViewHolderItemFactory.create(view);
            }
            case AD_BANNER: {
                View view = inflater.inflate(R.layout.main_list_ad_item, parent, false);
                return new MainViewHolderItemAd(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ListViewModel item = model.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemViewType(int position) {
        return model.get(position)
                .type()
                .ordinal();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

}
