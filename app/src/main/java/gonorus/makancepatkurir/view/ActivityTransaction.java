package gonorus.makancepatkurir.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ItemPesananAdapter;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.InfoModel;
import gonorus.makancepatkurir.model.ModelItemPesanan;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.model.ModelResponsePesanan;
import gonorus.makancepatkurir.model.ModelResponseTransaction;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTransaction extends AppCompatActivity {
    private TextView namaCustomerTV, notelpTV, hutang, bayar;
    private CircleImageView profileImage;
    private SessionManager sessionManager;
    private ProgressDialog progressDialog;
    private CheckBox checkBox;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Pesanan");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityTransaction.this, ActivityHome.class));
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }
            });
        }

        final int IDTransaksi = getIntent().getIntExtra("IDTransaksi", -1);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        namaCustomerTV = (TextView) findViewById(R.id.namaCustTV);
        notelpTV = (TextView) findViewById(R.id.noTelpCustTv);
        hutang = (TextView) findViewById(R.id.Hutang);
        bayar = (TextView) findViewById(R.id.Bayar);
        checkBox = (CheckBox) findViewById(R.id.checkBox2);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                    button.setText("Confirm");
                else
                    button.setText("Route");
            }
        });
        progressDialog = new ProgressDialog(ActivityTransaction.this);

        button = (Button) findViewById(R.id.btnRoute);
        if (IDTransaksi >= 0) {
            progressDialog.setMessage("Processing...");
            progressDialog.show();
            sessionManager = new SessionManager(getApplicationContext());
            LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
            Call<ModelResponseTransaction> user = apiService.getTransaksi(IDTransaksi, sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
            user.enqueue(new Callback<ModelResponseTransaction>() {
                @Override
                public void onResponse(Call<ModelResponseTransaction> call, Response<ModelResponseTransaction> response) {
                    //showProgress(false);
                    final ModelResponseTransaction model = response.body();
                    if (model != null) {
                        if (model.isError()) {
                            // SUKSES BUT ERROR
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // SUKSES TRANSAKSI
                            progressDialog.dismiss();
                            namaCustomerTV.setText((model.getUser_first_name() + " " + model.getUser_last_name()).trim());
                            notelpTV.setText(model.getUser_email() + "/" + model.getUser_phone());
                            hutang.setText("Total : Rp. " + model.getTransaksi_total());
                            if (model.getTransaksi_is_paid() == 1)
                                bayar.setText("Bayar :  " + model.getTransaksi_jumlah_bayar());
                            else
                                bayar.setText("Bayar :  Rp. 0");
                            try {
                                if (!model.getUser_foto().trim().isEmpty())
                                    Picasso.with(getApplicationContext()).load(Communicator.BASE_URL + model.getUser_foto()).skipMemoryCache().into(profileImage);
                            } catch (NullPointerException NPE) {
                                // Null Pointer Exception did not find any picture
                            }

                            List<ModelResponsePesanan> responsePesanan = model.getPesanan();
                            List<ModelItemPesanan> daftarResto = new ArrayList<>();
                            String temp = "";
                            for (int i = 0; i < responsePesanan.size(); i++) {
                                if (i == (responsePesanan.size() - 1) && temp.isEmpty())
                                    daftarResto.add(new ModelItemPesanan(
                                            responsePesanan.get(i).getWarung_logo(),
                                            responsePesanan.get(i).getWarung_nama(),
                                            responsePesanan.get(i).getWarung_alamat(),
                                            responsePesanan.get(i).getWarung_lattitude(),
                                            responsePesanan.get(i).getWarung_longitude()
                                    ));
                                else if (temp.isEmpty() || !temp.equals(responsePesanan.get(i).getWarung_nama())) {
                                    temp = responsePesanan.get(i).getWarung_nama();
                                    daftarResto.add(new ModelItemPesanan(
                                            responsePesanan.get(i).getWarung_logo(),
                                            responsePesanan.get(i).getWarung_nama(),
                                            responsePesanan.get(i).getWarung_alamat(),
                                            responsePesanan.get(i).getWarung_lattitude(),
                                            responsePesanan.get(i).getWarung_longitude()
                                    ));
                                }
                            }
                            ItemPesananAdapter pesananRvAdapter = new ItemPesananAdapter(responsePesanan, daftarResto, ActivityTransaction.this);
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(pesananRvAdapter);

                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!checkBox.isChecked()) {
                                        String label = (model.getUser_first_name() + " " + model.getUser_last_name()).trim();
                                        String uriBegin = "geo:" + model.getTransaksi_lattitude() + "," + model.getTransaksi_longitude();
                                        String query = model.getTransaksi_lattitude() + "," + model.getTransaksi_longitude() + "(" + label + ")";
                                        String encodedQuery = Uri.encode(query);
                                        String uriString = uriBegin + "?q=" + encodedQuery;
                                        Uri uri = Uri.parse(uriString);
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                                        Intent chooser = Intent.createChooser(intent, "Launch Maps");
                                        v.getContext().startActivity(chooser);
                                    } else {
                                        showConfim(IDTransaksi);
                                    }
                                }
                            });
                        }
                    } else {
                        // ERROR GET TRANSAKSI
                    }
                }

                @Override
                public void onFailure(Call<ModelResponseTransaction> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("MAKANCEPAT", t.getMessage());
                }
            });
        } else {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
            finish();
        }
    }

    public void showConfim(final int IDTransaksi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        // Add the buttons
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
                Call<InfoModel> user = apiService.updateTransaksiStatus(IDTransaksi, Integer.toString(IDTransaksi), Integer.toString(IDTransaksi));
                user.enqueue(new Callback<InfoModel>() {
                    @Override
                    public void onResponse(Call<InfoModel> call, Response<InfoModel> response) {
                        // SUCCEED TO LOGOUT
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<InfoModel> call, Throwable t) {
                        // FAILED TO LOGOUT
                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Pesanan sukses diterima pelanggan ?").setTitle("Konfirmasi");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
