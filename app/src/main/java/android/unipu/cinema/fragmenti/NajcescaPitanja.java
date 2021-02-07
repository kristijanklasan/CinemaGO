package android.unipu.cinema.fragmenti;

import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.PitanjaActivity;
import android.unipu.cinema.adapter.MainAdapter;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.PitanjaModel;
import android.unipu.cinema.retrofit.IMovie;
import android.unipu.cinema.retrofit.IPitanja;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.unipu.cinema.startCinema;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.ExpandableListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NajcescaPitanja extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private IPitanja iPitanja;
    private ExpandableListView expandableList;
    ArrayList<String> listaGrupa = new ArrayList<>();
    MainAdapter adapter;
    HashMap<String, ArrayList<String>> listItem = new HashMap<>();

    private String mParam1;
    private String mParam2;

    public NajcescaPitanja() {
        // Required empty public constructor
    }

    public static NajcescaPitanja newInstance(String param1, String param2) {
        NajcescaPitanja fragment = new NajcescaPitanja();
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
        View view = inflater.inflate(R.layout.fragment_najcesca_pitanja, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolbarNajcescaPitanja);
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

        expandableList = view.findViewById(R.id.expandable_list_pitanja);
        //initVoidList();

        getPitanja();
        return view;
    }

    private void getPitanja() {

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iPitanja = retrofitClient.create(IPitanja.class);

        Call<List<PitanjaModel>> call = iPitanja.dohvatPitanja();
        call.enqueue(new Callback<List<PitanjaModel>>() {
            @Override
            public void onResponse(Call<List<PitanjaModel>> call, Response<List<PitanjaModel>> response) {
                List<PitanjaModel> list = response.body();

                if(list!=null){
                    for (int i = 0; i < list.size(); i++) {
                        ArrayList<String> item = new ArrayList<>();
                        listaGrupa.add(list.get(i).getNaziv());
                        item.add(list.get(i).getOpis());

                        listItem.put(listaGrupa.get(i), item);
                        adapter = new MainAdapter(listaGrupa, listItem);
                        expandableList.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PitanjaModel>> call, Throwable t) {
            }
        });
    }
}
