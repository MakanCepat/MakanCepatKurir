package gonorus.makancepatkurir.customutil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.view.ActivityMain;

public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    private static final int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "session_manager";

    // kondisi status kurir sedang menggunakan aplikasi atau tidak
    private static final String IS_LOGIN = "Is Logged In";

    // id yang dimiliki kurir
    public static final String KEY_ID_KURIR = "id_kurir";

    // email yang digunakan kurir
    public static final String KEY_EMAIL = "email";

    // nama lengkap yang digunakan kurir
    public static final String KEY_NAME = "first_name + last_name";

    // kunci validasi aktivitas kurir
    public static final String KEY_VALIDATE = "validate_key";

    // status aktivasi account
    // 0 => belum teraktivasi
    public static final String KEY_IS_VALIDATE = "is_validated";

    // url foto kurir yang login
    public static final String KEY_FOTO = "foto";

    // status kurir sedang bekerja atau tidak
    // 0 => sedang tidak bekerja / sedang melayani antaran
    public static final String KEY_IS_WORKING = "is_working";

    // id transaksi yang di layani kurir
    // 0 => sedang tidak melayani transaksi
    public static final String KEY_ID_TRANSAKSI = "id_transaksi";

    // status jadwal kurir yang terdaftar
    // -1 => tidak memiliki jadwal terdaftar
    public static final String KEY_SHIFT_KERJA = "shift_kerja";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(ModelKurir model) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_ID_KURIR, model.getIdKurir());
        editor.putString(KEY_EMAIL, model.getEmail());
        editor.putString(KEY_NAME, (model.getFirstName() + " " + model.getLastName()).trim());
        editor.putString(KEY_VALIDATE, model.getKey_validate());
        editor.putInt(KEY_IS_VALIDATE, model.getIs_validate());
        editor.putString(KEY_FOTO, model.getFoto());
        editor.putInt(KEY_IS_WORKING, model.getIsWorking());
        editor.putInt(KEY_ID_TRANSAKSI, 0);
        editor.putInt(KEY_SHIFT_KERJA, -1);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getKurirDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID_KURIR, Integer.toString(pref.getInt(KEY_ID_KURIR, 0)));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_VALIDATE, pref.getString(KEY_VALIDATE, ""));
        user.put(KEY_IS_VALIDATE, Integer.toString(pref.getInt(KEY_IS_VALIDATE, 0)));
        user.put(KEY_FOTO, pref.getString(KEY_FOTO, ""));
        user.put(KEY_IS_WORKING, Integer.toString(pref.getInt(KEY_IS_WORKING, 0)));
        user.put(KEY_ID_TRANSAKSI, Integer.toString(pref.getInt(KEY_ID_TRANSAKSI, 0)));
        user.put(KEY_SHIFT_KERJA, Integer.toString(pref.getInt(KEY_SHIFT_KERJA, -1)));
        return user;
    }

    /**
     * Update session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void changeFoto(String foto) {
        editor.putString(KEY_FOTO, foto);
        editor.commit();
    }

    public void setKeyIdTransaksi(int id_transaksi) {
        editor.putInt(KEY_ID_TRANSAKSI, id_transaksi);
        editor.commit();
    }

    public void setKeyShiftKerja(int shift_kerja) {
        editor.putInt(KEY_SHIFT_KERJA, shift_kerja);
        editor.commit();
    }

    public void setKeyIsWorking(int is_working) {
        editor.putInt(KEY_IS_WORKING, is_working);
        editor.commit();
    }
}