package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.view.View;

import com.github.dmstocking.putitonthelist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolderItemAd extends MainViewHolder {

    @BindView(R.id.ad) AdView adView;

    public MainViewHolderItemAd(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void bind(@NonNull ListViewModel model) {
    }
}
