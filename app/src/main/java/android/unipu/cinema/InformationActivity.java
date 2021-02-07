package android.unipu.cinema;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    private TextView deviceText,verzijaOS, sdkVerzija,povezivanjeTekst;
    private Boolean wifiConnected,mobileConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Toolbar toolbar = findViewById(R.id.toolbarInformation);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deviceText = (TextView) findViewById(R.id.uredajText);
        verzijaOS = (TextView) findViewById(R.id.verzijaOS);
        sdkVerzija = (TextView) findViewById(R.id.sdkVersion);
        povezivanjeTekst = (TextView) findViewById(R.id.povezivanjeTekst);

        String device = getDeviceName();
        deviceText.setText(device);

        String version = android.os.Build.VERSION.RELEASE;
        verzijaOS.setText("Android "+version);

        int verzijaSdk = android.os.Build.VERSION.SDK_INT;
        sdkVerzija.setText(String.valueOf(verzijaSdk));

        checkNetworkConnection();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InformationActivity.this, startCinema.class);
                startActivity(i);
            }
        });
    }

    private void checkNetworkConnection(){

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

            if(wifiConnected){
                povezivanjeTekst.setText("Wireless");
            }else if(mobileConnected){
                povezivanjeTekst.setText("Mobilni podaci");
            }
        }
        else{
            povezivanjeTekst.setText("Nije povezano!");
        }
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        String device = manufacturer + " " + model;
        return device;
    }
}
