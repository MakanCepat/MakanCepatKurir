package gonorus.makancepatkurir.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.MyUtilities;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChangePassword extends AppCompatActivity  implements TextWatcher {

    private TextView txtCurrentPass, txtNewPass;
    private Button btnPass;
    private MyUtilities myUtilities;
    private SessionManager sessionManager;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Ganti Password");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            });
        }

        init();
    }

    private void init() {
        txtCurrentPass = (TextView) findViewById(R.id.txtCurrentPassword);
        txtCurrentPass.addTextChangedListener(this);
        txtNewPass = (TextView) findViewById(R.id.txtNewPassword);
        txtNewPass.addTextChangedListener(this);
        btnPass = (Button) findViewById(R.id.btnSavePassword);
        checkFieldsForEmptyValues();
        myUtilities = new MyUtilities();
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(ActivityChangePassword.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void savePassword(View view) {
        updatePassword(
                sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL),
                txtCurrentPass.getText().toString(),
                txtNewPass.getText().toString(),
                sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE)
        );
    }

    private void checkFieldsForEmptyValues() {
        Button s = (Button) findViewById(R.id.btnSavePassword);
        String n = txtCurrentPass.getText().toString();
        String p = txtNewPass.getText().toString();
        if (n.length() > 6 && p.length() > 6) {
            s.setEnabled(true);
        } else {
            s.setEnabled(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkFieldsForEmptyValues();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void showDialog(boolean show) {
        if (show) progressDialog.show();
        else progressDialog.dismiss();
    }

    private void updatePassword(String email, String password_lama, String password_baru, String key_validate) {
        showDialog(true);
        LoginInterface service = Communicator.getClient().create(LoginInterface.class);
        service.changeKurirPassword(email, password_lama, password_baru, key_validate).enqueue(new Callback<ModelKurir>() {
            @Override
            public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                if (response.isSuccessful()) {
                    showDialog(false);
                    Intent intent = new Intent();
                    intent.putExtra("message", "Ganti Password berhasil");
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }

            @Override
            public void onFailure(Call<ModelKurir> call, Throwable t) {
                showDialog(false);
                if (t instanceof IOException) {
                    //Add your code for displaying no network connection error
                    Toast.makeText(ActivityChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (t instanceof SocketTimeoutException) {
                    //Add your code for displaying no network connection error
                    Toast.makeText(ActivityChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}