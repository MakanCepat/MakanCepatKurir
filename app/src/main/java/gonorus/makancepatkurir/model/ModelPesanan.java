package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 2/10/2017.
 */

public class ModelPesanan {
    @SerializedName("id")
    int id;

    @SerializedName("id_user")
    int idUser;

    @SerializedName("id_kurir")
    int idKurir;

    @SerializedName("id_transaksi")
    int idTransaksi;

    @SerializedName("is_approve")
    int isApprove;

    @SerializedName("id_menu")
    int idMenu;

    @SerializedName("nama_menu")
    String namaMenu;

    @SerializedName("nama_warung")
    String namaWarung;

    @SerializedName("harga")
    int harga;

    @SerializedName("qty")
    int qty;

    @SerializedName("note")
    String note;

    @SerializedName("total_transaksi")
    int totalTransaksi;

    @SerializedName("waktu_transaksi")
    String waktuTransaksi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(int idKurir) {
        this.idKurir = idKurir;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(int isApprove) {
        this.isApprove = isApprove;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public String getNamaWarung() {
        return namaWarung;
    }

    public void setNamaWarung(String namaWarung) {
        this.namaWarung = namaWarung;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(int totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public String getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }
}
