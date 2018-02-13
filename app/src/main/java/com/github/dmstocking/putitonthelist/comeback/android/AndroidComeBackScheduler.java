package com.github.dmstocking.putitonthelist.comeback.android;

import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.github.dmstocking.putitonthelist.uitl.SystemTimeService;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidComeBackScheduler {

    @NonNull private final FirebaseJobDispatcher firebaseJobDispatcher;

    @Inject
    public AndroidComeBackScheduler(@NonNull FirebaseJobDispatcher firebaseJobDispatcher) {
        this.firebaseJobDispatcher = firebaseJobDispatcher;
    }

    public void scheduleComeBackNotificationJob() {
        Job job = firebaseJobDispatcher.newJobBuilder()
                .setService(ComeBackJobService.class)
                .setTag("come-back")
                .setTrigger(Trigger.executionWindow(days(7), days(8)))
                .build();

        firebaseJobDispatcher.mustSchedule(job);
    }

    public void cancelComeBackNotificationJob() {
        firebaseJobDispatcher.cancel("come-back");
    }

    private int days(int days) {
        return (int) TimeUnit.DAYS.toSeconds(days);
    }
}
