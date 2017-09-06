package com.github.dmstocking.putitonthelist.grocery_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.dmstocking.putitonthelist.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroceryListActivity extends AppCompatActivity {

    private Router router;

    @BindView(R.id.controller_container) ViewGroup container;
    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent create(Context context) {
        return new Intent(context, GroceryListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new GroceryListController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
