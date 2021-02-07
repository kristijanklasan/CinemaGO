package android.unipu.cinema.retrofit;

import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.KartaModel;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.RasporedFilmaModel;
import android.unipu.cinema.model.RezervacijaModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRezervacija {

    @GET("Raspored_filmova/dohvatDatum/{datum_prikazivanja}")
    Call<List<RasporedFilmaModel>> dohvatPoDatumu(@Path("datum_prikazivanja") String datum_prikazivanja);

    @GET("Raspored_filmova/dohvatNaziv/{datum_prikazivanja}/{id_filma}")
    Call<List<RasporedFilmaModel>> dohvatPoNazivu(@Path("datum_prikazivanja") String datum_prikazivanja,
                                                  @Path("id_filma") int id_filma);

    @GET("Film/dohvatId/{naziv}")
    Call<List<MovieModel>> dohvatId(@Path("naziv") String naziv);


    @GET("Raspored_filmova/dohvatiSve/{datum_prikazivanja}/{id_filma}/{vrijeme_prikazivanja}")
    Call<List<RasporedFilmaModel>> dohvatiSve(@Path("datum_prikazivanja") String datum_prikazivanja,
                                              @Path("id_filma") int id_filma,
                                              @Path("vrijeme_prikazivanja") String vrijeme_prikazivanja);

    @POST("Karta/insert")
    @FormUrlEncoded
    Call<ResponseBody> insertRezervacija(@Field("ime") String ime,
                                         @Field("prezime") String prezime,
                                         @Field("cijena") double cijena,
                                         @Field("red") int red,
                                         @Field("sjedalo") int sjedalo,
                                         @Field("oznaka") int oznaka,
                                         @Field("ukupno") int ukupno,
                                          @Field("id_raspored") int id_raspored,
                                         @Field("id_korisnik") int id_korisnik);

    @POST("Raspored_filmova/update")
    @FormUrlEncoded
    Call<List<RasporedFilmaModel>> updateRezervacija(@Field("id") int id,
                                              @Field("trenutno_ulaznica") int trenutno_ulaznica);

    @GET("Karta/dohvatiSve/{datum_prikazivanja}/{id_filma}/{vrijeme_prikazivanja}/{id}")
    Call<List<KartaModel>> dohvatiRezervaciju (@Path("datum_prikazivanja") String datum_prikazivanja,
                                               @Path("id_filma") int id_filma,
                                               @Path("vrijeme_prikazivanja") String vrijeme_prikazivanja,
                                               @Path("id") int id);

    @GET("Karta/ponudaDatum/{datum_prikazivanja}/{id_korisnik}")
    Call<List<RezervacijaModel>> ponudaDatum (@Path("datum_prikazivanja") String datum_prikazivanja,
                                              @Path("id_korisnik") int id_korisnik);

    @GET("Karta/sveRezervacije/{id_korisnik}")
    Call<List<RezervacijaModel>> sveRezervacije(@Path("id_korisnik") int id_korisnik);

    @GET("Karta/dohvatiRezervaciju/{id}")
    Call<List<RezervacijaModel>> dohvatiRezervacijuId(@Path("id") int id);

    @DELETE("Karta/deleteRezervacija/{id}")
    Call<ResponseBody> deleteRezervacija(@Path("id") int id);
}
