package android.unipu.cinema;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.adapter.AdapterPoster;
import android.unipu.cinema.fragmenti.MovieFragment;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IMovie;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class startCinema extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> naslov;
    ArrayList<String> slikaItem;
    ArrayList<String> zanr;
    ArrayList<String> trajanje;
    ArrayList<Double> ocjena;
    ArrayList<Integer> id;
    private AdapterPoster adapterPoster;
    private IRegistration iRegistration;
    private IMovie iMovie;
    public UserModel user = new UserModel();
    private static String kljuc;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_cinema);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        getData(email);
        getCurrentId(email);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,
                drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRegistration = retrofitClient.create(IRegistration.class);

        zanr = new ArrayList<>();
        slikaItem = new ArrayList<>();
        naslov = new ArrayList<>();
        trajanje = new ArrayList<>();
        ocjena = new ArrayList<>();
        id = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Call<List<MovieModel>> call = iRegistration.dohvatFilma();

        iMovie = retrofitClient.create(IMovie.class);
        Call<List<MovieModel>> call = iMovie.dohvatFilmova();
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                List<MovieModel> adslist = response.body();

                for(int i=0; i<adslist.size(); i++) {
                    slikaItem.add(""+adslist.get(i).getSlikaPozadina());
                    naslov.add("" + adslist.get(i).getFilm());
                    /*
                    zanr.add(""+adslist.get(i).getZanr());
                    trajanje.add(""+adslist.get(i).getTrajanje());
                    String ocjenaFilm = ""+adslist.get(i).getOcjena();
                    ocjena.add(Double.parseDouble(ocjenaFilm));

                     */
                    id.add(adslist.get(i).getId());
                }
                adapterPoster = new AdapterPoster(startCinema.this, slikaItem, naslov, id);
                recyclerView.setAdapter(adapterPoster);
                recyclerView.scheduleLayoutAnimation();
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch(menuItem.getItemId()){
            case R.id.korisnickiRacun:{

                Intent i = new Intent(startCinema.this, AccountActivity.class);
                startActivity(i);

            }break;

            case R.id.rezervirajKartu:{
                Intent i = new Intent(startCinema.this, RezervacijaKarataActivity.class);
                startActivity(i);
            }break;

            case R.id.pregledFilmova:{
                Intent i = new Intent(startCinema.this, MoviesActivity.class);
                startActivity(i);
            }break;

            case R.id.informacijeCinema: {
                Intent i = new Intent(startCinema.this, InformationActivity.class);
                startActivity(i);
            }break;

            case R.id.pregledRezervacija: {
                Intent i = new Intent(startCinema.this, RezervacijeActivity.class);
                startActivity(i);
            }break;

            case R.id.cijeneUlaznica: {
                Intent i = new Intent(startCinema.this, CijenaKarata.class);
                startActivity(i);
            }break;

            case R.id.najcescaPitanja:{
                Intent i = new Intent(startCinema.this, PitanjaActivity.class);
                startActivity(i);
            }break;

            case R.id.odjava:{
                Intent i = new Intent(startCinema.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }break;
        }
        return true;
    }

    public void getData(String email){
        user.setEmail(email);
    }

    private void getCurrentId(String email) {

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRegistration = retrofitClient.create(IRegistration.class);

        Call<List<UserModel>> call = iRegistration.getUser(email);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> list = response.body();

                for (int i = 0; i < list.size(); i++) {
                    kljuc = list.get(i).getId();
                }
                user.setKljuc(kljuc);
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
            }
        });
    }
}
