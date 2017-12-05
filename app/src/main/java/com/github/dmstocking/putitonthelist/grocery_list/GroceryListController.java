package com.github.dmstocking.putitonthelist.grocery_list;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.grocery_list.sort.SortActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroceryListController extends Controller implements GroceryListContract.View, OnGroceryListItemClicked {

    private static final String LAYOUT_MANAGER_STATE = "LAYOUT_MANAGER_STATE";

    @BindView(R.id.list) RecyclerView recyclerView;

    @Inject GroceryListAdapter adapter;
    @Inject GroceryListContract.Presenter presenter;

    @NonNull private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    private Parcelable layoutManagerState;
    private int lastFocusedPosition;

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
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return root;
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
                startActivity(SortActivity.create(getActivity(), args.groceryListId()));
                return true;
            case R.id.delete_purchased:
                presenter.onDeletePurchased();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        GroceryListArguments args = getArgs().getParcelable("args");
        presenter.attachView(this, args);
    }

    @Override
    public void render(GroceryListViewModel viewModel) {
        adapter.updateModel(viewModel.groceryList());
        if (layoutManagerState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerState);
            layoutManagerState = null;
        }

        if (lastFocusedPosition >= 0) {
            RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(lastFocusedPosition);
            if (vh != null && vh.itemView != null) {
                vh.itemView.requestFocus();
            }
            layoutManager.scrollToPosition(lastFocusedPosition);
            lastFocusedPosition = -1;
        }
    }

    @Override
    public void onGroceryListItemClicked(View view,
                                         ListViewModel item) {
        lastFocusedPosition = recyclerView.getChildAdapterPosition(view);
        presenter.onGroceryListItemClicked(item);
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        layoutManagerState = savedViewState.getParcelable(LAYOUT_MANAGER_STATE);
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        if (layoutManagerState == null) {
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState());
        } else {
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        presenter.detachView(this);
    }
}
