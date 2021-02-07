package android.unipu.cinema.retrofit;

import android.unipu.cinema.model.CijeneModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICijene {

    @GET("Cijene/dohvatCijena")
    Call<List<CijeneModel>> dohvatCijene();
}
