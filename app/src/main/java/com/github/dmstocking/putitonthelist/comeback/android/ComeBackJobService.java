package com.github.dmstocking.putitonthelist.comeback.android;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.github.dmstocking.putitonthelist.CoreApplication;

public class ComeBackJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        ((CoreApplication)getApplication()).coreComponent()
                .comeBackNotification()
                .post();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
