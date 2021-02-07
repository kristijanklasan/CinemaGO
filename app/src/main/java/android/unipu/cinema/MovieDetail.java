package android.unipu.cinema;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.unipu.cinema.fragmenti.IzborFragment;
import android.unipu.cinema.fragmenti.MovieFragment;
import android.unipu.cinema.fragmenti.RezervacijaKarata;
import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.RasporedFilmaModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IMojIzbor;
import android.unipu.cinema.retrofit.IMovie;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MovieDetail extends AppCompatActivity {

    private TextView textNaslov, straniText, zanrText, opisText,
            redateljText, glumciText, drzavaText, audioText, titloviText, trajanjeText,textOcjena,
            godinaText;

    CompositeDisposable compositeDisposible = new CompositeDisposable();
    private ImageView imagePozadina,imagePoster;
    private ImageButton btnIzbor;
    private IMovie iMovie;
    private IRegistration iRegistration;
    private MovieModel movieModel = new MovieModel();
    private IMojIzbor iIzbor;
    private UserModel user = new UserModel();
    private String start;
    private String datum = "datum";
    private int id_filma, id_korisnik;
    private Boolean flag = false;
    private ListView listaFilm;
    private CheckBox checkBoxRaspored;
    private FrameLayout frameRaspored;
    private TextView text1,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        start = user.getEmail();
        String id_korisnika= user.getKljuc();
        id_korisnik = Integer.parseInt(id_korisnika);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        datum = simpledateformat.format(calendar.getTime());

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iIzbor = retrofitClient.create(IMojIzbor.class);

        godinaText = (TextView) findViewById(R.id.godinaText);
        frameRaspored = (FrameLayout) findViewById(R.id.frameRaspored);
        checkBoxRaspored = (CheckBox) findViewById(R.id.checkBoxRaspored);
        listaFilm  = (ListView) findViewById(R.id.listaFilm);
        final View parentLayout = findViewById(R.id.relative_layoutMovieDetail);
        textNaslov = (TextView) findViewById(R.id.naslov_filma);
        straniText = (TextView) findViewById(R.id.straniNazivText);
        zanrText = (TextView) findViewById(R.id.zanrText);
        opisText = (TextView) findViewById(R.id.opisText);
        redateljText = (TextView) findViewById(R.id.redateljText);
        glumciText = (TextView) findViewById(R.id.glumciText);
        drzavaText = (TextView) findViewById(R.id.drzavaText);
        audioText = (TextView) findViewById(R.id.audioText);
        titloviText = (TextView) findViewById(R.id.titloviText);
        trajanjeText = (TextView) findViewById(R.id.trajanjeText);
        imagePozadina = (ImageView) findViewById(R.id.slikaPozadina);
        imagePoster = (ImageView) findViewById(R.id.slikaPoster);
        textOcjena = (TextView) findViewById(R.id.ocjenaIMBd);
        btnIzbor = (ImageButton) findViewById(R.id.btnMojIzbor);

        final Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        id_filma = id;
        getDetalji(id);
        rasporedFilmova(id_filma);
        provjeraIzbora(id_korisnik,id_filma);

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        listaFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String option = (listaFilm.getItemAtPosition(position).toString().trim());
                text1 = (TextView) view.findViewById(android.R.id.text1);
                text2 = (TextView) view.findViewById(android.R.id.text2);

                ArrayList<String> lista = new ArrayList<>();
                lista.add(text1.getText().toString());
                lista.add(movieModel.getFilm());
                lista.add(text2.getText().toString());
                lista.add(String.valueOf(movieModel.getId()));

                MovieModel movie = new MovieModel();
                movie.setMovieList(lista);

                Intent i = new Intent(MovieDetail.this, RezervacijaKarataActivity.class);
                startActivity(i);
            }
        });

        btnIzbor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int izbor = Color.WHITE;
                ColorStateList white = ColorStateList.valueOf(Color.WHITE);
                ColorStateList temp = btnIzbor.getBackgroundTintList();

                if(movieModel.getPotvrda() == true){

                    btnIzbor.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                    deleteIzbor(id_korisnik, id_filma);
                    Snackbar snackbar = Snackbar
                            .make(parentLayout, "Obrisano iz Mojeg izbora!", Snackbar.LENGTH_LONG)
                            .setAction("OTVORI", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(MovieDetail.this, RezervacijeActivity.class);
                                    startActivity(i);
                                }
                            });
                    snackbar.show();
                }
                else if(movieModel.getPotvrda() == false){
                    btnIzbor.setBackgroundTintList(ColorStateList.valueOf(izbor));
                    insertIzbor(datum, id_korisnik, id_filma);
                    Snackbar snackbar = Snackbar
                            .make(parentLayout, "Dodano u Moj izbor!", Snackbar.LENGTH_LONG)
                            .setAction("OTVORI", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(MovieDetail.this, RezervacijeActivity.class);
                                    startActivity(i);
                                }
                            });
                    snackbar.show();
                }
                provjeraIzbora(id_korisnik,id_filma);
            }
        });

        imagePozadina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MovieDetail.this,TrailerPlayer.class);
                intent.putExtra("trailer", movieModel.getTrailer());
                startActivity(intent);
            }
        });


        checkBoxRaspored.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBoxRaspored.isChecked()){
                    frameRaspored.setVisibility(View.VISIBLE);
                }else{
                    frameRaspored.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStop(){
        compositeDisposible.clear();
        super.onStop();
    }

    private void getDetalji(int id){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iMovie = retrofitClient.create(IMovie.class);

        Call<List<MovieModel>> call = iMovie.filmDetalji(id);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                List<MovieModel> adslist = response.body();

                for(int i=0; i<adslist.size(); i++) {

                    String imageUrl = adslist.get(i).getSlikaPozadina();
                    Picasso.get().load(imageUrl).fit().into(imagePozadina);

                    String imageUrlPoster = adslist.get(i).getSlikaFilm();
                    Picasso.get().load(imageUrlPoster).fit().into(imagePoster);

                    movieModel.setId(adslist.get(i).getId());
                    textNaslov.setText(adslist.get(i).getFilm());
                    movieModel.setFilm(adslist.get(i).getFilm());

                    textOcjena.setText("IMDb: "+adslist.get(i).getOcjena());
                    straniText.setText(adslist.get(i).getStraniNaziv());
                    zanrText.setText(adslist.get(i).getZanr());
                    opisText.setText(adslist.get(i).getOpis());
                    redateljText.setText(adslist.get(i).getRedatelji());
                    glumciText.setText(adslist.get(i).getGlumci());
                    drzavaText.setText(adslist.get(i).getDrzava());
                    audioText.setText(adslist.get(i).getAudio());
                    titloviText.setText(adslist.get(i).getTitlovi());
                    trajanjeText.setText(adslist.get(i).getTrajanje());
                    id_filma = adslist.get(i).getId();
                    godinaText.setText(String.valueOf(adslist.get(i).getGodina()));

                    movieModel.setTrailer(adslist.get(i).getTrailer());
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
            }
        });
    }

    private void insertIzbor(String datum, int id_korisnika, int id_filma){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iIzbor = retrofitClient.create(IMojIzbor.class);

        Call<List<IzborModel>> call = iIzbor.insertIzbor(datum,id_korisnika, id_filma);
        call.enqueue(new Callback<List<IzborModel>>() {
            @Override
            public void onResponse(Call<List<IzborModel>> call, Response<List<IzborModel>> response) {

                if (response.equals("true")) {
                    Toast.makeText(getApplicationContext(), " uspjesno uspjesno" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<IzborModel>> call, Throwable t) {
            }
        });
    }

    private void provjeraIzbora(int id_korisnika, int id_filma){

        compositeDisposible.add(iIzbor.provjeraIzbora(id_korisnika, id_filma)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer response) throws Exception {

                        if(response == 0){
                            movieModel.setPotvrda(true);
                            btnIzbor.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                            flag = true;

                        }
                        else if(response > 0) {
                            movieModel.setPotvrda(false);
                            btnIzbor.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                            flag = false;
                        }
                    }
                })

        );
    }

    private void deleteIzbor(int id_korisnika, int id_film){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iIzbor = retrofitClient.create(IMojIzbor.class);

        Call<ResponseBody> deleteRequest = iIzbor.deleteIzbor(id_korisnika, id_film);
        deleteRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String result = String.valueOf(response.body());

                if(result.equals("true")){
                    Toast.makeText(getApplicationContext(),"Izbor je izbrisan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void rasporedFilmova(int id_film){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iMovie = retrofitClient.create(IMovie.class);

        Call<List<RasporedFilmaModel>> rasporedRequest = iMovie.dohvatRasporedaFilmova(id_film);
        rasporedRequest.enqueue(new Callback<List<RasporedFilmaModel>>(){

            @Override
            public void onResponse(Call<List<RasporedFilmaModel>> call, Response<List<RasporedFilmaModel>> response) {

                List<RasporedFilmaModel> lista = response.body();

                List<Map<String, String>> listArray = new ArrayList<>();
                for(int i=0; i<lista.size(); i++){
                    HashMap<String, String> raspored = new HashMap<String, String>();

                    try {
                        String requiredDate = convertToDate(lista.get(i).getDatum_prikazivanja());
                        raspored.put("datum", requiredDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    raspored.put("vrijeme",lista.get(i).getVrijeme_prikazivanja());
                    listArray.add(raspored);
                }

                SimpleAdapter adapter = new SimpleAdapter (getApplication(), listArray,
                        android.R.layout.simple_list_item_2, new String[] {"datum", "vrijeme"},
                        new int[] {android.R.id.text1, android.R.id.text2 }){

                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        text1 = (TextView) view.findViewById(android.R.id.text1);
                        text1.setTextColor(Color.WHITE);

                        text2 = (TextView) view.findViewById(android.R.id.text2);
                        text2.setTextColor(Color.parseColor("#807E7E"));
                        return view;
                    };
                };;
                listaFilm.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RasporedFilmaModel>> call, Throwable t) {
            }
        });
    }

    String convertToDate(String receivedDate) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(receivedDate);

        formatter = new SimpleDateFormat("E, dd.MM.yyyy");
        String formattedDate2 = formatter.format(date);

        //String strDate = formatter.format(date);
        return  formattedDate2;
    }
}
