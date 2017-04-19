package gonorus.makancepatkurir.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;

public class ActivityMain extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            try {
                int temp = Integer.parseInt(getIntent().getExtras().get("id_transaksi").toString());
                if (temp > 0) {
                    sessionManager.setKeyIdTransaksi(temp);
                    intent = new Intent(this, ActivityTransaction.class);
                }
                intent.putExtra("notifikasiAlarm", getIntent().getIntExtra("notifikasiAlarm", 0));
            } catch (Exception E) {
                Log.e("MAKANCEPAT", E.getMessage());
            }

            startActivity(intent);
            finish();
        }
    }
}