package android.unipu.cinema;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.fragmenti.NajcescaPitanja;
import android.unipu.cinema.fragmenti.PitanjeFragment;
import android.unipu.cinema.fragmenti.RezervacijaKarata;
import android.unipu.cinema.fragmenti.UvjetiKupnje;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class PitanjaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitanja);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerPitanja,new NajcescaPitanja()).commit();
        BottomNavigationView bottomView = findViewById(R.id.bottom_navigationPitanja);
        bottomView.setOnNavigationItemSelectedListener(navListener);

        Toolbar toolbar = findViewById(R.id.toolbarPitanja);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PitanjaActivity.this, startCinema.class);
                startActivity(i);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(getApplicationContext(), PitanjaActivity.class);
        startActivityForResult(i,0);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;

            switch(menuItem.getItemId()){
                case R.id.listPitanja1:{
                    fragment = new NajcescaPitanja();
                }break;

                case R.id.listPitanja2: {
                    fragment = new PitanjeFragment();
                }break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerPitanja,fragment).commit();
            return true;
        }
    };
}
