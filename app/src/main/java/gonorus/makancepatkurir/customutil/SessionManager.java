package gonorus.makancepatkurir.customutil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.view.ActivityLogin;
import gonorus.makancepatkurir.view.ActivityMain;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "session_manager";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // ID kurir
    public static final String KEY_ID_KURIR = "id_kurir";

    // KEY_VALIDATE
    public static final String KEY_VALIDATE = "key_validate";

    // url foto
    public static final String KEY_FOTO = "foto";

    public static final String KEY_ID_TRANSAKSI = "-1";

    public static final String KEY_IS_WORKING = "0";

    public static final String KEY_SHIFT_KERJA = "-1";

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

        editor.putString(KEY_NAME, (model.getFirstName() + " " + model.getLastName()).trim());
        editor.putString(KEY_EMAIL, model.getEmail());
        editor.putInt(KEY_ID_KURIR, model.getIdKurir());
        editor.putString(KEY_VALIDATE, model.getKey_validate());
        editor.putString(KEY_FOTO, model.getFoto());
        editor.putString(KEY_IS_WORKING, model.getIsWorking());
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getKurirDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID_KURIR, Integer.toString(pref.getInt(KEY_ID_KURIR, 0)));
        user.put(KEY_FOTO, pref.getString(KEY_FOTO, ""));
        user.put(KEY_VALIDATE, pref.getString(KEY_VALIDATE, null));
        user.put(KEY_ID_TRANSAKSI, pref.getString(KEY_ID_TRANSAKSI, "-1"));
        user.put(KEY_IS_WORKING, pref.getString(KEY_IS_WORKING, "0"));
        user.put(KEY_SHIFT_KERJA, pref.getString(KEY_SHIFT_KERJA, "-1"));
        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, ActivityLogin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    /**
     * Update session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, ActivityMain.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
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

    public void setKeyIdTransaksi(String id_transaksi) {
        editor.putString(KEY_ID_TRANSAKSI, id_transaksi);
        editor.commit();
    }

    public void setKeyShiftKerja(String shift_kerja) {
        editor.putString(KEY_SHIFT_KERJA, shift_kerja);
        editor.commit();
    }

    public void setKeyIsWorking(String is_working) {
        editor.putString(KEY_IS_WORKING, is_working);
        editor.commit();
    }
}