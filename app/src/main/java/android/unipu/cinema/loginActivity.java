package android.unipu.cinema;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.unipu.cinema.model.PitanjaModel;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IPitanja;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loginActivity extends AppCompatActivity {

    private TextView textRegistracija;
    private EditText textEmail;
    private EditText textLozinka;
    private Button btnLogin;
    private ArrayList<String> list;
    CompositeDisposable compositeDisposible = new CompositeDisposable();
    IRegistration iRegistration;
    public UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userModel = new UserModel();
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iRegistration = retrofitClient.create(IRegistration.class);

        textEmail = (EditText) findViewById(R.id.email);
        textLozinka = (EditText) findViewById(R.id.password);

        textRegistracija = (TextView) findViewById(R.id.textRegistracijaLink);
        String stil = "<font color='green'>Registracija</font>";
        textRegistracija.setText(Html.fromHtml(stil), TextView.BufferType.SPANNABLE);

        textRegistracija.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Slanje zahtjeva za prijavom",Toast.LENGTH_SHORT).show();
                loginUser(textEmail.getText().toString(),
                        textLozinka.getText().toString());
            }
        });
    }

    @Override
    protected void onStop(){
        compositeDisposible.clear();
        super.onStop();
    }

    private void loginUser(String email, String lozinka){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Unesite e-mail adresu!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lozinka)){
            Toast.makeText(this,"Unesite lozinku!",Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofitClient = RetrofitClient.getInstance2();
        iRegistration = retrofitClient.create(IRegistration.class);

        Call<List<UserModel>> call = iRegistration.loginUser(email,lozinka);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                Intent intent = new Intent(loginActivity.this, startCinema.class);
                intent.putExtra("email",textEmail.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Pogrešna e-mail adresa ili lozinka", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void writeData(ArrayList<String> lista) throws IndexOutOfBoundsException{
        try {
            FileOutputStream fileout= getApplication().openFileOutput("User.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            // Spremanje podataka u tekstualnu datoteku (internal storage)
            for(int i = 0; i<lista.size(); i++){
                //Toast.makeText(getApplicationContext(),""+lista.get(i),Toast.LENGTH_LONG).show();
                outputWriter.write(lista.get(i)+"\n");
            }
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
