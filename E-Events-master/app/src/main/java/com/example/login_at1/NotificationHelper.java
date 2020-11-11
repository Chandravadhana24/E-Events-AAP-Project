package com.example.login_at1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelId = "channel1_Id";
    public static final String channelName = "Channel1";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager()
    {
        if(mManager == null)
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotif(String title, String msg)
    {
        return new NotificationCompat.Builder(getApplicationContext(),channelId)
        .setContentTitle(title)
        .setContentText(msg)
        .setSmallIcon(R.drawable.ic_all_events);
    }
}
