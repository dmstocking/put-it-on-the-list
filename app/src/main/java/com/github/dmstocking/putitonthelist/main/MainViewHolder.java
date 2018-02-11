package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class MainViewHolder extends RecyclerView.ViewHolder {

    public MainViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(@NonNull ListViewModel model);
}
