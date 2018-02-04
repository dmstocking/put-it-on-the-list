package com.github.dmstocking.putitonthelist.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
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
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainController
        extends Controller
        implements MainContract.View, OnGroceryListClicked, OnGroceryListLongClicked {

    private static final String LAYOUT_MANAGER_STATE = "LAYOUT_MANAGER_STATE";
    
    @Inject MainContract.Presenter presenter;
    @Inject MainAdapter adapter;

    @BindView(R.id.list) RecyclerView list;
    @BindDrawable(R.color.black12) Drawable divider;

    private Unbinder unbinder = Unbinder.EMPTY;
    private ActionMode actionMode;
    private ActionMode.Callback actionModelCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_action_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    presenter.onDelete();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            presenter.onCancelSelection();
        }
    };

    @NonNull private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    @Nullable private Parcelable layoutManagerState;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication) getApplicationContext()).coreComponent()
                .mainComponent(new MainModule(this))
                .inject(this);

        View root = inflater.inflate(R.layout.main_controller, container, false);
        unbinder = ButterKnife.bind(this, root);
        list.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(layoutManager);
        list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        DividerItemDecoration decorator = new DividerItemDecoration(
                getActivity(),
                DividerItemDecoration.VERTICAL);
        decorator.setDrawable(divider);
        list.addItemDecoration(decorator);
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setHasOptionsMenu(true);
        presenter.attachView(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void render(MainViewModel viewModel) {
        if (viewModel.isSelecting() && actionMode == null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity != null) {
                actionMode = activity.startSupportActionMode(actionModelCallback);
            }
        } else if (!viewModel.isSelecting() && actionMode != null) {
            actionMode.finish();
        }

        if (actionMode != null) {
            actionMode.setTitle(viewModel.actionTitle());
        }

        adapter.updateModel(viewModel.groceryLists());

        if (layoutManagerState != null) {
            layoutManager.onRestoreInstanceState(layoutManagerState);
            layoutManagerState = null;
        }
    }

    @Override
    public void onGroceryListClicked(ListViewModel model) {
        presenter.onClick(model);
    }

    @Override
    public void onGroceryListLongClicked(ListViewModel model) {
        presenter.onLongClick(model);
    }

    @Override
    public void launchGroceryList(GroceryListId id) {
        startActivity(GroceryListActivity.create(getActivity(), id));
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
        unbinder.unbind();
        unbinder = Unbinder.EMPTY;
    }
}
