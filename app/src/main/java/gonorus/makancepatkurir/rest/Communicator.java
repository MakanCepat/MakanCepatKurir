package gonorus.makancepatkurir.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ADMIN on 9/4/2016.
 */

public class Communicator {
    public static final String BASE_URL = "http://adm.makancepat.com/mcapi/";
    //public static final String BASE_URL = "http://192.168.1.65/mcapi/";
    //public static final String BASE_URL = "http://www.mapc.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}