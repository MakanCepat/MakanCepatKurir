package gonorus.makancepatkurir.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.rest.ReminderReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment {


    public FragmentNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_notification, container, false);
        Button button = (Button) rootview.findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar myAlarmDate = Calendar.getInstance();
                myAlarmDate.setTimeInMillis(System.currentTimeMillis());
                myAlarmDate.set(2017, 2, 2, 15, 59);

                AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getContext(), ReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0,  intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, pendingIntent);
            }
        });
        return rootview;
    }

}