package android.unipu.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.model.CijeneModel;
import android.unipu.cinema.retrofit.ICijene;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.View;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CijenaKarata extends AppCompatActivity {

    private ICijene iCijene;
    private CijeneModel model = new CijeneModel();;
    private TextView textCijenaOdrasli, textCijenaStudenti, textCijenaDjeca, textCijeneDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cijena_karata);

        Toolbar toolbar = findViewById(R.id.toolbarCijene);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textCijenaDjeca = (TextView) findViewById(R.id.textCijenaDjeca);
        textCijenaOdrasli = (TextView) findViewById(R.id.textCijenaOdrasli);
        textCijenaStudenti = (TextView) findViewById(R.id.textCijenaStudenti);
        textCijeneDatum = (TextView) findViewById(R.id.textCijeneDatum);
        setCijene();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CijenaKarata.this, startCinema.class);
                startActivity(i);
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

                        try {
                            String datum_unosa = datum(list.get(i).getDatum_unosa());
                            textCijeneDatum.setText(datum_unosa);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        textCijenaDjeca.setText(String.valueOf(list.get(i).getCijene_studenti())+" kn");
                        textCijenaOdrasli.setText(String.valueOf(list.get(i).getCijene_odrasli())+" kn");
                        textCijenaStudenti.setText(String.valueOf(list.get(i).getCijene_studenti())+" kn");
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CijeneModel>> call, Throwable t) {
            }
        });
    }

    String datum(String datum) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datum);

        format  = new SimpleDateFormat("E, dd.MM.yyyy");;
        String formattedDate2 = format.format(date);

        return  formattedDate2;
    }

}

