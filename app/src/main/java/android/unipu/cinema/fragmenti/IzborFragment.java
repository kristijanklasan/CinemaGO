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
import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IMojIzbor;
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

public class IzborFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2, formatDate;
    private IzborModel izbor = new IzborModel();
    private Switch switchDatum;
    private  int id_korisnika;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private ArrayList slikaItem, naslov, id;
    private UserModel user = new UserModel();
    private AdapterPoster adapterPoster;
    private TextView textDatum, warningIzbor;
    private DatePickerDialog.OnDateSetListener mDatePicker;
    private IMojIzbor iIzbor;

    public IzborFragment() {
        // Required empty public constructor
    }

    public static IzborFragment newInstance(String param1, String param2) {
        IzborFragment fragment = new IzborFragment();
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

        View view = inflater.inflate(R.layout.fragment_izbor, container, false);

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iIzbor = retrofitClient.create(IMojIzbor.class);

        Toolbar toolbar = view.findViewById(R.id.toolbarIzbor);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        slikaItem = new ArrayList();
        naslov = new ArrayList();
        id = new ArrayList();

        warningIzbor = (TextView) view.findViewById(R.id.warningIzbor);
        imageButton = (ImageButton) view.findViewById(R.id.imageDatumIzbor);
        textDatum = (TextView) view.findViewById(R.id.textDatum);
        switchDatum = (Switch) view.findViewById(R.id.switch1);
        recyclerView = view.findViewById(R.id.recyclerViewIzbor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        id_korisnika = Integer.parseInt(user.getKljuc());
        final FrameLayout frame = (FrameLayout) view.findViewById(R.id.frameLayoutIzbor);

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

                SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
                formatDate = simple.format(calendar.getTime());
                textDatum.setText(formatDate);

                String datum = textDatum.getText().toString();
                setIzborDatum(id_korisnika,datum);
                Toast.makeText(getContext(),""+id_korisnika,Toast.LENGTH_SHORT).show();
            }
        };

        setIzbor(id_korisnika);

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
                warningIzbor.setVisibility(View.GONE);

                if(switchDatum.isChecked()) {
                    frame.setVisibility(View.VISIBLE);
                }
                else {
                    clearRecycler();
                    frame.setVisibility(View.GONE);
                    setIzbor(id_korisnika);
                }
            }
        });
        return view;
    }

    private void clearRecycler(){
        slikaItem.clear();
        naslov.clear();
        id.clear();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setIzborDatum(int id_korisnik, String datum_dodavanja){

        Call<List<MovieModel>> call = iIzbor.dohvatDatum(id_korisnik,datum_dodavanja);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {

                if (response.isSuccessful()) {
                    List<MovieModel> list = response.body();
                    if(list != null) {
                        warningIzbor.setVisibility(View.GONE);
                        for (int i = 0; i < list.size(); i++) {
                            slikaItem.add("" + list.get(i).getSlikaPozadina());
                            naslov.add("" + list.get(i).getFilm());
                            id.add(list.get(i).getId());
                        }
                        adapterPoster = new AdapterPoster(getContext(), slikaItem, naslov, id);
                        recyclerView.setAdapter(adapterPoster);
                        recyclerView.scheduleLayoutAnimation();
                    }else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Upozorenje")
                                .setContentText("Nema filmova pod datumom!")
                                .show();

                        warningIzbor.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
            }
        });
    }

    private void setIzbor(int id_korisnik){

        Call<List<MovieModel>> call = iIzbor.dohvatIzbora(id_korisnik);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {

                if (response.isSuccessful()) {
                    List<MovieModel> list = response.body();

                    if (list != null) {
                        warningIzbor.setVisibility(View.GONE);
                        for (int i = 0; i < list.size(); i++) {
                            slikaItem.add("" + list.get(i).getSlikaPozadina());
                            naslov.add("" + list.get(i).getFilm());
                            id.add(list.get(i).getId());
                        }
                        adapterPoster = new AdapterPoster(getContext(), slikaItem, naslov, id);
                        recyclerView.setAdapter(adapterPoster);
                        recyclerView.scheduleLayoutAnimation();
                    }
                }else{
                    warningIzbor.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
            }
        });
    }
}
