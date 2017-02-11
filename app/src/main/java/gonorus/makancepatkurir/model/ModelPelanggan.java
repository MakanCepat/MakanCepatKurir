package gonorus.makancepatkurir.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 2/10/2017.
 */

public class ModelPelanggan {
    @SerializedName("error")
    boolean error;

    @SerializedName("id_user")
    String idUser;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("username")
    String username;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("message")
    String message;

    @SerializedName("apiKey")
    String apiKey;

    @SerializedName("jabatan")
    String jabatan;

    @SerializedName("foto")
    String foto;

    @SerializedName("no_ktp")
    String noKtp;

    @SerializedName("is_validate")
    int isValidate;

    @SerializedName("no_rekening")
    String noRekening;

    @SerializedName("phone")
    String phone;

    @SerializedName("alamat")
    String alamat;

    @SerializedName("bank")
    String bank;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public int getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(int isValidate) {
        this.isValidate = isValidate;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
