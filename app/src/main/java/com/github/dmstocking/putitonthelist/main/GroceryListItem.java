package com.github.dmstocking.putitonthelist.main;

class GroceryListItem {

    private String category;
    private String name;
    private boolean purchased;

    public GroceryListItem() {
    }

    public GroceryListItem(String category, String name, boolean purchased) {
        this.category = category;
        this.name = name;
        this.purchased = purchased;
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
