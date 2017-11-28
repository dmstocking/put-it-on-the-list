package com.github.dmstocking.putitonthelist.grocery_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.grocery_list.sort.SortController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroceryListController extends Controller implements GroceryListContract.View, OnGroceryListItemClicked {

    @BindView(R.id.list) RecyclerView recyclerView;

    @Inject GroceryListAdapter adapter;
    @Inject GroceryListContract.Presenter presenter;

    public GroceryListController(@Nullable Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication) getApplicationContext()).coreComponent()
                .groceryListComponent(new GroceryListModule(this))
                .inject(this);

        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.grocery_list_controller, container, false);
        ButterKnife.bind(this, root);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        GroceryListArguments args = getArgs().getParcelable("args");
        presenter.attachView(this, args);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        presenter.detachView(this);
    }

    @Override
    public void render(GroceryListViewModel viewModel) {
        adapter.updateModel(viewModel.groceryList());
    }

    @Override
    public void onGroceryListItemClicked(ListViewModel item) {
        presenter.onGroceryListItemClicked(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.grocery_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        GroceryListArguments args = getArgs().getParcelable("args");
        switch (item.getItemId()) {
            case R.id.sort:
                getRouter().pushController(RouterTransaction.with(SortController.create(args.groceryListId())));
                return true;
            case R.id.delete_purchased:
                presenter.onDeletePurchased();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
