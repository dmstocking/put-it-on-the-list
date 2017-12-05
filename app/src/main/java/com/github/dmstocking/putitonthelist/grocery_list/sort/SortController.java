package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.GroceryListId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class SortController extends Controller {

    private static final String LAYOUT_MANAGER_STATE = "LAYOUT_MANAGER_STATE";

    @BindView(R.id.list) RecyclerView recyclerView;

    @Inject SortService sortService;
    @Inject SortAdapter sortAdapter;
    @Inject SortItemTouchHelper itemTouchHelper;

    @NonNull private LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @NonNull private Disposable subscription = Disposables.empty();
    @NonNull private List<SortItemViewModel> startModel = Collections.emptyList();

    @Nullable private Parcelable layoutManagerState = null;

    public SortController(@Nullable Bundle bundle) {
        super(bundle);
    }

    public static SortController create(@NonNull GroceryListId groceryListId) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("args", SortArguments.create(groceryListId));
        return new SortController(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication)getApplicationContext())
                .coreComponent()
                .sortComponent(new SortModule())
                .inject(this);
        View root = inflater.inflate(R.layout.grocery_list_sort_controller, container, false);
        ButterKnife.bind(this, root);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sortAdapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        SortArguments args = getArgs().getParcelable("args");
        subscription = sortService.fetchModel(args.groceryListId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    this.startModel = new ArrayList<>(model);
                    sortAdapter.update(model);
                    if (layoutManagerState != null) {
                        layoutManager.onRestoreInstanceState(layoutManagerState);
                        layoutManagerState = null;
                    }
                });
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
        subscription.dispose();
        if (!startModel.equals(sortAdapter.getModel())) {
            SortArguments args = getArgs().getParcelable("args");
            List<CategoryId> ids = new ArrayList<>();
            for (SortItemViewModel model : sortAdapter.getModel()) {
                ids.add(model.id());
            }
            sortService.reorder(args.groceryListId(), ids);
        }
    }
}
