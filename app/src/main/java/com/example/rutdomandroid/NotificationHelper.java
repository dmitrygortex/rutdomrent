package com.example.rutdomandroid;

import static androidx.core.content.ContextCompat.startActivity;

import static com.google.common.reflect.Reflection.getPackageName;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    public static final String RENT_REMIND_CHANNEL_ID = "rent_remind_channel";

    public static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null && notificationManager.getNotificationChannel(RENT_REMIND_CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(RENT_REMIND_CHANNEL_ID, "Rent Remind Channel", NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is a channel for reminding about upcoming sessions of rent");
                channel.enableLights(true);
                channel.setLightColor(Color.CYAN);
                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void sendNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, RENT_REMIND_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"we dont have permissions for notify u", Toast.LENGTH_LONG).show();
            openNotificationSettings(context);
            return;
        }
        notificationManagerCompat.notify(5555, builder.build());
    }
    private static void openNotificationSettings(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }
// only API 26+
//    private static void openChannelSettings(String channelId, Context context) {
//        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
//        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
//        context.startActivity(intent);
//    }

}
