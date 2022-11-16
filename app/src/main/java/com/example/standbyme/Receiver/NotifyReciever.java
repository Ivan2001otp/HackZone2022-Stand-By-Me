package com.example.standbyme.Receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.standbyme.Major.MajorActivity;
import com.example.standbyme.R;

public class NotifyReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MajorActivity.class);
        context.startActivity(i);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notification")
                .setSmallIcon(R.drawable.alarm_clock)
                .setContentTitle("Alarm App")
                .setContentText("Your alarm is Activated!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
