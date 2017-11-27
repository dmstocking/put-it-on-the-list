package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bluelinelabs.conductor.Controller;
import com.github.dmstocking.putitonthelist.ColorResources;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.android.widget.GridMarginDecoration;
import com.github.dmstocking.putitonthelist.uitl.ImageLoadingService;
import com.github.dmstocking.putitonthelist.uitl.Log;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class AddGroceryListItemController
        extends Controller
        implements AddGroceryListItemContract.View, AddGroceryListItemOnClick {

    private static final String TAG = "AddGroceryListItemController";

    @BindView(R.id.list) RecyclerView list;
    @BindView(R.id.name) EditText name;
    @BindView(R.id.category) ImageView category;
    @BindView(R.id.done) View done;

    @Inject AddGroceryListItemAdapter adapter;
    @Inject AddGroceryListItemContract.Presenter presenter;
    @Inject ColorResources colorResources;
    @Inject ImageLoadingService imageLoadingService;
    @Inject Log log;

    private Disposable subscribe = Disposables.disposed();

    public AddGroceryListItemController(@Nullable Bundle bundle) {
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        AddGroceryListItemArgs args = getArgs().getParcelable("args");
        ((CoreApplication) getApplicationContext()).coreComponent()
                .addGroceryListItemComponent(new AddGroceryListItemModule(args, this))
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
        subscribe = presenter.model()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    done.setEnabled(model.doneEnabled());
                    imageLoadingService.load(model.categoryImage())
                            .into(category);
                    int color = colorResources.color(model.categoryColor());
                    category.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    name.setTextColor(color);
                    adapter.update(model.items());
                }, throwable -> {
                    log.e(TAG, "Error while getting model", throwable);
                });
    }

    @OnTextChanged(R.id.name)
    public void onNameTextChanged(CharSequence value) {
        presenter.onNameChanged(value.toString());
    }

    @OnClick(R.id.done)
    public void onDone() {
        presenter.onDoneClicked();
    }

    @Override
    public void onCategoryClicked(CategoryDocument categoryDocument) {
        presenter.onCategoryChanged(categoryDocument);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        subscribe.dispose();
    }
}
