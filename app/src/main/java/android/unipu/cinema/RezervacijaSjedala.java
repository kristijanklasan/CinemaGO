package android.unipu.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.unipu.cinema.model.KartaModel;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class RezervacijaSjedala extends AppCompatActivity {

    private LinearLayout linearLayout1, linearLayout2,linearLayout3,linearLayout4,linearLayout5,linearLayout6,linearLayout7,linearLayout8;
    private Button btnPrihvatiSjedala;
    private KartaModel modelKarta;
    private ArrayList<Integer> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacija_sjedala);

        final CheckBox checkBox = new CheckBox(getApplicationContext());
        lista = new ArrayList<>();

        modelKarta = new KartaModel();
        linearLayout1 = (LinearLayout) findViewById(R.id.layoutRed1);
        linearLayout2 = (LinearLayout) findViewById(R.id.layoutRed2);
        linearLayout3 = (LinearLayout) findViewById(R.id.layoutRed3);
        linearLayout4 = (LinearLayout) findViewById(R.id.layoutRed4);
        linearLayout5 = (LinearLayout) findViewById(R.id.layoutRed5);
        linearLayout6 = (LinearLayout) findViewById(R.id.layoutRed6);
        linearLayout7 = (LinearLayout) findViewById(R.id.layoutRed7);
        linearLayout8 = (LinearLayout) findViewById(R.id.layoutRed8);
        btnPrihvatiSjedala = (Button) findViewById(R.id.btnPotvrdaSjedala);

        kreirajCheckBox();
        CheckBox[] checkBoxes = new CheckBox[97];

        ArrayList<Integer> lista = new ArrayList<>();
        lista = modelKarta.getLista();

        //Toast.makeText(getApplicationContext(),""+lista.size(),Toast.LENGTH_SHORT).show();

        for (int i = 0; i < 96; i++) {
            checkBoxes[i] = (CheckBox) findViewById(1 + i);

            if(lista!=null){
                for (int k = 0; k < lista.size(); k++) {
                    if (lista.get(k).intValue() == i+1) {
                        checkBoxes[i].setEnabled(false);
                    }
                }
            }

            final String tekst = checkBoxes[i].getText().toString();
            checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        provjeri();
                    }
                });
        }

        btnPrihvatiSjedala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int red = modelKarta.getRed();
                int sjedalo = modelKarta.getSjedalo();

                if (red != 0 && sjedalo != 0) {
                    Intent intent = new Intent(RezervacijaSjedala.this, RezervacijaKarataActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        });
    }


    private void kreirajCheckBox(){

        CheckBox[] checkBoxes = new CheckBox[12];

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i + 1));
            checkBoxes[i].setId(1+i);
            checkBoxes[i].setTag(1);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout1.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+12+1);
            checkBoxes[i].setTag(2);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout2.addView(checkBoxes[i], i);
        }


        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+24+1);
            checkBoxes[i].setTag(3);
            checkBoxes[i].setTextSize(14);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout3.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+36+1);
            checkBoxes[i].setTag(4);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout4.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+48+1);
            checkBoxes[i].setTag(5);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout5.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+60+1);
            checkBoxes[i].setTag(6);
            checkBoxes[i].setGravity(Gravity.CENTER);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout6.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+72+1);
            checkBoxes[i].setTag(7);
            checkBoxes[i].setPadding(25,0,25,0);
            checkBoxes[i].setGravity(Gravity.CENTER);
            linearLayout7.addView(checkBoxes[i], i);
        }

        for (int i = 0; i < 12; i++) {
            checkBoxes[i] = new CheckBox(this);
            checkBoxes[i].setText(String.format(Locale.getDefault(), "%d", i+1));
            checkBoxes[i].setId(i+84+1);
            checkBoxes[i].setTag(8);
            checkBoxes[i].setPadding(25,0,25,0);
            linearLayout8.addView(checkBoxes[i], i);
        }
    }

    private void provjeri(){

        CheckBox[] checkBoxes = new CheckBox[97];
        for (int i = 0; i < 96; i++) {
            checkBoxes[i] = (CheckBox) findViewById(1+i);

            if(checkBoxes[i].isChecked()){
                String red_tekst = checkBoxes[i].getTag().toString();
                int red = Integer.parseInt(red_tekst);
                int sjedalo = Integer.parseInt(checkBoxes[i].getText().toString());
                int oznaka = checkBoxes[i].getId();
                modelKarta.setRed(red);
                modelKarta.setSjedalo(sjedalo);
                modelKarta.setOznaka2(oznaka);
            }
        }
    }
}
