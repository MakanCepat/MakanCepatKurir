package gonorus.makancepatkurir.rest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by USER on 1/27/2017.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String token = null;

    public FirebaseInstanceIDService() {
        onTokenRefresh();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        this.token = FirebaseInstanceId.getInstance().getToken();
    }

    public String getToken() {
        return token;
    }
}
