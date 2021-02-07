package android.unipu.cinema.retrofit;

import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.PitanjaModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IPitanja {

    @GET("Najcesca_pitanja/dohvatPitanja")
    Call<List<PitanjaModel>> dohvatPitanja();

    @POST("Upit/insert")
    @FormUrlEncoded
    Call<List<PitanjaModel>> insertUpit(@Field("pitanje") String pitanje,
                                       @Field("prijedlog") String prijedlog,
                                       @Field("id_korisnik") int id_korisnik);
}
