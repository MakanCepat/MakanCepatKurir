package gonorus.makancepatkurir.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;

public class ActivityMain extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLoggedIn()){
            Intent intent = new Intent();
            intent = new Intent(ActivityMain.this, ActivityHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
        }
    }
}
