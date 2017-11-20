package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import com.github.dmstocking.putitonthelist.Color;
import com.github.dmstocking.putitonthelist.Icon;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class CategoryRepository {

    @Inject
    public CategoryRepository() {
    }

    public Flowable<List<CategoryDocument>> getCategories() {
        return Flowable.just(
                new ArrayList<CategoryDocument>() {

                    private int id = 0;

                    private void addCategory(String category, Color color, Icon icon) {
                        add(new CategoryDocument(String.valueOf(id++), category, color, icon));
                    }

                    {
                        addCategory("unknown", Color.BLUE, Icon.FREEZER);
                        addCategory("deli", Color.PINK, Icon.DELI);
                        addCategory("bakery", Color.BROWN, Icon.BAKERY);
                        addCategory("produce", Color.GREEN, Icon.PRODUCE);
                        addCategory("beverages", Color.LIGHT_BLUE, Icon.BEVERAGES);
                        addCategory("dairy", Color.LIGHT_BLUE, Icon.DAIRY);
                        addCategory("refrigerated", Color.BROWN, Icon.REFRIGERATED);
                        addCategory("meat", Color.RED, Icon.MEAT);
                        addCategory("snacks", Color.YELLOW, Icon.SNACKS);
                        addCategory("canned", Color.LIME, Icon.CANNED);
                        addCategory("condiments", Color.ORANGE, Icon.CONDIMENTS);
                        addCategory("baking", Color.DEEP_ORANGE, Icon.BAKING);
                        addCategory("international", Color.AMBER, Icon.INTERNATIONAL);
                        addCategory("pets", Color.LIGHT_GREEN, Icon.PETS);
                        addCategory("baby", Color.PINK, Icon.BABY);
                        addCategory("personal care", Color.LIGHT_GREEN, Icon.PERSONAL_CARE);
                        addCategory("medicine", Color.TEAL, Icon.MEDICINE);
                        addCategory("cleaning", Color.CYAN, Icon.CLEANING);
                        addCategory("unknown", Color.GREY, Icon.UNKNOWN);
                        addCategory("other", Color.GREY, Icon.OTHER);
                    }
                }
        );
    }
}
