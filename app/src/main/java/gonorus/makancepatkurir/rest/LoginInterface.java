package gonorus.makancepatkurir.rest;

import gonorus.makancepatkurir.model.InfoModel;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.model.ModelResponseTransaction;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by USER on 1/30/2017.
 */

public interface LoginInterface {
    @FormUrlEncoded
    //@Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("kurir/login")
    Call<ModelKurir> checkLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("kurir/logout")
    Call<ModelKurir> checkLogout(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("kurir/register")
    Call<ModelKurir> createKurir(
            //email, username, password, namaDepan, namaBelakang, noKTP, alamat, bank, noRekening, noTelp, referal
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("namaDepan") String namaDepan,
            @Field("namaBelakang") String namaBelakang,
            @Field("noKTP") String noKTP,
            @Field("alamat") String alamat,
            @Field("bank") String bank,
            @Field("noRekening") String noRekening,
            @Field("noTelp") String noTelp,
            @Field("referal") String referal
    );

    @FormUrlEncoded
    @POST("kurir/kurirInfo")
    Call<ModelKurir> kurirInfo(
            @Field("email") String email,
            @Field("key_validate") String key_validate
    );

    @FormUrlEncoded
    @POST("kurir/send_verification_email")
    Call<InfoModel> sendVerificationEmail(@Field("email") String email);

    @Multipart
    @POST("kurir/upload_profile_picture")
    Call<ModelKurir> uploadProfilePicture(
            @Part MultipartBody.Part file,
            @Part("id_user") RequestBody idUser,
            @Part("last_foto") RequestBody lastFoto
    );

    @FormUrlEncoded
    @POST("kurir/updateKurir")
    Call<ModelKurir> updateProfilKurir(
            @Field("id_user") String idUser,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("phone") String noTelp,
            @Field("no_ktp") String noKtp,
            @Field("no_rekening") String noRekening,
            @Field("alamat") String alamat,
            @Field("bank") String bank
    );

    @FormUrlEncoded
    @POST("kurir/getTransaksi")
    Call<ModelResponseTransaction> getTransaksi(
            @Field("id_transaksi") int id_transaksi,
            @Field("key_validate") String key_validate
    );

    @FormUrlEncoded
    @POST("kurir/updateTransaksiStatus")
    Call<InfoModel> updateTransaksiStatus(
            @Field("id_transaksi") int id_transaksi,
            @Field("jumlah_dibayar") String jumlah_dibayar,
            @Field("kembalian") String kembalian
    );

    @FormUrlEncoded
    @POST("kurir/changeKurirPassword")
    Call<ModelKurir> changeKurirPassword(
            @Field("email") String email,
            @Field("password_lama") String password_lama,
            @Field("password_baru") String password_baru,
            @Field("key_validate") String key_validate
    );

    @FormUrlEncoded
    @POST("kurir/setKurirIsWorking")
    Call<InfoModel> setKurirIsWorking(
            @Field("email") String email,
            @Field("is_working") String is_working,
            @Field("key_validate") String key_validate
    );
}
