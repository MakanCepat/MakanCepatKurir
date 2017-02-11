package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 2/10/2017.
 */

public class ModelTransaction {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("id_transaksi")
    private int idTransaksi;

    private ModelKurir modelKurir;

    @SerializedName("lattitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("is_ship")
    private int isShipped;

    @SerializedName("is_paid")
    private int isPaid;

    @SerializedName("total_transaksi")
    private int totalTransaksi;

    @SerializedName("total_harga")
    private int totalHarga;

    private ModelPelanggan modelPelanggan;

    @SerializedName("besaran")
    private int besaran;

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

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public ModelKurir getModelKurir() {
        return modelKurir;
    }

    public void setModelKurir(ModelKurir modelKurir) {
        this.modelKurir = modelKurir;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getIsShipped() {
        return isShipped;
    }

    public void setIsShipped(int isShipped) {
        this.isShipped = isShipped;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(int totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public ModelPelanggan getModelPelanggan() {
        return modelPelanggan;
    }

    public void setModelPelanggan(ModelPelanggan modelPelanggan) {
        this.modelPelanggan = modelPelanggan;
    }

    public int getBesaran() {
        return besaran;
    }

    public void setBesaran(int besaran) {
        this.besaran = besaran;
    }
}
