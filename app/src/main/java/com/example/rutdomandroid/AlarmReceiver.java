package com.example.rutdomandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToMyRents = new Intent(context, CancelationFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentToMyRents, PendingIntent.FLAG_IMMUTABLE);
        NotificationHelper.createChannel(context);
        NotificationHelper.sendNotification(context, "Напоминание о бронировании | RUT DOM", "До начала вашего времени аренды остался час", pendingIntent);

    }
}
