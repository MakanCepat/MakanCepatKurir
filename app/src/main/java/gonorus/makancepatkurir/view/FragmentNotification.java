package gonorus.makancepatkurir.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.InfoModel;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import gonorus.makancepatkurir.rest.ReminderReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment {

    private CardView cardView;
    private TextView hariTV, tglTV, bulanTV, jadwalTV, jamKerjaTV, statusTV, statusMsg;
    private FloatingActionButton fab;
    private Switch aSwitch;
    private int DEFAULT_JADWAL = 0;
    private SessionManager sessionManager;

    public FragmentNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_notification, container, false);
        hariTV = (TextView) rootview.findViewById(R.id.HariTV);
        tglTV = (TextView) rootview.findViewById(R.id.TanggalTV);
        bulanTV = (TextView) rootview.findViewById(R.id.BulanTV);
        jadwalTV = (TextView) rootview.findViewById(R.id.JadwalTV);
        jamKerjaTV = (TextView) rootview.findViewById(R.id.jamKerjaTV);
        aSwitch = (Switch) rootview.findViewById(R.id.switch1);
        statusTV = (TextView) rootview.findViewById(R.id.statusTV);
        statusMsg = (TextView) rootview.findViewById(R.id.statusMsg);
        cardView = (CardView) rootview.findViewById(R.id.reminder);

        fab = (FloatingActionButton) rootview.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
                Call<InfoModel> user = apiService.setKurirIsWorking(
                        sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL),
                        Integer.toString(aSwitch.isChecked() ? 1 : 0),
                        sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
                user.enqueue(new Callback<InfoModel>() {
                    @Override
                    public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                        InfoModel model = response.body();
                        if (model != null) {
                            if (!model.isError()) {
                                sessionManager.setKeyIsWorking(aSwitch.isChecked() ? 1 : 0);
                                Toast.makeText(getContext(), "Proses absen sukses", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<InfoModel> call, Throwable t) {
                        Log.e("MAKANCEPAT", t.getMessage());
                    }
                });
            }
        });

        sessionManager = new SessionManager(getContext());
        loadData();

        return rootview;
    }

    void setJadwal(int index) {
        String[] jadwal = {"Pagi", "Siang", "Malam"};
        String[] jamKerja = {"09.00 - 14.00 WIB", "14.00 - 19.00 WIB", "19.00 - 23.00 WIB"};

        jadwalTV.setText(jadwal[index]);
        jamKerjaTV.setText(jamKerja[index]);
    }

    void setTglHariIni(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE-dd-MMMM");
        String dateString = sdf.format(date);
        String[] tgl = dateString.split("-");
        hariTV.setText(tgl[0]);
        tglTV.setText(tgl[1]);
        bulanTV.setText(tgl[2]);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dialog_title);
        builder.setCancelable(true);
        final String[] jadwal = getResources().getStringArray(R.array.jadwal_kerja);
        builder.setSingleChoiceItems(jadwal, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DEFAULT_JADWAL = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.setKeyIsWorking(0);
                sessionManager.setKeyShiftKerja(DEFAULT_JADWAL);
                cardView.setVisibility(View.VISIBLE);
                setJadwal(DEFAULT_JADWAL);
                setTglHariIni(1);
                fab.setVisibility(View.GONE);

                AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getContext(), ReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 0);
                switch (DEFAULT_JADWAL) {
                    case 0:
                        calendar.set(Calendar.HOUR_OF_DAY, 9);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        break;
                    case 1:
                        calendar.set(Calendar.HOUR_OF_DAY, 14);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        break;
                    case 2:
                        calendar.set(Calendar.HOUR_OF_DAY, 19);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        break;
                }
                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
    }

    private void loadData () {
        aSwitch.setChecked(false);
        try {
            if (!sessionManager.getKurirDetails().get(SessionManager.KEY_SHIFT_KERJA).equals("-1")) {
                cardView.setVisibility(View.VISIBLE);
                if (sessionManager.getKurirDetails().get(SessionManager.KEY_IS_WORKING).equals("1")) {
                    setJadwal(Integer.parseInt(sessionManager.getKurirDetails().get(SessionManager.KEY_SHIFT_KERJA)));
                    setTglHariIni(0);
                    fab.setVisibility(View.GONE);
                } else {
                    setJadwal(Integer.parseInt(sessionManager.getKurirDetails().get(SessionManager.KEY_SHIFT_KERJA)));
                    setTglHariIni(1);
                    fab.setVisibility(View.GONE);
                }
            }
            if (sessionManager.getKurirDetails().get(SessionManager.KEY_IS_WORKING).equals("1")) {
                aSwitch.setChecked(true);
                statusTV.setText("Aktif");
                statusMsg.setVisibility(View.VISIBLE);
            } else {
                aSwitch.setChecked(false);
                statusTV.setText("Off");
                statusMsg.setVisibility(View.GONE);
            }
        } catch (NullPointerException NPE) {
        }
        aSwitch.setVisibility(View.GONE);
    }
}