package gonorus.makancepatkurir.rest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.view.ActivityMain;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

/**
 * Created by USER on 2/8/2017.
 */

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("Message Title")
                .setContentText("Message Text")
                .setSmallIcon(R.drawable.logo_makancepat)
                .setTicker("Test Test Test")
                .setVibrate(new long[DEFAULT_VIBRATE])
                .setPriority(Notification.PRIORITY_MAX);
        manager.notify(2, builder.build());
        Toast.makeText(context, "ta ta ta", Toast.LENGTH_SHORT).show();
    }
}
