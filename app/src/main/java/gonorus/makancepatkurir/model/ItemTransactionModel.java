package gonorus.makancepatkurir.model;

/**
 * Created by USER on 1/16/2017.
 */

public class ItemTransactionModel {
    private String Nama;
    private String Alamat;
    private double Lat;
    private double Lon;
    private boolean Status;

    public ItemTransactionModel(String nama, String alamat, double lat, double lon, boolean status) {
        Nama = nama;
        Alamat = alamat;
        Lat = lat;
        Lon = lon;
        Status = status;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
