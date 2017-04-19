package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 2/11/2017.
 */

public class ModelResponseTransaction {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("transaksi_id")
    private String transaksi_id;

    @SerializedName("kurir_email")
    private String kurir_email;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("user_phone")
    private String user_phone;

    @SerializedName("user_foto")
    private String user_foto;

    @SerializedName("user_first_name")
    private String user_first_name;

    @SerializedName("user_last_name")
    private String user_last_name;

    @SerializedName("transaksi_waktu")
    private String transaksi_waktu;

    @SerializedName("transaksi_lattitude")
    private String transaksi_lattitude;

    @SerializedName("transaksi_longitude")
    private String transaksi_longitude;

    @SerializedName("transaksi_is_paid")
    private int transaksi_is_paid;

    @SerializedName("transaksi_total")
    private int transaksi_total;

    @SerializedName("transaksi_jumlah_bayar")
    private int transaksi_jumlah_bayar;

    @SerializedName("pesanan")
    private List<ModelResponsePesanan> pesanan;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTransaksi_id() {
        return transaksi_id;
    }

    public void setTransaksi_id(String transaksi_id) {
        this.transaksi_id = transaksi_id;
    }

    public String getKurir_email() {
        return kurir_email;
    }

    public void setKurir_email(String kurir_email) {
        this.kurir_email = kurir_email;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_foto() {
        return user_foto;
    }

    public void setUser_foto(String user_foto) {
        this.user_foto = user_foto;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getTransaksi_waktu() {
        return transaksi_waktu;
    }

    public void setTransaksi_waktu(String transaksi_waktu) {
        this.transaksi_waktu = transaksi_waktu;
    }

    public String getTransaksi_lattitude() {
        return transaksi_lattitude;
    }

    public void setTransaksi_lattitude(String transaksi_lattitude) {
        this.transaksi_lattitude = transaksi_lattitude;
    }

    public String getTransaksi_longitude() {
        return transaksi_longitude;
    }

    public void setTransaksi_longitude(String transaksi_longitude) {
        this.transaksi_longitude = transaksi_longitude;
    }

    public int getTransaksi_is_paid() {
        return transaksi_is_paid;
    }

    public void setTransaksi_is_paid(int transaksi_is_paid) {
        this.transaksi_is_paid = transaksi_is_paid;
    }

    public int getTransaksi_total() {
        return transaksi_total;
    }

    public void setTransaksi_total(int transaksi_total) {
        this.transaksi_total = transaksi_total;
    }

    public int getTransaksi_jumlah_bayar() {
        return transaksi_jumlah_bayar;
    }

    public void setTransaksi_jumlah_bayar(int transaksi_jumlah_bayar) {
        this.transaksi_jumlah_bayar = transaksi_jumlah_bayar;
    }

    public List<ModelResponsePesanan> getPesanan() {
        return pesanan;
    }

    public void setPesanan(List<ModelResponsePesanan> pesanan) {
        this.pesanan = pesanan;
    }
}
