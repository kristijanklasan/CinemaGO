package android.unipu.cinema.fragmenti;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.RezervacijaSjedala;
import android.unipu.cinema.model.CijeneModel;
import android.unipu.cinema.model.KartaModel;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.RasporedFilmaModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.ICijene;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.IRezervacija;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.unipu.cinema.startCinema;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RezervacijaKarata extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CheckBox checkStudenti, checkDjeca, checkOdrasli;
    private IRegistration iRegistration;
    private String mParam1;
    private String mParam2;
    private Spinner spinFilm,spinVrijeme;
    private int id_filma;
    private String [] l;
    private List<String> cijene;
    private ICijene iCijene;
    private IRezervacija iRezervacija;
    private CijeneModel model;
    private Button btnOdabir_sjedala;
    private DatePickerDialog.OnDateSetListener mDatePicker;
    private TextView textDatum;
    private String formatDate, format;
    private TextView textFilm,textDatumPrikazivanja, textVrijeme, textMaxUlaznica, textTrenutnoUlaznica, textDvorana;
    private TextView textImeKorisnik, textPrezimeKorisnik, textUkupno, textRed, textSjedalo, textCijena;
    private CheckBox checkBoxUvjeti;
    private RasporedFilmaModel modelRaspored = new RasporedFilmaModel();
    private KartaModel modelKarta;
    private MovieModel movieModel = new MovieModel();
    public RezervacijaKarata() {
        // Required empty public constructor
    }

    public static RezervacijaKarata newInstance(String param1, String param2) {
        RezervacijaKarata fragment = new RezervacijaKarata();
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

        View view = inflater.inflate(R.layout.fragment_rezervacija_karata, container, false);

        modelKarta = new KartaModel();

        //Podaci o filmu
        textDatumPrikazivanja = (TextView) view.findViewById(R.id.textDatum);
        textFilm = (TextView) view.findViewById(R.id.textFilm);
        textVrijeme = (TextView) view.findViewById(R.id.textVrijeme);
        textMaxUlaznica = (TextView) view.findViewById(R.id.textMaxUlaznica);
        textTrenutnoUlaznica = (TextView) view.findViewById(R.id.textTrenutnoUlaznica);
        textDvorana = (TextView)  view.findViewById(R.id.textDvorana);

        //

        // Podaci o korisniku - za kartu
        textImeKorisnik = (TextView) view.findViewById(R.id.textIme);
        textPrezimeKorisnik = (TextView) view.findViewById(R.id.textPrezime);
        textCijena = (TextView) view.findViewById(R.id.textCijena);
        textRed = (TextView) view.findViewById(R.id.textRed);
        textSjedalo = (TextView) view.findViewById(R.id.textSjedalo);
        textUkupno = (TextView) view.findViewById(R.id.textUkupno);
        checkBoxUvjeti = (CheckBox) view.findViewById(R.id.checkBoxUvjeti);
        //

        popuniSjedala();
        textDatum = (TextView) view.findViewById(R.id.textDatumKarta);
        model = new CijeneModel();
        cijene = new ArrayList<>();
        checkDjeca = (CheckBox) view.findViewById(R.id.checkBoxDjeca);
        checkOdrasli = (CheckBox) view.findViewById(R.id.checkBoxOdrasli);
        checkStudenti = (CheckBox) view.findViewById(R.id.checkBoxStudenti);
        spinFilm = (Spinner) view.findViewById(R.id.spinnerFilm);
        spinVrijeme = (Spinner) view.findViewById(R.id.spinnerVrijeme);
        btnOdabir_sjedala = (Button) view.findViewById(R.id.btnOdabir_sjedala);

        try {
            ArrayList<String> lista = new ArrayList();
            lista = movieModel.getMovieList();

            if(lista != null){
                ArrayList<String> film = new ArrayList<>();
                film.add(lista.get(1).toString());

                ArrayList<String> vrijeme = new ArrayList<>();
                vrijeme.add(lista.get(2).toString());

                spinnerFilm((ArrayList<String>) film);
                spinnerVrijeme(vrijeme);

                textDatum.setText(lista.get(0).toString());
                String datum = datum(lista.get(0));
                modelRaspored.setDatum_prikazivanja(datum);

                int id = Integer.parseInt(lista.get(3));
                dohvatiSve(datum, id, lista.get(2).toString());

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Toolbar toolbar = view.findViewById(R.id.toolbarKarte);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), startCinema.class);
                startActivity(i);
            }
        });

        setCijene();
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageDatumKarta);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDatePicker,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDatePicker = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker picker, int year, int month, int day){
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);

                SimpleDateFormat simple = new SimpleDateFormat("E, dd.MM.yyyy");
                formatDate = simple.format(calendar.getTime());
                textDatum.setText(formatDate);
                String datum = textDatum.getText().toString();

                // Za drugaciji formatirani datum
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                format = formatter.format(calendar.getTime());
                modelRaspored.setDatum_prikazivanja(format);
                pretraziDatum(format);
            }
        };


        checkDjeca.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkDjeca.isChecked()){
                    checkOdrasli.setChecked(false);
                    checkStudenti.setChecked(false);
                    popuniPodatke(model.getCijene_djeca());
                }
            }
        });

        checkOdrasli.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkOdrasli.isChecked()){
                    checkDjeca.setChecked(false);
                    checkStudenti.setChecked(false);
                    popuniPodatke(model.getCijene_odrasli());
                }
            }
        });

        checkStudenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkStudenti.isChecked()){
                    checkDjeca.setChecked(false);
                    checkOdrasli.setChecked(false);
                    popuniPodatke(model.getCijene_studenti());
                }
            }
        });

        btnOdabir_sjedala.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dohvatRezervaciju();
                popuniSjedala();
                Intent i = new Intent(getContext(), RezervacijaSjedala.class);
                startActivity(i);
            }
        });

        final UserModel user = new UserModel();
        Button btnRezerviraj = (Button) view.findViewById(R.id.btnRezervacija);
        btnRezerviraj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String kljuc = user.getKljuc();
                rezervirajKartu(kljuc);
            }
        });
        return view;
    }

    private void spinnerFilm(ArrayList lista){

        String[] l = (String[]) lista.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, l);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinFilm.setAdapter(adapter);

        spinFilm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                if(parent.getItemAtPosition(position).equals("")){
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    getFilmId(item);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void spinnerVrijeme(ArrayList lista){

        String[] l = (String[]) lista.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, l);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinVrijeme.setAdapter(adapter);

        spinVrijeme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                if(parent.getItemAtPosition(position).equals("")){
                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    modelRaspored.setVrijeme_prikazivanja(item);
                    dohvatiSve(format, id_filma, item);
                    dohvatRezervaciju();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCijene(){
        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iCijene = retrofitClient.create(ICijene.class);

        Call<List<CijeneModel>> call = iCijene.dohvatCijene();
        call.enqueue(new Callback<List<CijeneModel>>() {
            @Override
            public void onResponse(Call<List<CijeneModel>> call, Response<List<CijeneModel>> response) {

                if (response.isSuccessful()) {
                    List<CijeneModel> list = response.body();

                    for(int i=0; i<list.size(); i++){
                        model.setCijene_djeca(list.get(i).getCijene_djeca());
                        model.setCijene_odrasli(list.get(i).getCijene_odrasli());
                        model.setCijene_studenti(list.get(i).getCijene_studenti());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CijeneModel>> call, Throwable t) {
            }
        });
    }

    private void pretraziDatum(String datum_prikazivanja){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<RasporedFilmaModel>> call = iRezervacija.dohvatPoDatumu(datum_prikazivanja);
        call.enqueue(new Callback<List<RasporedFilmaModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<RasporedFilmaModel>> call, Response<List<RasporedFilmaModel>> response) {

                ArrayList<String> sviFilmovi = new ArrayList<>();
                 if (response.isSuccessful()) {
                    List<RasporedFilmaModel> list = response.body();

                    if(list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            sviFilmovi.add(list.get(i).getFilm());
                        }
                        ArrayList uniqueList = (ArrayList) sviFilmovi.stream().distinct().collect(Collectors.toList());
                        spinnerFilm((ArrayList) uniqueList);
                    }
                    else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Upozorenje")
                                .setContentText("Nema filmova pod traženim datumom!")
                                .show();

                        spinFilm.setAdapter(null);
                        spinVrijeme.setAdapter(null);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RasporedFilmaModel>> call, Throwable t) {
            }
        });
    }

    private void getFilmId(String naziv){
        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<MovieModel>> call = iRezervacija.dohvatId(naziv);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {

                if (response.isSuccessful()) {
                    List<MovieModel> list = response.body();

                    for(int i=0; i<list.size(); i++){
                        id_filma = list.get(i).getId();
                        modelRaspored.setId_filma(id_filma);
                        dohvatNaziv(format, list.get(i).getId());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
            }
        });
    }

    private void dohvatNaziv(String format, int id_filma){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<RasporedFilmaModel>> call = iRezervacija.dohvatPoNazivu(format, id_filma);
        call.enqueue(new Callback<List<RasporedFilmaModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<RasporedFilmaModel>> call, Response<List<RasporedFilmaModel>> response) {

                ArrayList<String> sviFilmovi = new ArrayList<>();

                if (response.isSuccessful()) {
                    List<RasporedFilmaModel> list = response.body();
                    if(list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            sviFilmovi.add(list.get(i).getVrijeme_prikazivanja());
                        }
                        spinnerVrijeme((ArrayList) sviFilmovi);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RasporedFilmaModel>> call, Throwable t) {
            }
        });
    }

    private void dohvatiSve(String format, int id_filma, String vrijeme_prikazivanja){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<RasporedFilmaModel>> call = iRezervacija.dohvatiSve(format, id_filma, vrijeme_prikazivanja);
        call.enqueue(new Callback<List<RasporedFilmaModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<RasporedFilmaModel>> call, Response<List<RasporedFilmaModel>> response) {

                if (response.isSuccessful()) {
                    List<RasporedFilmaModel> list = response.body();

                    if(list != null) {
                        for (int i = 0; i < list.size(); i++) {

                            int id_ponuda = list.get(i).getId();
                            modelRaspored.setId(id_ponuda);

                            textFilm.setText(list.get(i).getFilm());
                            String datum = prikazDatuma(list.get(i).getDatum_prikazivanja());
                            textDatumPrikazivanja.setText(datum);
                            textVrijeme.setText(list.get(i).getVrijeme_prikazivanja());

                            textMaxUlaznica.setText(list.get(i).getMax_ulaznica());
                            textTrenutnoUlaznica.setText(list.get(i).getTrenutno_ulaznica());
                            textDvorana.setText(list.get(i).getDvorana());
                            modelRaspored.setId(list.get(i).getId());
                            modelRaspored.setTemp_ulaznica(Integer.parseInt(list.get(i).getTrenutno_ulaznica()));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RasporedFilmaModel>> call, Throwable t) {
            }
        });
    }

    private String prikazDatuma(String datum){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = input.parse(datum);
            SimpleDateFormat simple = new SimpleDateFormat("E, dd.MM.yyyy");
            String finalDateString = simple.format(convertedDate);
            return finalDateString;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void popuniPodatke(int cijena){
        UserModel user = new UserModel();
        String kljuc = user.getKljuc();

        getUserData(kljuc);
        textCijena.setText(String.valueOf(cijena));
        textUkupno.setText(String.valueOf(cijena)+" kn");
        popuniSjedala();
    }

    private void popuniSjedala(){
        int red = modelKarta.getRed();

        if(red == 0){
            textRed.setText("1");
            textSjedalo.setText("1");

        }
        else{
            textRed.setText(String.valueOf(modelKarta.getRed()));
            textSjedalo.setText(String.valueOf(modelKarta.getSjedalo()));
        }

    }

    void getUserData(String kljuc){

        int id = Integer.parseInt(kljuc);
        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRegistration = retrofitClient.create(IRegistration.class);

        Call<List<UserModel>> call = iRegistration.getUserId(id);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                List<UserModel> lista = response.body();

                for(int i=0; i<lista.size(); i++) {
                    textImeKorisnik.setText(lista.get(i).getIme());
                    textPrezimeKorisnik.setText(lista.get(i).getPrezime());
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
            }
        });
    }


    private void rezervirajKartu(String kljuc_rijec){

        int kljuc = Integer.parseInt(kljuc_rijec);
        int id_ponuda = modelRaspored.getId();
        double cijena = Double.parseDouble(textCijena.getText().toString());
        int ukupno = Integer.parseInt(textCijena.getText().toString());
        int trenutno_ulaznica = modelRaspored.getTemp_ulaznica();
        int red = modelKarta.getRed();
        int sjedalo = modelKarta.getSjedalo();
        int oznaka = modelKarta.getOznaka2();

        if(checkBoxUvjeti.isChecked() && trenutno_ulaznica>0){
            if(!textImeKorisnik.equals("") && !textPrezimeKorisnik.equals("") &&
                    !textCijena.equals("") && !textRed.equals("") && !textSjedalo.equals("")){

                String ime = textImeKorisnik.getText().toString();
                String prezime = textPrezimeKorisnik.getText().toString();

                Retrofit retrofitClient = RetrofitClient.getInstance();
                iRezervacija = retrofitClient.create(IRezervacija.class);

                Call<ResponseBody>call= iRezervacija.insertRezervacija(ime, prezime, cijena, red,sjedalo,oznaka, ukupno, id_ponuda, kljuc);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        updateKarta();

                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Uspješno")
                                .setContentText("Uspješno ste rezervirali kartu!")
                                .show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }
        }
    }

    private void updateKarta(){
        int id_raspored = modelRaspored.getId();
        int temp_ulaznica = modelRaspored.getTemp_ulaznica();
        int novo = temp_ulaznica - 1;

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<RasporedFilmaModel>>call= iRezervacija.updateRezervacija(id_raspored,novo);
        call.enqueue(new Callback<List<RasporedFilmaModel>>() {
            @Override
            public void onResponse(Call<List<RasporedFilmaModel>> call, Response<List<RasporedFilmaModel>> response) {

            }

            @Override
            public void onFailure(Call<List<RasporedFilmaModel>> call, Throwable t) {

            }
        });
    }

    private void dohvatRezervaciju(){

        int id_filma = modelRaspored.getId_filma();
        int id = modelRaspored.getId();
        String vrijeme = modelRaspored.getVrijeme_prikazivanja();
        String datum_prikazivanja = modelRaspored.getDatum_prikazivanja();

        //Toast.makeText(getContext(),""+datum_prikazivanja+" "+vrijeme+" "+id_filma+" "+id,Toast.LENGTH_SHORT).show();

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<KartaModel>> call = iRezervacija.dohvatiRezervaciju(datum_prikazivanja,id_filma,vrijeme,id);
        call.enqueue(new Callback<List<KartaModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<KartaModel>> call, Response<List<KartaModel>> response) {

                ArrayList<Integer> svaSjedala = new ArrayList<>();
                List<KartaModel> list = response.body();

                if(list!=null) {
                    for (int i = 0; i < list.size(); i++) {
                        svaSjedala.add(list.get(i).getOznaka());
                    }
                    KartaModel karta = new KartaModel();
                    karta.setLista(svaSjedala);
                }

                else{
                    KartaModel karta = new KartaModel();
                    svaSjedala.add(0);
                    karta.setLista(svaSjedala);
                }
            }
            @Override
            public void onFailure(Call<List<KartaModel>> call, Throwable t) {
            }
        });
    }

    String datum(String receivedDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("E, dd.MM.yyyy");
        Date date = format.parse(receivedDate);

        format  = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate2 = format.format(date);

        return  formattedDate2;
    }
}
