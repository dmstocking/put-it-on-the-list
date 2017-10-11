package com.github.dmstocking.putitonthelist.grocery_list.sort;

import com.github.dmstocking.putitonthelist.PerController;

import dagger.Subcomponent;

@PerController
@Subcomponent(modules = {
        SortModule.class,
})
public interface SortComponent {
    void inject(SortController sortController);
}
