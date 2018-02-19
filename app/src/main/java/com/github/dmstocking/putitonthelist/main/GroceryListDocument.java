package com.github.dmstocking.putitonthelist.main;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class GroceryListDocument {

    @Exclude
    private String id;

    private Map<String, Boolean> authIds;
    private Map<String, Object> categories;
    private String name;
    private int purchased;
    private int total;

    public GroceryListDocument() {
    }

    public GroceryListDocument(Map<String, Boolean> authIds,
                               Map<String, Object> categories,
                               String name,
                               int purchased,
                               int total) {
        this.authIds = authIds;
        this.categories = categories;
        this.name = name;
        this.purchased = purchased;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Boolean> getAuthIds() {
        return authIds;
    }

    public Map<String, Object> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public int getPurchased() {
        return purchased;
    }

    public int getTotal() {
        return total;
    }
}
