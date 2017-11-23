package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;
import com.google.firebase.firestore.Exclude;

public class CategoryDocument {

    @Exclude
    private String id;

    private String category;
    private Color color;
    private Icon icon;
    private int order;

    public CategoryDocument() {
    }

    public CategoryDocument(String category) {
        this.category = category;
    }

    public CategoryDocument(String id,
                            String category,
                            Color color,
                            Icon icon,
                            int order) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.icon = icon;
        this.order = order;
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

    public Color getColor() {
        return color;
    }

    public Icon getIcon() {
        return icon;
    }

    public int getOrder() {
        return order;
    }
}
