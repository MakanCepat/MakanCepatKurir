package gonorus.makancepatkurir.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher {

    private Spinner spinner;
    private Button btnEdit;
    private EditText txtNamaDepan, txtNamaBelakang, txtNoKTP, txtPhone, txtNoRekening, txtAlamat;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    String messageDialog = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Edit Profile");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            });
        }

        init();
        requestData(sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL), sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
        checkFieldsForEmptyValues();
    }

    private void init() {
        sessionManager = new SessionManager(ActivityEditProfile.this);

        txtNamaDepan = (EditText) findViewById(R.id.txtNamaDepan);
        txtNamaBelakang = (EditText) findViewById(R.id.txtNamaBelakang);
        txtPhone = (EditText) findViewById(R.id.txtNoTelp);
        txtNoKTP = (EditText) findViewById(R.id.txtNoKTP);
        txtNoRekening = (EditText) findViewById(R.id.txtNoRekening);
        txtAlamat = (EditText) findViewById(R.id.txtAlamat);

        txtNamaDepan.addTextChangedListener(this);
        txtNamaBelakang.addTextChangedListener(this);
        txtPhone.addTextChangedListener(this);
        txtNoKTP.addTextChangedListener(this);
        txtNoRekening.addTextChangedListener(this);
        txtAlamat.addTextChangedListener(this);

        spinner = (Spinner) findViewById(R.id.spinnerBank);
        spinner.setOnItemSelectedListener(this);

        btnEdit = (Button) findViewById(R.id.btnEditProfil);
        btnEdit.setOnClickListener(this);

        progressDialog = new ProgressDialog(ActivityEditProfile.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view == btnEdit) {
            updateService();
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

    private void checkFieldsForEmptyValues() {
        String namaDepan = txtNamaDepan.getText().toString();
        String noTelp = txtPhone.getText().toString();
        String noKTP = txtNoKTP.getText().toString();
        String noRekening = txtNoRekening.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String bank = spinner.getSelectedItem().toString();
        if (namaDepan.length() > 3 && noKTP.length() > 12
                && noTelp.length() > 5 && noRekening.length() > 9 && alamat.length() > 10 && !bank.equals("-")) {
            btnEdit.setEnabled(true);
        } else {
            btnEdit.setEnabled(false);
        }
    }


    private void updateService() {
        String idUser = sessionManager.getKurirDetails().get(SessionManager.KEY_ID_KURIR);
        String namaDepan = txtNamaDepan.getText().toString();
        String namaBelakang = txtNamaBelakang.getText().toString();
        String noTelp = txtPhone.getText().toString();
        String noKTP = txtNoKTP.getText().toString();
        String noRekening = txtNoRekening.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String bank = spinner.getSelectedItem().toString();
        LoginInterface service = Communicator.getClient().create(LoginInterface.class);
        service.updateProfilKurir(idUser, namaDepan, namaBelakang, noTelp, noKTP, noRekening, alamat, bank)
                .enqueue(new Callback<ModelKurir>() {
                    @Override
                    public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.putExtra("message", response.body().getMessage());
                            setResult(RESULT_OK, intent);
                            finish();
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelKurir> call, Throwable t) {
                        setResult(RESULT_CANCELED);
                        finish();
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                });
    }

    private void requestData(String email, String key_validate) {
        messageDialog = "Processing...";
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        LoginInterface service = Communicator.getClient().create(LoginInterface.class);
        service.kurirInfo(email, key_validate).enqueue(new Callback<ModelKurir>() {
            @Override
            public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    ModelKurir model = response.body();
                    txtNamaDepan.setText(model.getFirstName());
                    txtNamaBelakang.setText(model.getLastName());
                    txtPhone.setText(model.getPhone());
                    txtNoKTP.setText(model.getNoKTP());
                    txtNoRekening.setText(model.getNo_rekening());
                    txtAlamat.setText(model.getAlamat());

                    Resources res = getResources();
                    String[] planets = res.getStringArray(R.array.list_bank);
                    int i = 0;
                    for (String text : planets) {
                        if (model.getBank().equals(text)) {
                            spinner.setSelection(i);
                            return;
                        }
                        i++;
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelKurir> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("MAKANCEPAT", t.getMessage());
            }
        });
    }
}
