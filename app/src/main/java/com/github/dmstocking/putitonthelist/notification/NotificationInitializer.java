package com.github.dmstocking.putitonthelist.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.github.dmstocking.putitonthelist.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationInitializer {

    @NonNull private final Context context;
    @NonNull private final NotificationManager notificationManager;

    @Inject
    public NotificationInitializer(@NonNull Context context,
                                   @NonNull NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "general";
            // The user-visible name of the group.
            CharSequence name = context.getString(R.string.notification_channel);
            notificationManager.createNotificationChannel(new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
