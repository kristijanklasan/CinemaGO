package android.unipu.cinema.fragmenti;

import android.opengl.Visibility;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.unipu.cinema.Adapter;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.retrofit.IMovie;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.unipu.cinema.R;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.unipu.cinema.*;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IMovie iMovie;
    private RecyclerView recyclerView;
    private Adapter adapter;
    ArrayList<String> naslov;
    ArrayList<String> slikaItem;
    ArrayList<String> zanr;
    ArrayList<String> trajanje;
    ArrayList<Double> ocjena;
    ArrayList<Integer> id;
    private TextView textKategorija;
    private TextView textFilmovi;

    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        textFilmovi = (TextView) view.findViewById(R.id.textFilmovi);
        textKategorija = (TextView) view.findViewById(R.id.kategorijaFragment);
        String kategorija = getArguments().getString("kategorija","defaultValue");
        textKategorija.setText("Kategorija: "+ kategorija);

        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iMovie = retrofitClient.create(IMovie.class);

        getMovies(kategorija);

        return view;
    }

    private void getMovies(String kategorija){
        zanr = new ArrayList<>();
        slikaItem = new ArrayList<>();
        naslov = new ArrayList<>();
        trajanje = new ArrayList<>();
        ocjena = new ArrayList<>();
        id = new ArrayList<>();

        Call<List<MovieModel>> call = iMovie.filmoviKategorije(kategorija);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                List<MovieModel> filmovi = response.body();

                if(!filmovi.isEmpty()){
                    textFilmovi.setVisibility(View.GONE);
                }
                else {
                    textFilmovi.setVisibility(View.VISIBLE);
                }

                for(int i=0; i<filmovi.size(); i++) {
                    slikaItem.add(""+filmovi.get(i).getSlikaFilm());
                    naslov.add("" + filmovi.get(i).getFilm());
                    zanr.add(""+filmovi.get(i).getZanr());
                    trajanje.add(""+filmovi.get(i).getTrajanje());
                    String ocjenaFilm = ""+filmovi.get(i).getOcjena();
                    ocjena.add(Double.parseDouble(ocjenaFilm));
                    id.add(filmovi.get(i).getId());
                }

                adapter = new Adapter(getContext(),slikaItem, naslov, zanr,trajanje, ocjena,id);
                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                textFilmovi.setVisibility(TextView.VISIBLE);
            }
        });
    }
}
