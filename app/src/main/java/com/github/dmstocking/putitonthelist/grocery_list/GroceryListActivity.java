package com.github.dmstocking.putitonthelist.grocery_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.dmstocking.putitonthelist.BaseActivity;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.grocery_list.items.add.AddGroceryListItemActivity;
import com.github.dmstocking.putitonthelist.main.GroceryListId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GroceryListActivity extends BaseActivity {

    private Router router;

    @BindView(R.id.controller_container) ViewGroup container;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    public static Intent create(Context context, GroceryListId groceryListId) {
        Intent intent = new Intent(context, GroceryListActivity.class);
        intent.putExtra("args", GroceryListArguments.create(groceryListId));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new GroceryListController(getIntent().getExtras())));
        }
    }

    @OnClick(R.id.fab)
    public void onFloatingActionBarClicked() {
        GroceryListArguments args = getIntent().getParcelableExtra("args");
        startActivity(AddGroceryListItemActivity.create(this, args.groceryListId()));
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
