package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 1/30/2017.
 */

public class InfoModel {
    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
