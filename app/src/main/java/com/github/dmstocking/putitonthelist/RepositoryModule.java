package com.github.dmstocking.putitonthelist;

import com.github.dmstocking.putitonthelist.grocery_list.GroceryListRepository;
import com.github.dmstocking.putitonthelist.grocery_list.firebase_database.GroceryListRepositoryFirebaseDatabase;
import com.github.dmstocking.putitonthelist.grocery_list.sort.CategoryRepository;
import com.github.dmstocking.putitonthelist.grocery_list.sort.firebase_database.CategoryRepositoryFirebaseDatabase;
import com.github.dmstocking.putitonthelist.main.MainRepository;
import com.github.dmstocking.putitonthelist.main.firebase_database.MainRepositoryFirebaseDatabase;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract MainRepository providesMainRepository(MainRepositoryFirebaseDatabase repo);

    @Binds
    @Singleton
    public abstract GroceryListRepository providesGroceryListRepository(
            GroceryListRepositoryFirebaseDatabase repo);

    @Binds
    @Singleton
    public abstract CategoryRepository providesCategoryRepository(
            CategoryRepositoryFirebaseDatabase repo);
}
