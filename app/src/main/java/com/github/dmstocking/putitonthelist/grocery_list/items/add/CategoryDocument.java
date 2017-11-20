package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;

public class CategoryDocument {

    private String id;
    private String category;
    private Color color;
    private Icon icon;

    public CategoryDocument() {
    }

    public CategoryDocument(String category) {
        this.category = category;
    }

    public CategoryDocument(String id,
                            String category,
                            Color color, Icon icon) {
        this.id = id;
        this.category = category;
        this.color = color;
        this.icon = icon;
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
}
