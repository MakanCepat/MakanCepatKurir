package gonorus.makancepatkurir.rest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.InfoModel;
import gonorus.makancepatkurir.view.ActivityMain;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

/**
 * Created by USER on 2/8/2017.
 */

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final SessionManager sessionManager = new SessionManager(context);
        if (sessionManager.getKurirDetails().get(SessionManager.KEY_IS_WORKING).equalsIgnoreCase("0")) {
            sessionManager.setKeyIsWorking(1);
            setNotification(context, "Mengingatkan waktu kerja", ActivityMain.class, "");

            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent intent2 = new Intent(context, ReminderReceiver.class);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 5);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent2);

            LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
            Call<InfoModel> user = apiService.setKurirIsWorking(
                    sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL),
                    "1",
                    sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
            user.enqueue(new Callback<InfoModel>() {
                @Override
                public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                    InfoModel model = response.body();
                    if (model != null) {
                        if (model.isError()) {
                            Toast.makeText(context, "Proses absen sukses", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<InfoModel> call, Throwable t) {
                    Log.e("MAKANCEPAT", t.getMessage());
                }
            });
        } else {
            sessionManager.setKeyIsWorking(0);
            sessionManager.setKeyShiftKerja(-1);

            LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
            Call<InfoModel> user = apiService.setKurirIsWorking(
                    sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL),
                    "0",
                    sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
            user.enqueue(new Callback<InfoModel>() {
                @Override
                public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                    InfoModel model = response.body();
                    if (model != null) {
                        if (model.isError()) {
                            sessionManager.setKeyIsWorking(0);
                            Toast.makeText(context, "Proses absen sukses", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<InfoModel> call, Throwable t) {
                    Log.e("MAKANCEPAT", t.getMessage());
                }
            });
            setNotification(context, "Mengingatkan waktu kerja usai", ActivityMain.class, "3");
        }
    }

    private void setNotification(Context context, String Message, Class forward, String putExtra) {
        Intent MainActivityIntent = new Intent(context, forward);
        MainActivityIntent.putExtra("notifikasiAlarm", putExtra);
        MainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, MainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("Makan Cepat Reminder")
                .setContentText(Message)
                .setSmallIcon(R.drawable.logo_makancepat)
                .setTicker("Makan Cepat Reminder")
                .setVibrate(new long[DEFAULT_VIBRATE])
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX);
        manager.notify(1000, builder.build());
    }
}
