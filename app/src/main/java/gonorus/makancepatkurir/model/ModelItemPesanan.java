package gonorus.makancepatkurir.model;

/**
 * Created by USER on 2/11/2017.
 */

public class ModelItemPesanan {
    private String logo_warung;
    private String nama_warung;
    private String alamat_warung;
    private String lattitude_warung;
    private String longitude_warung;

    public ModelItemPesanan(String logo_warung, String nama_warung, String alamat_warung, String lattitude_warung, String longitude_warung) {
        this.logo_warung = logo_warung;
        this.nama_warung = nama_warung;
        this.alamat_warung = alamat_warung;
        this.lattitude_warung = lattitude_warung;
        this.longitude_warung = longitude_warung;
    }

    public String getLogo_warung() {
        return logo_warung;
    }

    public void setLogo_warung(String logo_warung) {
        this.logo_warung = logo_warung;
    }

    public String getNama_warung() {
        return nama_warung;
    }

    public void setNama_warung(String nama_warung) {
        this.nama_warung = nama_warung;
    }

    public String getAlamat_warung() {
        return alamat_warung;
    }

    public void setAlamat_warung(String alamat_warung) {
        this.alamat_warung = alamat_warung;
    }

    public String getLattitude_warung() {
        return lattitude_warung;
    }

    public void setLattitude_warung(String lattitude_warung) {
        this.lattitude_warung = lattitude_warung;
    }

    public String getLongitude_warung() {
        return longitude_warung;
    }

    public void setLongitude_warung(String longitude_warung) {
        this.longitude_warung = longitude_warung;
    }
}
