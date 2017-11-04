package com.github.dmstocking.putitonthelist.main;

import java.util.List;
import java.util.Map;

public class GroceryListDocument {

    private Map<String, Boolean> authIds;
    private String name;
    private List<GroceryListItem> groceryListItems;

    public GroceryListDocument() {
    }

    public GroceryListDocument(Map<String, Boolean> authIds,
                               String name,
                               List<GroceryListItem> groceryListItems) {
        this.authIds = authIds;
        this.name = name;
        this.groceryListItems = groceryListItems;
    }

    public Map<String, Boolean> getAuthIds() {
        return authIds;
    }

    public String getName() {
        return name;
    }

    public List<GroceryListItem> getGroceryListItems() {
        return groceryListItems;
    }
}
