package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 2/11/2017.
 */

public class ModelResponsePesanan {
    @SerializedName("detail_qty")
    private int detail_qty;

    @SerializedName("detail_price")
    private int detail_price;

    @SerializedName("detail_note")
    private String detail_note;

    @SerializedName("menu_nama")
    private String menu_nama;

    @SerializedName("menu_harga")
    private int menu_harga;

    @SerializedName("warung_nama")
    private String warung_nama;

    @SerializedName("warung_alamat")
    private String warung_alamat;

    @SerializedName("warung_lattitude")
    private String warung_lattitude;

    @SerializedName("warung_longitude")
    private String warung_longitude;

    @SerializedName("warung_logo")
    private String warung_logo;

    public int getDetail_qty() {
        return detail_qty;
    }

    public void setDetail_qty(int detail_qty) {
        this.detail_qty = detail_qty;
    }

    public int getDetail_price() {
        return detail_price;
    }

    public void setDetail_price(int detail_price) {
        this.detail_price = detail_price;
    }

    public String getDetail_note() {
        return detail_note;
    }

    public void setDetail_note(String detail_note) {
        this.detail_note = detail_note;
    }

    public String getMenu_nama() {
        return menu_nama;
    }

    public void setMenu_nama(String menu_nama) {
        this.menu_nama = menu_nama;
    }

    public int getMenu_harga() {
        return menu_harga;
    }

    public void setMenu_harga(int menu_harga) {
        this.menu_harga = menu_harga;
    }

    public String getWarung_nama() {
        return warung_nama;
    }

    public void setWarung_nama(String warung_nama) {
        this.warung_nama = warung_nama;
    }

    public String getWarung_alamat() {
        return warung_alamat;
    }

    public void setWarung_alamat(String warung_alamat) {
        this.warung_alamat = warung_alamat;
    }

    public String getWarung_lattitude() {
        return warung_lattitude;
    }

    public void setWarung_lattitude(String warung_lattitude) {
        this.warung_lattitude = warung_lattitude;
    }

    public String getWarung_longitude() {
        return warung_longitude;
    }

    public void setWarung_longitude(String warung_longitude) {
        this.warung_longitude = warung_longitude;
    }

    public String getWarung_logo() {
        return warung_logo;
    }

    public void setWarung_logo(String warung_logo) {
        this.warung_logo = warung_logo;
    }
}
