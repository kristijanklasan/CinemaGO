package android.unipu.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.unipu.cinema.fragmenti.AccountFragment;
import android.unipu.cinema.fragmenti.IzborFragment;
import android.unipu.cinema.fragmenti.RezervacijaFragment;
import android.view.MenuItem;
import android.view.WindowManager;

public class RezervacijeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacije);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerRezervacija,new IzborFragment()).commit();

        BottomNavigationView bottomView = findViewById(R.id.bottom_navigationIzbor);
        bottomView.setOnNavigationItemSelectedListener(navListener);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent i = new Intent(getApplicationContext(), RezervacijeActivity.class);
        startActivityForResult(i,0);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;

            switch(menuItem.getItemId()){
                case R.id.listIzbor1:{
                    fragment = new RezervacijaFragment();
                }break;

                case R.id.listIzbor2: {
                    fragment = new IzborFragment();
                }break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerRezervacija,fragment).commit();
            return true;
        }
    };

}
