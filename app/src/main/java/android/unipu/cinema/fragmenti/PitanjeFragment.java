package android.unipu.cinema.fragmenti;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.model.IzborModel;
import android.unipu.cinema.model.PitanjaModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IMojIzbor;
import android.unipu.cinema.retrofit.IPitanja;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.unipu.cinema.startCinema;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PitanjeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editPrijedlog, editPitanje;
    private IPitanja iPitanja;
    private UserModel userModel = new UserModel();
    private Button btnPosaljiUpit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PitanjeFragment() {
        // Required empty public constructor
    }

    public static PitanjeFragment newInstance(String param1, String param2) {
        PitanjeFragment fragment = new PitanjeFragment();
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
        View view = inflater.inflate(R.layout.fragment_pitanje, container, false);

        editPrijedlog = (EditText) view.findViewById(R.id.editPrijedlog);
        editPitanje = (EditText) view.findViewById(R.id.editPitanje);
        btnPosaljiUpit = (Button) view.findViewById(R.id.btnPosaljiUpit);

        Toolbar toolbar = view.findViewById(R.id.toolbarPostaviPitanje);
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

        btnPosaljiUpit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                insertUpit();

                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Uspješno")
                        .setContentText("Uspješno ste poslali upit!")
                        .show();
            }
        });
        return view;
    }

    private void insertUpit(){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iPitanja = retrofitClient.create(IPitanja.class);

        String pitanje = editPitanje.getText().toString();
        String prijedlog = editPrijedlog.getText().toString();
        int id_korisnik = Integer.parseInt(userModel.getKljuc());

        Call<List<PitanjaModel>> call = iPitanja.insertUpit(pitanje, prijedlog, id_korisnik);
        call.enqueue(new Callback<List<PitanjaModel>>() {
            @Override
            public void onResponse(Call<List<PitanjaModel>> call, Response<List<PitanjaModel>> response) {
            }

            @Override
            public void onFailure(Call<List<PitanjaModel>> call, Throwable t) {
            }
        });
    }
}
