package gonorus.makancepatkurir.rest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.view.ActivityHome;
import gonorus.makancepatkurir.view.ActivityTransaction;

/**
 * Created by USER on 1/27/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private SessionManager sessionManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setKeyIdTransaksi(Integer.parseInt(remoteMessage.getData().get("id_transaksi")));

        Intent intent = new Intent(this, ActivityTransaction.class);
        intent.putExtra("id_transaksi", Integer.parseInt(remoteMessage.getData().get("id_transaksi")));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo_makancepat2)
                .setContentTitle("Makan Cepat")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}