package com.github.dmstocking.putitonthelist.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.AutoTransitionChangeHandler;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.grocery_list.GroceryListController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainController extends Controller implements MainContract.View, OnGroceryListClicked {

    @Inject MainContract.Presenter presenter;
    @Inject MainAdapter adapter;

    @BindView(R.id.list) RecyclerView list;

    private Unbinder unbinder;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication) getApplicationContext()).coreComponent()
                .mainComponent(new MainModule(this))
                .inject(this);

        View root = inflater.inflate(R.layout.main_controller, container, false);
        unbinder = ButterKnife.bind(this, root);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.attachView(this);
    }

    @Override
    public void render(MainViewModel viewModel) {
        adapter.updateModel(viewModel.groceryLists());
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        unbinder.unbind();
    }

    @Override
    public void onGroceryListClicked(com.github.dmstocking.putitonthelist.main.ListViewModel model) {
        RouterTransaction transaction = RouterTransaction.with(new GroceryListController())
                .pushChangeHandler(new AutoTransitionChangeHandler())
                .popChangeHandler(new AutoTransitionChangeHandler());
        getRouter().pushController(transaction);
    }
}
