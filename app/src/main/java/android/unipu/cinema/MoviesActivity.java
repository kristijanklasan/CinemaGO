package android.unipu.cinema;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.fragmenti.MovieFragment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MoviesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDrama, btnKomedija,
            btnAkcija, btnHoror, btnTriler, btnUskoro, btnSci, btnDokumentarni, btnRomantika;

    private String kategorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Toolbar toolbar = findViewById(R.id.toolbarMovies);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnDrama = (Button) findViewById(R.id.btnDrama);
        btnDrama.setOnClickListener(this);

        btnKomedija = (Button) findViewById(R.id.btnKomedija);
        btnKomedija.setOnClickListener(this);

        btnAkcija = (Button) findViewById(R.id.btnAkcija);
        btnAkcija.setOnClickListener(this);

        btnHoror = (Button) findViewById(R.id.btnHoror);
        btnHoror.setOnClickListener(this);

        btnTriler = (Button) findViewById(R.id.btnTriler);
        btnTriler.setOnClickListener(this);

        btnUskoro = (Button) findViewById(R.id.btnUskoro);
        btnUskoro.setOnClickListener(this);

        btnSci = (Button) findViewById(R.id.btnSci);
        btnSci.setOnClickListener(this);

        btnDokumentarni = (Button) findViewById(R.id.btnDokumentarni);
        btnDokumentarni.setOnClickListener(this);

        btnRomantika = (Button) findViewById(R.id.btnRomantika);
        btnRomantika.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MoviesActivity.this, startCinema.class);
                startActivity(i);
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("kategorija", "Drama");
        MovieFragment frag = new MovieFragment();
        frag.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMovie, frag);
        ft.commit();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.btnAkcija: {
                kategorija = (String) btnAkcija.getText();
            }break;

            case R.id.btnKomedija: {
                kategorija = (String) btnKomedija.getText();
            }break;

            case R.id.btnHoror: {
                kategorija = (String) btnHoror.getText();
            }break;

            case R.id.btnTriler: {
                kategorija = (String) btnTriler.getText();
            }break;

            case R.id.btnSci: {
                kategorija = (String) btnSci.getText();
            }break;

            case R.id.btnDokumentarni: {
                kategorija = (String) btnDokumentarni.getText();
            }break;

            case R.id.btnRomantika: {
                kategorija = (String) btnRomantika.getText();
            }break;

            case R.id.btnDrama: {
                kategorija = (String) btnDrama.getText();
            }break;

            case R.id.btnUskoro: {
                kategorija = (String) btnUskoro.getText();
            }break;
        }

        Bundle bundle = new Bundle();
        bundle.putString("kategorija", kategorija);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMovie, fragment);
        ft.commit();
    }
}
