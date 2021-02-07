package android.unipu.cinema.retrofit;

import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.MovieModel;

import java.util.List;
import java.util.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMojIzbor {

    @POST("mojIzbor/insert")
    @FormUrlEncoded
    Call<List<IzborModel>> insertIzbor(@Field("datum_dodavanja") String datum_dodavanja,
                                       @Field("id_korisnik") int id_korisnik,
                                       @Field("id_film") int id_film);

    @GET("mojIzbor/provjeraFilma/{id_korisnik}/{id_film}")
    io.reactivex.Observable<Integer> provjeraIzbora(@Path("id_korisnik") int id_korisnik,
                              @Path("id_film") int id_film);

    @DELETE("mojIzbor/deleteIzbor/{id_korisnik}/{id_film}")
    Call<ResponseBody> deleteIzbor(@Path("id_korisnik") int id_korisnik,
                                  @Path("id_film") int id_film);

    @GET("mojIzbor/izborDohvat/{id_korisnik}")
    Call<List<MovieModel>> dohvatIzbora(@Path("id_korisnik") int id_korisnik);

    @GET("mojIzbor/dohvatPoDatumu/{id_korisnik}/{datum_dodavanja}")
    Call<List<MovieModel>> dohvatDatum(@Path("id_korisnik") int id_korisnik,
                                    @Path("datum_dodavanja") String datum_dodavanja);

}


