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
    public void createdList(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("created_list", bundle);
    }

    @Override
    public void deletedList(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("deleted_list", bundle);
    }

    @Override
    public void editedSortOrder() {
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent("edited_sort_order", bundle);
    }

    @Override
    public void addedItem(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void deletedItem(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void editedItem(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void purchasedItem(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void unpurchasedItem(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        firebaseAnalytics.logEvent("added_item", bundle);
    }

    @Override
    public void userRegisters() {
        Bundle bundle = new Bundle();
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }
}
