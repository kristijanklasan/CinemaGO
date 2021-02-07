package android.unipu.cinema.fragmenti;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.adapter.MainAdapter;
import android.unipu.cinema.model.KartaModel;
import android.unipu.cinema.startCinema;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.unipu.cinema.R;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UvjetiKupnje extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ExpandableListView expandableList;
    ArrayList<String> listaGrupa = new ArrayList<>();
    MainAdapter adapter;
    HashMap<String, ArrayList<String>> listItem = new HashMap<>();


    public UvjetiKupnje() {
        // Required empty public constructor
    }
    public static UvjetiKupnje newInstance(String param1, String param2) {
        UvjetiKupnje fragment = new UvjetiKupnje();
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
        final View view = inflater.inflate(R.layout.fragment_uvjeti_kupnje, container, false);

        expandableList = view.findViewById(R.id.expandable_list);

        Toolbar toolbar = view.findViewById(R.id.toolbarUvjetiKupnje);
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

        listaGrupa.add("Postupak kupovine karata");
        listaGrupa.add("Registracija");
        listaGrupa.add("Plaćanje karata");
        listaGrupa.add("Uvjeti dostave/isporuke karata");
        listaGrupa.add("Promjene programa");
        listaGrupa.add("Zamjena i reklamacije");

        String [] polje = new String[6];
        polje[0] = "Rezervacija ili kupnja ulaznice vrši se odabirom željene satnice ispod filma ili odabirom dana u kalendaru, " +
                "izravno iz izbornika ili u izborniku Ulaznice/Kupi ulaznicu. \n\n Nakon izbora željenog filma i termina: \n\n – odaberite željeno mjesto u dvorani \n\n" +
                "– registrirajte se\n \n– provjerite točnost podataka prije kupnje\n \n– izvršite plaćanje";

        polje[1] = "Kupac mora biti registriran kako bi izvršio kupovinu. Prilikom registracije od Kupca se zahtjeva da unese osobne e-mail adresu i lozinku.";

        polje[2] = "Plaćanje je moguće izvršiti putem kreditne kartice (American Express®, MasterCard®, Visa, Maestro) isključivo prije projekcije filma.";

        polje[3] = "U završnom postupku kupovine online ulaznica na vašem sučelju prikazat će se potvrda o kupnji (ulaznica) sa svim potrebnim podacima .\n";

        polje[4] = "U slučajevima otkazivanja filma ili promjene rasporeda, " +
                "Kupac za online kupljenu ulaznicu može na blagajni kina dobiti povrat novca ili ju zamijeniti za isti film u drugom terminu. " +
                "\n Rok za ostvarivanje prava je najkasnije do vremena na koje se kupljena ulaznica odnosi.";

        polje[5] = "Zamjenu ulaznica moguće je ostvariti samo u slučajevima promjene ili otkazivanja programa.\n" +
                "Za kupljene, a nepreuzete kino ulaznice, nije moguće tražiti povrat sredstava ili zamjenu za drugu kino ulaznicu.\n" +
                "Prije nego što kupovinu konačno potvrdite provjerite sve podatke o željenom filmu, terminu i vremenu projekcije jer naknadne reklamacije ne uvažavamo.";

        for(int i=0; i<listaGrupa.size(); i++){

            ArrayList<String> lista = new ArrayList<>();

            lista.add(polje[i]);

            listItem.put(listaGrupa.get(i),lista);

            adapter = new MainAdapter(listaGrupa,listItem);
            expandableList.setAdapter(adapter);
        }
        return view;
    }
}
