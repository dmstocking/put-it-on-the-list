package com.github.dmstocking.putitonthelist.grocery_list.items.add;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.bluelinelabs.conductor.Controller;
import com.github.dmstocking.putitonthelist.ColorResources;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.android.widget.GridMarginDecoration;
import com.github.dmstocking.putitonthelist.uitl.ImageLoadingService;
import com.github.dmstocking.putitonthelist.uitl.Log;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class AddGroceryListItemController
        extends Controller
        implements AddGroceryListItemContract.View, AddGroceryListItemOnClick {

    private static final String TAG = "AddGroceryListItemController";

    private static final String LAYOUT_MANAGER_STATE = "LAYOUT_MANAGER_STATE";

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
    private RecyclerView.LayoutManager layoutManager;
    @Nullable private Parcelable layoutManagerState = null;

    @Nullable private String nameValue = null;
    @Nullable private String categoryValue = null;

    public AddGroceryListItemController(@Nullable Bundle bundle) {
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        if (adapter == null) {
            AddGroceryListItemArgs args = getArgs().getParcelable("args");
            ((CoreApplication) getApplicationContext()).coreComponent()
                    .addGroceryListItemComponent(new AddGroceryListItemModule(args, this))
                    .inject(this);
        }
        View root = inflater.inflate(R.layout.grocery_list__item__add__controller,
                                        container,
                                        false);
        ButterKnife.bind(this, root);
        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
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
                    if (layoutManagerState != null) {
                        layoutManager.onRestoreInstanceState(layoutManagerState);
                        layoutManagerState = null;
                    }
                }, throwable -> {
                    log.e(TAG, "Error while getting model", throwable);
                });
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        layoutManagerState = savedViewState.getParcelable(LAYOUT_MANAGER_STATE);
        SavedState savedState = savedViewState.getParcelable("savedState");
        if (savedState != null) {
            presenter.restoreState(savedState);
        }
    }

    @OnTextChanged(R.id.name)
    public void onNameTextChanged(CharSequence value) {
        presenter.onNameChanged(value.toString());
    }

    @OnEditorAction(R.id.name)
    public boolean onNameEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE && name.getText().length() > 0) {
            onDone();
            return true;
        }

        return false;
    }

    @OnClick(R.id.done)
    public void onDone() {
        presenter.onDoneClicked();
    }

    @Override
    public void onCategoryClicked(CategoryDocument categoryDocument) {
        presenter.onCategoryChanged(categoryDocument.getCategory());
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        if (layoutManagerState == null) {
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManager.onSaveInstanceState());
        } else {
            outState.putParcelable(LAYOUT_MANAGER_STATE, layoutManagerState);
        }
        outState.putParcelable("savedState", presenter.onSaveState());
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        subscribe.dispose();
    }
}
