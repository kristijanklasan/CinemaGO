package android.unipu.cinema;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.unipu.cinema.fragmenti.RezervacijaKarata;
import android.unipu.cinema.model.MovieModel;
import android.unipu.cinema.model.RezervacijaModel;
import android.unipu.cinema.retrofit.IMojIzbor;
import android.unipu.cinema.retrofit.IRezervacija;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RezervacijaDetalji extends AppCompatActivity {

    private TextView textKorisnik, textNazivFilma, textDatum, textVrijeme, textRed, textSjedalo, textCijena;
    private IRezervacija iRezervacija;
    private Button btnOcjena, btnZanr, btnTrajanje,btnIzbrisiRezervaciju;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacija_detalji);

        Toolbar toolbar = findViewById(R.id.toolbarRezervacijaDetalji);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RezervacijaDetalji.this, RezervacijeActivity.class);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        textKorisnik = (TextView) findViewById(R.id.textKorisnik);
        textNazivFilma = (TextView) findViewById(R.id.textNazivFilma);
        textDatum = (TextView) findViewById(R.id.textDatumPrikazivanja);
        textVrijeme = (TextView) findViewById(R.id.textVrijemePrikazivanja);
        textRed = (TextView) findViewById(R.id.textRedFilm);
        textSjedalo = (TextView) findViewById(R.id.textSjedaloFilm);
        textCijena = (TextView) findViewById(R.id.textCijenaFilm);

        btnZanr = (Button) findViewById(R.id.btnZanr);
        btnTrajanje = (Button) findViewById(R.id.btnTrajanje);
        btnOcjena = (Button) findViewById(R.id.btnOcjena);
        btnIzbrisiRezervaciju = (Button) findViewById(R.id.btnIzbrisiRezervaciju);
        final RezervacijaModel rezervacija = new RezervacijaModel();
        rezervacija.setId(id);
        dohvatRezervacije(id);

        btnIzbrisiRezervaciju.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                new SweetAlertDialog(RezervacijaDetalji.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Jeste li sigurni?")
                        .setContentText("Jednom izbrisanu rezervaciju ne možete vratiti!")
                        .setConfirmText("Da, izbriši")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog
                                        .setTitleText("Izbrisano!")
                                        .setContentText("Rezervacija je uspješno izbrisana")
                                        .setConfirmText("U redu")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                int id = rezervacija.getId();
                                deleteRezervacija(id);

                                Intent i = new Intent(RezervacijaDetalji.this, RezervacijeActivity.class);
                                startActivity(i);
                            }
                        })
                        .setCancelButton("Poništi", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }

    private void dohvatRezervacije(int id){
        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<List<RezervacijaModel>> call = iRezervacija.dohvatiRezervacijuId(id);
        call.enqueue(new Callback<List<RezervacijaModel>>() {
            @Override
            public void onResponse(Call<List<RezervacijaModel>> call, Response<List<RezervacijaModel>> response) {

                if (response.isSuccessful()) {
                    List<RezervacijaModel> list = response.body();

                    if(list!=null) {
                        for(int i = 0; i<list.size(); i++) {
                            textKorisnik.setText(list.get(i).getIme() + " " + list.get(i).getPrezime());
                            textNazivFilma.setText(list.get(i).getFilm());

                            try {
                                String datum_prikazivanja = datum(list.get(i).getDatum_prikazivanja());
                                textDatum.setText(datum_prikazivanja);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            textVrijeme.setText(list.get(i).getVrijeme_prikazivanja());
                            textRed.setText(String.valueOf(list.get(i).getRed()));
                            textSjedalo.setText(String.valueOf(list.get(i).getSjedalo()));
                            textCijena.setText(String.valueOf(list.get(i).getCijena())+" kn");
                            btnZanr.setText(list.get(i).getZanr());
                            btnTrajanje.setText(list.get(i).getTrajanje());
                            btnOcjena.setText(String.valueOf(list.get(i).getOcjena()));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RezervacijaModel>> call, Throwable t) {
            }
        });
    }

    private void deleteRezervacija(int id){

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRezervacija = retrofitClient.create(IRezervacija.class);

        Call<ResponseBody> deleteRequest = iRezervacija.deleteRezervacija(id);
        deleteRequest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String rezultat = String.valueOf(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    String datum(String datum) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datum);

        format  = new SimpleDateFormat("E, dd.MM.yyyy");
        String formattedDate2 = format.format(date);

        return  formattedDate2;
    }
}
