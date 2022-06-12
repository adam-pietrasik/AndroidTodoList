package com.example.todolistapp.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todolistapp.R;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "TaskReminder")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Task")
                .setContentText("Task is coming to an end")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManage = NotificationManagerCompat.from(context);
        notificationManage.notify(0, builder.build());
    }
}
