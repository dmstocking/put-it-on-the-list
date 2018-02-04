package com.github.dmstocking.putitonthelist.grocery_list.sort;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.dmstocking.putitonthelist.BaseActivity;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.GroceryListId;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortActivity extends BaseActivity {

    private Router router;

    @BindView(R.id.controller_container) ViewGroup container;
    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent create(Context context, GroceryListId groceryListId) {
        Intent intent = new Intent(context, SortActivity.class);
        intent.putExtra("args", SortArguments.create(groceryListId));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new SortController(getIntent().getExtras())));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
