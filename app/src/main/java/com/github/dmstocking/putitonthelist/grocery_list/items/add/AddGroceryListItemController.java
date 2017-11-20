package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bluelinelabs.conductor.Controller;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.android.widget.GridMarginDecoration;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.jakewharton.rxbinding2.view.RxView;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class AddGroceryListItemController extends Controller implements AddGroceryListItemContract.View {

    private static final String TAG = "AddGroceryListItemController";

    @BindView(R.id.list) RecyclerView list;
    @BindView(R.id.name) EditText name;
    @BindView(R.id.category) View category;
    @BindView(R.id.done) View done;

    @Inject AddGroceryListItemAdapter adapter;
    @Inject AddGroceryListItemContract.Presenter presenter;
    @Inject Log log;

    private Disposable subscribe = Disposables.disposed();

    public AddGroceryListItemController() {
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ((CoreApplication) getApplicationContext()).coreComponent()
                .addGroceryListItemComponent(new AddGroceryListItemModule())
                .inject(this);
        View root = inflater.inflate(R.layout.grocery_list__item__add__controller,
                                        container,
                                        false);
        ButterKnife.bind(this, root);
        RecyclerView.LayoutManager layoutManager = new FlowLayoutManager();
        list.addItemDecoration(new GridMarginDecoration(getActivity(), R.dimen.dp_8));
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        return root;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        subscribe.dispose();
        subscribe = presenter.model(RxView.clicks(done))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    adapter.update(model.items());
                }, throwable -> {
                    log.e(TAG, "Error while getting model", throwable);
                });
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        subscribe.dispose();
    }
}
