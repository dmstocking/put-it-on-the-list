package com.github.dmstocking.putitonthelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.github.dmstocking.putitonthelist.comeback.ComeBackScheduler;
import com.github.dmstocking.putitonthelist.main.MainActivity;

public class BaseActivity extends AppCompatActivity {

    private ComeBackScheduler comeBackScheduler;

    @Override
    protected void onResume() {
        super.onResume();
        CoreComponent coreComponent = ((CoreApplication) getApplication()).coreComponent();
        comeBackScheduler = coreComponent.comeBackScheduler();
        coreComponent.userService()
                .isLoggedIn()
                .subscribe(loggedIn -> {
                    if (!loggedIn) {
                        finish();
                        Intent intent = MainActivity.createIntent(this);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, throwable -> {
                    coreComponent.log().e("BaseActivity", "Error while checking logged in status", throwable);
                });

        comeBackScheduler.cancelComeBackNotificationJob();
    }

    @Override
    protected void onPause() {
        super.onPause();
        comeBackScheduler.scheduleComeBackNotificationJob();
    }
}
