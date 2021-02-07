package android.unipu.cinema.retrofit;

import java.sql.Blob;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import android.unipu.cinema.model.*;

public interface IRegistration {

    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("ime") String ime,
                                                 @Field("prezime") String prezime,
                                                 @Field("telefon") String telefon,
                                                 @Field("email") String email,
                                                 @Field("lozinka") String lozinka);


    @POST("login")
    @FormUrlEncoded
    Call<List<UserModel>> loginUser(@Field("email") String email,
                                 @Field("lozinka") String lozinka);

    @POST("user/update")
    @FormUrlEncoded
    Observable<String> updateUser(@Field("ime") String ime,
                                    @Field("prezime") String prezime,
                                    @Field("telefon") String telefon,
                                    @Field("email") String email,
                                    @Field("lozinka") String lozinka,
                                    @Field("slika") String slika,
                                    @Field("id") int id);

    @POST("user/update2")
    @FormUrlEncoded
    Observable<String> updateUser2(@Field("ime") String ime,
                                  @Field("prezime") String prezime,
                                  @Field("telefon") String telefon,
                                  @Field("email") String email,
                                  @Field("slika") String slika,
                                  @Field("id") int id);


    @GET("user/podaci/{email}")
    Call<List<UserModel>> getUser(@Path("email") String email);

    @GET("user/podaci_id/{id}")
    Call<List<UserModel>> getUserId(@Path("id") int id);

}
