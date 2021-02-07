package android.unipu.cinema.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import android.unipu.cinema.MovieDetail;
import android.unipu.cinema.model.*;

import java.util.List;

public interface IMovie {

    @GET("film/filmDetalji/{id}")
    Call<List<MovieModel>> filmDetalji(@Path("id") int id);

    @GET("film/kategorije/{zanr}")
    Call<List<MovieModel>> filmoviKategorije(@Path("zanr") String zanr);

    @GET("Raspored_filmova/dohvatFilma/{id_filma}")
    Call<List<RasporedFilmaModel>> dohvatRasporedaFilmova(@Path("id_filma") int id_filma);

    @GET("Film/dohvat_preporuka")
    Call<List<MovieModel>> dohvatFilmova();
}
