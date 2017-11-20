package com.github.dmstocking.putitonthelist.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridMarginDecoration extends RecyclerView.ItemDecoration {

    private int mInsets;

    public GridMarginDecoration(Context context, @DimenRes int size) {
        mInsets = context.getResources().getDimensionPixelSize(size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}
