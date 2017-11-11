package com.github.dmstocking.putitonthelist.grocery_list;

import com.google.firebase.firestore.Exclude;

public class GroceryListItemDocument {

    @Exclude
    private String id;

    private String category;
    private String name;
    private boolean purchased;

    public GroceryListItemDocument() {
    }

    public GroceryListItemDocument(String category, String name, boolean purchased) {
        this.category = category;
        this.name = name;
        this.purchased = purchased;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public boolean isPurchased() {
        return purchased;
    }
}
