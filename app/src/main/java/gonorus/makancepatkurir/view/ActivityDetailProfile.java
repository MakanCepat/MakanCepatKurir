package gonorus.makancepatkurir.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.DetailUserLVAdapter;
import gonorus.makancepatkurir.customutil.MyPermissionChecker;
import gonorus.makancepatkurir.customutil.MyUtilities;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailProfile extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private ModelKurir model;
    private ListView listView;
    private DetailUserLVAdapter adapter;
    private TextView txtNama, txtEmail, txtPhone;
    private CircleImageView imageView;
    private CoordinatorLayout coordinatorLayout;
    private Button snackbar;
    private int RESULT_EDIT_PROFILE = 1001;
    private int RESULT_EDIT_PASSWORD = 1002;
    private int GALLERY_INTENT = 1003;
    private int GALLERY_INTENT_UP_KITKAT = 1004;

    MyPermissionChecker checker;

    MyUtilities x;
    private SessionManager sessionManager;
    private String email = "";
    String imagePath;

    ProgressDialog progressDialog;
    String messageDialog = "";

    String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        if (getSupportActionBar() == null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Profil Kurir");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ActivityDetailProfile.this, ActivityHome.class));
                    finish();
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                }
            });
        }

        init();
        email = sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL);
        Log.d("MAKANCEPAT", email);
        Log.d("MAKANCEPAT", sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
        requestData(email, sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
    }

    private void init() {
        x = new MyUtilities();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL);
        txtNama = (TextView) findViewById(R.id.txtProfilNama);
        txtEmail = (TextView) findViewById(R.id.txtProfilEmail);
        txtPhone = (TextView) findViewById(R.id.txtProfilUsername);
        imageView = (CircleImageView) findViewById(R.id.profile_image);
        imageView.setOnClickListener(this);

        checker = new MyPermissionChecker(this);

        progressDialog = new ProgressDialog(ActivityDetailProfile.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.menuEditProfile:
                Intent i = new Intent(this, ActivityEditProfile.class);
                startActivityForResult(i, RESULT_EDIT_PROFILE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
            case R.id.menuEditPassword:
                Intent i2 = new Intent(this, ActivityChangePassword.class);
                startActivityForResult(i2, RESULT_EDIT_PASSWORD);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_profil, menu);
        return true;
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
                    model = response.body();
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(model.getAlamat());
                    list.add(model.getNoKTP());
                    list.add(model.getBank());
                    list.add(model.getNo_rekening());
                    adapter = new DetailUserLVAdapter(ActivityDetailProfile.this, list);
                    listView = (ListView) findViewById(R.id.lvDetailUser);
                    listView.setAdapter(adapter);
                    txtNama.setText(model.getFirstName() + " " + model.getLastName());
                    txtEmail.setText(model.getEmail());
                    txtPhone.setText(model.getPhone());
                    try {
                        if (!(model.getFoto().equals(null)))
                            sessionManager.changeFoto(model.getFoto());
                        if (!(sessionManager.getKurirDetails().get(SessionManager.KEY_FOTO)).trim().isEmpty())
                            Picasso.with(getApplicationContext()).load(Communicator.BASE_URL + "kurir/images/profile/" + sessionManager.getKurirDetails().get(SessionManager.KEY_FOTO)).skipMemoryCache().into(imageView);
                    } catch (NullPointerException NPE) {
                        // Null Pointer Exception did not find any picture
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ModelKurir> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("MAKANCEPAT", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null == data) return;

        if (requestCode == RESULT_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            x.showInfoDialog(data.getStringExtra("message"), R.style.MyAlertDialogStyle, this, false);
            requestData(email, sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
        }
        if (requestCode == RESULT_EDIT_PASSWORD && resultCode == Activity.RESULT_OK) {
            x.showInfoDialog(data.getStringExtra("message"), R.style.MyAlertDialogStyle, this, false);
        }
        Uri originalUri = null;
        if (requestCode == GALLERY_INTENT) {
            originalUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(originalUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();
                if (!TextUtils.isEmpty(imagePath)) {
                    uploadImage();
                }
            } else {
                x.showInfoDialog("Error...:(", R.style.MyAlertDialogStyle, this, false);
            }
        }
    }

    public void showPhotoSpinner() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetailProfile.this);
        builder.setItems(R.array.option_change_photo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
                    startPermissionsActivity(PERMISSIONS_READ_STORAGE);
                } else {
                    // File System.
                    final Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_PICK);

                    // Chooser of file system options.
                    final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose image..");
                    startActivityForResult(chooserIntent, GALLERY_INTENT);
                }
            }
        });
        AlertDialog b = builder.create();
        b.setCancelable(true);
        b.setCanceledOnTouchOutside(true);
        b.show();
    }

    @Override
    public void onClick(View view) {
        if (view == imageView) {
            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
            } else {
                // Pre-Marshmallow
            }
            showPhotoSpinner();
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Open Gallery"), GALLERY_INTENT);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpeg");
            startActivityForResult(intent, GALLERY_INTENT_UP_KITKAT);
        }
    }

    private void uploadImage() {
        messageDialog = "Uploading image...";
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();

        LoginInterface api = Communicator.getClient().create(LoginInterface.class);
        File file = new File(imagePath);
        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        String filename = sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestIdUser = RequestBody.create(MediaType.parse("text/plain"),
                sessionManager.getKurirDetails().get(SessionManager.KEY_ID_KURIR));
        RequestBody requestFoto = RequestBody.create(MediaType.parse("text/plain"),
                sessionManager.getKurirDetails().get(SessionManager.KEY_FOTO).trim());
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", filename + extension, requestFile);
        Call<ModelKurir> call = api.uploadProfilePicture(body, requestIdUser, requestFoto);
        call.enqueue(new Callback<ModelKurir>() {
            @Override
            public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        requestData(email, sessionManager.getKurirDetails().get(SessionManager.KEY_VALIDATE));
                    } else {
                        x.showInfoDialog(response.message(), R.style.MyAlertDialogStyle, ActivityDetailProfile.this, false);
                    }
                } else {
                    x.showInfoDialog("No response from server...", R.style.MyAlertDialogStyle, ActivityDetailProfile.this, false);
                }
                imagePath = "";
            }

            @Override
            public void onFailure(Call<ModelKurir> call, Throwable t) {
                progressDialog.dismiss();
                x.showInfoDialog(t.getMessage(), R.style.MyAlertDialogStyle, ActivityDetailProfile.this, false);

            }
        });
    }

    private void startPermissionsActivity(String[] permission) {
        ActivityPermission.startActivityForResult(this, 0, permission);
    }
}
