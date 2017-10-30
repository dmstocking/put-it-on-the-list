package com.github.dmstocking.putitonthelist.uitl;

import io.reactivex.annotations.NonNull;

interface Analytics {

    void createdList(@NonNull String name);
    void deletedList(@NonNull String name);

    void editedSortOrder();

    void addedItem(@NonNull String name);
    void deletedItem(@NonNull String name);
    void editedItem(@NonNull String name);
    void purchasedItem(@NonNull String name);
    void unpurchasedItem(@NonNull String name);

    void userRegisters();
}
