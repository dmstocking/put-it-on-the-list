package com.github.dmstocking.putitonthelist.comeback.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.github.dmstocking.putitonthelist.R;
import com.github.dmstocking.putitonthelist.main.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComeBackNotification {

    @NonNull private final Context context;
    @NonNull private final NotificationManager notificationManager;
    @NonNull private final Resources resources;

    @Inject
    public ComeBackNotification(@NonNull Context context,
                                @NonNull NotificationManager notificationManager,
                                @NonNull Resources resources) {
        this.context = context;
        this.notificationManager = notificationManager;
        this.resources = resources;
    }

    public void post() {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                R.id.come_back_pending_intent,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setChannelId("general")
                .setSmallIcon(R.drawable.ic_heart)
                .setContentTitle(resources.getString(R.string.come_back_title))
                .setContentText(resources.getString(R.string.come_back_content))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(R.id.come_back_notification, notification);
    }
}
