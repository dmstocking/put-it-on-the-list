package com.github.dmstocking.putitonthelist.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class AuthenticationActivity extends AppCompatActivity {

    private int REQUEST_CODE = R.id.authentication_result & 0xFFF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            final List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

            final Intent loginIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                    .setLogo(R.drawable.ic_reorder_black_24dp)
                    .setTheme(R.style.AppTheme)
                    .build();

            startActivityForResult(loginIntent, REQUEST_CODE);
        } else {
            startActivity(MainActivity.createIntent(this));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            return;
        }

        switch (resultCode) {
            case RESULT_OK:
                startActivity(MainActivity.createIntent(this));
                break;

            case RESULT_CANCELED:
                // Try again?
                break;
        }
    }
}
