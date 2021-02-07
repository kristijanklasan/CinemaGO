package android.unipu.cinema;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.unipu.cinema.model.UserModel;
import android.unipu.cinema.retrofit.IRegistration;
import android.unipu.cinema.retrofit.RetrofitClient;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.unipu.cinema.model.UserModel;

import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class registrationActivity extends AppCompatActivity {

    private TextView textPrijavaLink;
    CompositeDisposable compositeDisposible = new CompositeDisposable();
    IRegistration iRegistration;
    private EditText textName;
    private EditText textLastName;
    private EditText phoneNumber;
    private EditText textEmail;
    private EditText textPassword;
    private Button regButton;
    private ArrayList<String> list;
    public UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iRegistration = retrofitClient.create(IRegistration.class);

        userModel = new UserModel();
        textName = (EditText) findViewById(R.id.usernameRegistration);
        textLastName = (EditText) findViewById(R.id.lastnameRegistration);
        phoneNumber = (EditText) findViewById(R.id.phoneRegistration);
        textEmail = (EditText) findViewById(R.id.emailRegistration);
        textPassword = (EditText) findViewById(R.id.passwordRegistration);

        textPrijavaLink = (TextView) findViewById(R.id.textPrijavaLink);
        String stil = "<font color='#fc0a3a'>Prijava</font>";
        textPrijavaLink.setText(Html.fromHtml(stil), TextView.BufferType.SPANNABLE);

        textPrijavaLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registrationActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        regButton = (Button) findViewById(R.id.registration);
        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"Slanje zahjteva za unosom",Toast.LENGTH_SHORT).show();
                registerUser(textName.getText().toString(),
                        textLastName.getText().toString(),
                        phoneNumber.getText().toString(),
                        textEmail.getText().toString(),
                        textPassword.getText().toString());
            }
        });
    }

    @Override
    protected void onStop(){
        compositeDisposible.clear();
        super.onStop();
    }

    private void registerUser(String ime, String prezime, String telefon, String email, String lozinka){

        if(TextUtils.isEmpty(ime) && TextUtils.isEmpty(prezime)){
            Toast.makeText(this,"Polja ime i prezime moraju biti popunjena!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(telefon)){
            Toast.makeText(this,"Unesite podatak o broju telefona!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(lozinka)){
            Toast.makeText(this,"Unesite lozinku!",Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposible.add(iRegistration.registerUser(ime,prezime,telefon,email,lozinka)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {

                        if(response.equals("true")){
                            userModel.setEmail(textEmail.getText().toString());
                            list = new ArrayList<String>();
                            list.add(textName.getText().toString());
                            list.add(textLastName.getText().toString());
                            list.add(textEmail.getText().toString());
                            userModel.setEmail(textEmail.getText().toString());

                            writeData(list);
                            Intent intent = new Intent(registrationActivity.this, startCinema.class);
                            intent.putExtra("email",userModel.getEmail());
                            startActivity(intent);
                        }else{
                            Toast.makeText(registrationActivity.this,""+response, Toast.LENGTH_LONG).show();
                        }
                    }
                }));
    }

    private void writeData(ArrayList<String> lista) throws IndexOutOfBoundsException{
        try {
            FileOutputStream fileout = getApplication().openFileOutput("User.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);

            // Spremanje podataka u tekstualnu datoteku (internal storage)
            for(int i = 0; i<lista.size(); i++){
                Toast.makeText(getApplicationContext(),""+lista.get(i),Toast.LENGTH_LONG).show();
                outputWriter.write(lista.get(i)+"\n");
            }
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
