package gonorus.makancepatkurir.rest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.view.ActivityTransaction;

import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

/**
 * Created by USER on 1/27/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String message = remoteMessage.getNotification().getBody();
        String title = remoteMessage.getNotification().getTitle();
        int idTransaksi = Integer.parseInt(remoteMessage.getData().get("id_transaksi"));

        showNotification(message, title, idTransaksi);
    }

    private void showNotification(String message, String title, int idTransaksi) {
        Intent intent = new Intent(this, ActivityTransaction.class);
        intent.putExtra("IDTransaksi", idTransaksi);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("MAKANCEPAT", "ID-T = "+idTransaksi);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setKeyIdTransaksi(Integer.toString(idTransaksi));
        try {
            Log.d("MAKANCEPAT", "ID-T = "+sessionManager.getKurirDetails().get(SessionManager.KEY_ID_TRANSAKSI));
        } catch (NullPointerException NPE) {
            Log.d("MAKANCEPAT", "NOTIF NPE");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo_makancepat)
                .setVibrate(new long[DEFAULT_VIBRATE])
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}