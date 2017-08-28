package com.github.dmstocking.putitonthelist.grocery_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroceryListController extends Controller implements GroceryListContract.View, OnGroceryListItemClicked {

    @BindView(R.id.list) RecyclerView recyclerView;

    @Inject GroceryListAdapter adapter;
    @Inject GroceryListContract.Presenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication) getApplicationContext()).coreComponent()
                .groceryListComponent(new GroceryListModule(this))
                .inject(this);

        View root = inflater.inflate(R.layout.grocery_list_controller, container, false);
        ButterKnife.bind(this, root);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.attachView(this);
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
    }
}
