package com.github.dmstocking.putitonthelist.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.firebase.ui.auth.AuthUI;
import com.github.dmstocking.putitonthelist.BuildConfig;
import com.github.dmstocking.putitonthelist.CoreApplication;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.authentication.UserService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE = R.id.authentication_result & 0xFFF;

    private Router router;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.controller_container) ViewGroup container;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Inject MainService mainService;
    @Inject UserService userService;

    @NonNull
    private Disposable loggedInDisposable = Disposables.disposed();

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        loggedInDisposable.dispose();
        loggedInDisposable = userService.isLoggedIn()
                .subscribe(isLoggedIn -> {
                    if (!isLoggedIn) {
                        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

                        final Intent loginIntent = AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                .setLogo(R.mipmap.ic_launcher)
                                .setTheme(R.style.AppTheme)
                                .build();

                        startActivityForResult(loginIntent, REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            return;
        }

        switch (resultCode) {
            case RESULT_OK:
                break;

            case RESULT_CANCELED:
                FirebaseAuth.getInstance().signInAnonymously();
                break;
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
                            mainService.create(editText.getText().toString())
                                    .subscribe();
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

    @Override
    protected void onPause() {
        super.onPause();
        loggedInDisposable.dispose();
    }
}
