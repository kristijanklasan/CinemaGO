package android.unipu.cinema;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.unipu.cinema.connection.checkConnection;

import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private int pStatus = 0;
    private TextView txtProgress;
    private ProgressBar progressBar;
    private ShimmerFrameLayout shimmerContent;
    Boolean connection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProgress = (TextView) findViewById(R.id.textProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        checkNetwork();

        shimmerContent = findViewById(R.id.shimmerContent);
        shimmerContent.startShimmerAnimation();

    }

    private void checkNetwork(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        checkConnection check = new checkConnection();
        connection = check.checkNetworkConnection(connectivityManager);

        if(connection.equals(true)) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (pStatus < 100) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(pStatus);
                                if (pStatus == 99) {
                                    txtProgress.setText("Učitavanje dovršeno: 100 %");

                                    Intent i = new Intent(MainActivity.this, loginActivity.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    txtProgress.setText("Pričekajte: " + pStatus + " %");
                                }
                            }
                        });
                        try {
                            Thread.sleep(100);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pStatus++;
                    }
                }
            }).start();

            if (pStatus == 100) {
            }
        }else{
            alertInternet();
        }
    }

    private void alertInternet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Niste povezani s Internetom");
        builder.setMessage("Provjerite povezanost s Internetom!");
        builder.setPositiveButton("Ponovno pokreni", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkNetwork();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
