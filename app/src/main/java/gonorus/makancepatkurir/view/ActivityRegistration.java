package gonorus.makancepatkurir.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegistration extends AppCompatActivity implements View.OnClickListener {

    private Button btnDaftar;
    private Toolbar toolbar;

    private EditText txtNamaDepan, txtNamaBelakang, txtUsername, txtEmail, txtPassword,
            txtNoTelp, txtAlamat, txtNoRekening, txtNoKTP, txtReferal;// txtBank;
    private Spinner spinnerBank;
    private SessionManager sessionManager;

    private RadioButton btnRadioLaki, btnRadioPerempuan;

    ProgressDialog progressDialog;
    String messageDialog = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Daftar");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityRegistration.this, ActivityMain.class));
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
                }
            });

        } else {
            setTitle("Daftar");
        }

        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        txtNamaDepan = (EditText) findViewById(R.id.txtDaftarNamaDepan);
        txtNamaBelakang = (EditText) findViewById(R.id.txtDaftarNamaBelakang);
        txtEmail = (EditText) findViewById(R.id.txtDaftarEmail);
        txtPassword = (EditText) findViewById(R.id.txtDaftarPassword);
        //txtUsername = (EditText) findViewById(R.id.txtDaftarUsername);
        txtNoTelp = (EditText) findViewById(R.id.txtDaftarNoTelp);
        txtAlamat = (EditText) findViewById(R.id.txtDaftarAlamat);
        txtNoRekening = (EditText) findViewById(R.id.txtDaftarNoRekening);
        txtNoKTP = (EditText) findViewById(R.id.txtDaftarNoKTP);
        txtReferal = (EditText) findViewById(R.id.txtDaftarReferal);
        //txtBank = (EditText) findViewById(R.id.txtDaftarBank);
        spinnerBank = (Spinner) findViewById(R.id.spinnerBank);

        btnDaftar.setOnClickListener(this);
        sessionManager = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(ActivityRegistration.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btnDaftar) {
            checkField();
        }
    }

    private void checkField() {
        boolean cancel = false;
        View focusView = null;

        String email = txtEmail.getText().toString().trim();
        //String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String namaDepan = txtNamaDepan.getText().toString().trim();
        String namaBelakang = txtNamaBelakang.getText().toString().trim();
        String noKTP = txtNoKTP.getText().toString().trim();
        String alamat = txtAlamat.getText().toString().trim();
        //String bank = txtBank.getText().toString().trim();
        String bank = spinnerBank.getSelectedItem().toString();
        String noRekening = txtNoRekening.getText().toString().trim();
        String noTelp = txtNoTelp.getText().toString().trim();
        String referal = txtReferal.getText().toString().trim();

        if (TextUtils.isEmpty(namaDepan)) {
            txtNamaDepan.setError("Tidak boleh kosong");
            focusView = txtNamaDepan;
            cancel = true;
        }
        /*
        else if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Tidak boleh kosong");
            focusView = txtUsername;
            cancel = true;
        }
        */
        else if (bank.equals("-")) {
            ((TextView) spinnerBank.getSelectedView()).setError("Pilih salah satu daftar bank");
            focusView = spinnerBank;
            cancel = true;
        } else if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            txtEmail.setError("Email tidak valid");
            focusView = txtEmail;
            cancel = true;
        } else if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            txtPassword.setError("Tidak boleh kosong dan minimal 6 karakter");
            focusView = txtPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(noTelp) || noTelp.length() < 11) {
            txtNoTelp.setError("Tidak boleh kosong dan minimal 11");
            focusView = txtNoTelp;
            cancel = true;
        } else if (TextUtils.isEmpty(alamat) || alamat.length() < 10) {
            txtAlamat.setError("Tidak boleh kosong dan minimal 10 karakter");
            focusView = txtAlamat;
            cancel = true;
        }
        /*
        else if (TextUtils.isEmpty(bank)) {
            txtBank.setError("Tidak boleh kosong");
            focusView = txtBank;
            cancel = true;
        }
        */
        else if (TextUtils.isEmpty(noRekening) || noRekening.length() < 8) {
            txtNoRekening.setError("Isi No. Rekening dengan benar");
            focusView = txtNoRekening;
            cancel = true;
        } else if (TextUtils.isEmpty(noKTP) || noKTP.length() < 16) {
            txtNoKTP.setError("Isi No KTP dengan benar");
            focusView = txtNoKTP;
            cancel = true;
        }
        /*
        else if (TextUtils.isEmpty(referal)) {
            txtReferal.setError("Tidak boleh kosong dan minimal 10 karakter");
            focusView = txtReferal;
            cancel = true;
        }
        */

        if (cancel) {
            focusView.requestFocus();
        } else {
            createUser();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void createUser() {
        messageDialog = "Processing...";
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        String email = txtEmail.getText().toString().trim();
        //String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String noKTP = txtNoKTP.getText().toString().trim();
        String alamat = txtAlamat.getText().toString().trim();
        String namaDepan = txtNamaDepan.getText().toString().trim();
        String namaBelakang = txtNamaBelakang.getText().toString().trim();
        //String bank = txtBank.getText().toString().trim();
        String bank = spinnerBank.getSelectedItem().toString();
        String noRekening = txtNoRekening.getText().toString().trim();
        String noTelp = txtNoTelp.getText().toString().trim();
        String referal = txtReferal.getText().toString().trim();

        LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
        Call<ModelKurir> user = apiService.createKurir(
                email, "", password, namaDepan, namaBelakang, noKTP, alamat, bank, noRekening, noTelp, referal
        );
        user.enqueue(new Callback<ModelKurir>() {
            @Override
            public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                progressDialog.dismiss();
                ModelKurir model = response.body();
                if (model != null) {
                    if (!model.isError())
                        showInfoDialog();
                } else {
                    Toast.makeText(ActivityRegistration.this, "Terjadi kesalahan...Coba Lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelKurir> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityRegistration.this, "Failed to connect the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showInfoDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        dialogBuilder.setTitle("Informasi");
        dialogBuilder.setMessage("Registrasi berhasil, \nSilahkan cek email anda untuk verifikasi\nTerima Kasih");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                startActivity(new Intent(ActivityRegistration.this, ActivityMain.class));
                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_from_right);
                finish();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        b.setCanceledOnTouchOutside(false);
        b.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityRegistration.this, ActivityMain.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
        super.onBackPressed();
    }
}
