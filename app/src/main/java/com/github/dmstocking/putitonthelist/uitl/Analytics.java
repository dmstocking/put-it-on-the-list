package com.github.dmstocking.putitonthelist.uitl;

import io.reactivex.annotations.NonNull;

public interface Analytics {

    void createdList();
    void deletedList();

    void editedSortOrder();

    void addedItem();
    void purchasedItem(@NonNull String id);
    void unpurchasedItem(@NonNull String id);
    void deletedPurchased();

    void userRegisters();
}
