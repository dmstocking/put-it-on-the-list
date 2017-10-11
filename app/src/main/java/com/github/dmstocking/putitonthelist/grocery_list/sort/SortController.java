package com.github.dmstocking.putitonthelist.grocery_list.sort;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class SortController extends Controller {

    @BindView(R.id.list) RecyclerView recyclerView;

    @Inject SortService sortService;
    @Inject SortAdapter sortAdapter;
    @Inject SortItemTouchHelper itemTouchHelper;

    @NonNull private Disposable subscription = Disposables.empty();

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication)getApplicationContext())
                .coreComponent()
                .sortComponent(new SortModule())
                .inject(this);
        View root = inflater.inflate(R.layout.grocery_list_sort_controller, container, false);
        ButterKnife.bind(this, root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(sortAdapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        subscription = sortService.fetchModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> sortAdapter.update(model));
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        subscription.dispose();
    }
}
