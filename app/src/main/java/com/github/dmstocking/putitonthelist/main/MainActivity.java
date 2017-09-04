package com.github.dmstocking.putitonthelist.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Router router;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.controller_container) ViewGroup container;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Inject MainRepository mainRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CoreApplication) getApplicationContext()).coreComponent()
                .inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new MainController()));
        }
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View root = inflater.inflate(R.layout.add_list_dialog, null);
        EditText editText = (EditText) root.findViewById(R.id.create);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.main_add_list_title)
                .setNegativeButton(
                        R.string.cancel,
                        (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(
                        R.string.create,
                        (dialogInterface, i) -> {
                            mainRepository.create(editText.getText().toString());
                            dialogInterface.dismiss();
                        })
                .setView(root)
                .create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
