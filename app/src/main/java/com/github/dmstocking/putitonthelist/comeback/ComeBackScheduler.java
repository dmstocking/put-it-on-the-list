package com.github.dmstocking.putitonthelist.comeback;

import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.comeback.android.AndroidComeBackScheduler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComeBackScheduler {

    @NonNull private final AndroidComeBackScheduler androidComeBackScheduler;

    @Inject
    public ComeBackScheduler(@NonNull AndroidComeBackScheduler androidComeBackScheduler) {
        this.androidComeBackScheduler = androidComeBackScheduler;
    }

    public void scheduleComeBackNotificationJob() {
        androidComeBackScheduler.scheduleComeBackNotificationJob();
    }

    public void cancelComeBackNotificationJob() {
        androidComeBackScheduler.cancelComeBackNotificationJob();
    }
}
