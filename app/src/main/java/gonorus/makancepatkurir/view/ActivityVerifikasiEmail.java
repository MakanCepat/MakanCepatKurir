package gonorus.makancepatkurir.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.model.InfoModel;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityVerifikasiEmail extends AppCompatActivity {
    private LinearLayout progressBar;
    private Button btnVerifikasi;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_email);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Verifikasi Ulang");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.slide_to_left, R.anim.slide_from_right);
                }
            });

        } else {
            setTitle("Verifikasi Ulang");
        }
        progressBar = (LinearLayout) findViewById(R.id.progress_bar);
        btnVerifikasi = (Button) findViewById(R.id.btnVerifikasiEmail);

        showProgressBar(false);
        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekForm();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_to_left, R.anim.slide_from_right);
    }

    private void cekForm() {
        boolean cancel = false;
        View focusView = null;

        EditText txtEmail = (EditText) findViewById(R.id.txtVerifikasiEmail);
        String email = txtEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            txtEmail.setError("Perhatikan email anda...");
            cancel = true;
            focusView = txtEmail;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            requestData(email);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void showProgressBar(boolean visible) {
        if (visible)
            progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    private void requestData(String email) {
        showProgressBar(true);
        LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
        Call<InfoModel> user = apiService.sendVerificationEmail(email);
        user.enqueue(new Callback<InfoModel>() {
            @Override
            public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                showProgressBar(false);
                if (response.isSuccessful()) {
                    InfoModel model = response.body();
                    if (model.isError())
                        showDialog(model.getMessage(), false);
                    else {
                        showDialog(model.getMessage(), true);
                    }
                }
            }

            @Override
            public void onFailure(Call<InfoModel> call, Throwable t) {
                showProgressBar(false);
                showDialog("Tidak dapat tersambung ke server", true);
            }
        });
    }

    private void showDialog(String message, final boolean closeDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                if (closeDialog) finish();
            }
        });

        builder.setMessage(message).setTitle("Informasi");
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}