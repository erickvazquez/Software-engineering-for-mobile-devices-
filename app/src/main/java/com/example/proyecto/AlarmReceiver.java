package com.example.proyecto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String event = intent.getStringExtra("event");
        String time = intent.getStringExtra("time");
        String dosis = intent.getStringExtra("dosis");
        byte[] img = intent.getByteArrayExtra("img");
        Bitmap myImg = BitmapFactory.decodeByteArray(img, 0, img.length);
        String contenido = event + " " + dosis;
        int notId = intent.getIntExtra("idalarma", 0);

        String channelId = "channel_id";
        CharSequence name = "channel_name";
        String description = "description";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(myImg)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(contenido)
                .setContentText(time)
                .setGroup("Group_calendar_view")
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationManagerCompat.notify(notId, notification);


    }
}
