package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

public class ModelKurir {
    @SerializedName("error")
    private boolean error;

    @SerializedName("id_kurir")
    private int idKurir;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("no_ktp")
    private String noKTP;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("key_validate")
    private String key_validate;

    @SerializedName("rate")
    private float rate;

    @SerializedName("bank")
    private String bank;

    @SerializedName("no_rekening")
    private String no_rekening;

    @SerializedName("phone")
    private String phone;

    @SerializedName("foto")
    private String foto;

    @SerializedName("lattitude")
    private double lattitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("is_working")
    private boolean isWorking;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(int idKurir) {
        this.idKurir = idKurir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKey_validate() {
        return key_validate;
    }

    public void setKey_validate(String key_validate) {
        this.key_validate = key_validate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}