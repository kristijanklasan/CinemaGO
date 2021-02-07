package android.unipu.cinema.fragmenti;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.adapter.AdapterPoster;
import android.unipu.cinema.adapter.AdapterRezervacija;
import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.RezervacijaModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IMojIzbor;
import android.unipu.cinema.retrofit.IRezervacija;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.unipu.cinema.startCinema;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RezervacijaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2, formatDate;
    private Switch switchDatum;
    private  int id_korisnika;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private ArrayList slikaItem, naslov, id,datum,vrijeme;
    private UserModel user = new UserModel();
    private AdapterRezervacija adapterRezervacija;
    private TextView textDatum, warningRezervacija;
    private DatePickerDialog.OnDateSetListener mDatePicker;
    private IRezervacija iRezervacija;


    public RezervacijaFragment() {
        // Required empty public constructor
    }

    public static RezervacijaFragment newInstance(String param1, String param2) {
        RezervacijaFragment fragment = new RezervacijaFragment();
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

        View view = inflater.inflate(R.layout.fragment_rezervacija, container, false);

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Toolbar toolbar = view.findViewById(R.id.toolbarRezervacija);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        slikaItem = new ArrayList();
        naslov = new ArrayList();
        id = new ArrayList();
        datum = new ArrayList();
        vrijeme = new ArrayList();

        warningRezervacija = (TextView) view.findViewById(R.id.warningRezervacija);
        imageButton = (ImageButton) view.findViewById(R.id.imageDatumRezervacija);
        textDatum = (TextView) view.findViewById(R.id.textDatumRezervacija);
        switchDatum = (Switch) view.findViewById(R.id.switchRezervacija);
        recyclerView = view.findViewById(R.id.recyclerViewRezervacija);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id_korisnika = Integer.parseInt(user.getKljuc());
        final FrameLayout frame = (FrameLayout) view.findViewById(R.id.frameLayoutRezervacija);

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearRecycler();
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

                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
                formatDate = simple.format(calendar.getTime());
                textDatum.setText(formatDate);

                String datum = textDatum.getText().toString();
                ponudaDatum(id_korisnika,datum);
                Toast.makeText(getContext(),""+id_korisnika,Toast.LENGTH_SHORT).show();
            }
        };

        setRezervacija(id_korisnika);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), startCinema.class);
                startActivity(i);
            }
        });

        switchDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.clearOnChildAttachStateChangeListeners();
                warningRezervacija.setVisibility(View.GONE);

                if(switchDatum.isChecked()) {
                    frame.setVisibility(View.VISIBLE);
                }
                else {
                    clearRecycler();
                    frame.setVisibility(View.GONE);
                    setRezervacija(id_korisnika);
                }
            }
        });

        return view;
    }

    private void clearRecycler(){
        slikaItem.clear();
        naslov.clear();
        id.clear();
        datum.clear();
        vrijeme.clear();

        if(recyclerView.getAdapter() != null)
            recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void ponudaDatum(int id_korisnik, String datum_dodavanja){

        Call<List<RezervacijaModel>> call = iRezervacija.ponudaDatum(datum_dodavanja,id_korisnik);
        call.enqueue(new Callback<List<RezervacijaModel>>() {
            @Override
            public void onResponse(Call<List<RezervacijaModel>> call, Response<List<RezervacijaModel>> response) {
                if (response.isSuccessful()) {
                    List<RezervacijaModel> list = response.body();

                    warningRezervacija.setVisibility(View.GONE);

                    for (int i = 0; i < list.size(); i++) {
                        slikaItem.add(list.get(i).getSlikaFilm());
                        naslov.add(list.get(i).getFilm());
                        id.add(list.get(i).getId());
                        datum.add(list.get(i).getDatum_prikazivanja());
                        vrijeme.add(list.get(i).getVrijeme_prikazivanja());
                    }
                    adapterRezervacija = new AdapterRezervacija(getContext(), slikaItem, naslov, datum, vrijeme, id);
                    recyclerView.setAdapter(adapterRezervacija);
                    recyclerView.scheduleLayoutAnimation();
                }
                else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Upozorenje")
                                .setContentText("Nema filmova pod datumom!")
                                .show();

                        warningRezervacija.setVisibility(View.VISIBLE);
                    }
                }
            @Override
            public void onFailure(Call<List<RezervacijaModel>> call, Throwable t) {
            }
        });
    }

    private void setRezervacija(int id_korisnik){

        Call<List<RezervacijaModel>> call = iRezervacija.sveRezervacije(id_korisnik);
        call.enqueue(new Callback<List<RezervacijaModel>>() {
            @Override
            public void onResponse(Call<List<RezervacijaModel>> call, Response<List<RezervacijaModel>> response) {
                if (response.isSuccessful()) {
                    List<RezervacijaModel> list = response.body();

                    if (list != null) {
                        warningRezervacija.setVisibility(View.GONE);
                        for (int i = 0; i < list.size(); i++) {
                            slikaItem.add("" + list.get(i).getSlikaFilm());
                            naslov.add("" + list.get(i).getFilm());
                            id.add(list.get(i).getId());
                            datum.add(list.get(i).getDatum_prikazivanja());
                            vrijeme.add(list.get(i).getVrijeme_prikazivanja());

                            adapterRezervacija = new AdapterRezervacija(getContext(), slikaItem, naslov, datum, vrijeme, id);
                            recyclerView.setAdapter(adapterRezervacija);
                            recyclerView.scheduleLayoutAnimation();
                        }
                    }
                }else{
                    warningRezervacija.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<RezervacijaModel>> call, Throwable t) {
            }
        });
    }
}
