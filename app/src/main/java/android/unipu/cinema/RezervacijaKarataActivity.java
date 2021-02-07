package android.unipu.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.fragmenti.IzborFragment;
import android.unipu.cinema.fragmenti.RezervacijaKarata;
import android.unipu.cinema.fragmenti.UvjetiKupnje;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class RezervacijaKarataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacija_karata);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerRezervacijaKarata,new RezervacijaKarata()).commit();
        BottomNavigationView bottomView = findViewById(R.id.bottom_navigationKarte);
        bottomView.setOnNavigationItemSelectedListener(navListener);

        Toolbar toolbar = findViewById(R.id.toolbarRezervacijaKarata);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RezervacijaKarataActivity.this, startCinema.class);
                startActivity(i);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(getApplicationContext(), RezervacijaKarataActivity.class);
        startActivityForResult(i,0);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;

            switch(menuItem.getItemId()){
                case R.id.listKarte1:{
                    fragment = new RezervacijaKarata();
                }break;

                case R.id.listKarte2: {
                    fragment = new UvjetiKupnje();
                }break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRezervacijaKarata,fragment).commit();
            return true;
        }
    };
}
