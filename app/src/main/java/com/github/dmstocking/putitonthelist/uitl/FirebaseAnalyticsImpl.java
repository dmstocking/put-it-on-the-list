package com.github.dmstocking.putitonthelist.uitl;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;

@Singleton
public class FirebaseAnalyticsImpl implements Analytics {

    @NonNull private final FirebaseAnalytics firebaseAnalytics;

    @Inject
    public FirebaseAnalyticsImpl(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void createdList() {
        firebaseAnalytics.logEvent("created_list", null);
    }

    @Override
    public void deletedList() {
        firebaseAnalytics.logEvent("deleted_list", null);
    }

    @Override
    public void editedSortOrder() {
        firebaseAnalytics.logEvent("edited_sort_order", null);
    }

    @Override
    public void addedItem() {
        firebaseAnalytics.logEvent("added_item", null);
    }

    @Override
    public void purchasedItem(@NonNull String id) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void unpurchasedItem(@NonNull String id) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void deletedPurchased() {
        firebaseAnalytics.logEvent("deleted_purchased", null);
    }
}
