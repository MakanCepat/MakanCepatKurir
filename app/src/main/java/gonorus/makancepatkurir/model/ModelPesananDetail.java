package gonorus.makancepatkurir.model;

/**
 * Created by USER on 2/10/2017.
 */

public class ModelPesananDetail {
    private String namaItem;
    private int total;

    public ModelPesananDetail(String namaItem, int total) {
        this.namaItem = namaItem;
        this.total = total;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
