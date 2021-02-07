package android.unipu.cinema;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class TrailerPlayer extends AppCompatActivity {

    private VideoView v;
    MediaController mediaController;
    ProgressDialog progressDialog;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_player);
        v = (VideoView) findViewById(R.id.videoView);

        try {
            url = getIntent().getStringExtra("trailer");
            playvideo(url);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void playvideo(String videopath) {

        try {

            progressDialog = ProgressDialog.show(TrailerPlayer.this, "",
                    "Priprema videozapisa", false);
            progressDialog.setCancelable(true);

            getWindow().setFormat(PixelFormat.TRANSLUCENT);

            mediaController = new MediaController(TrailerPlayer.this);

            Uri video = Uri.parse(videopath);
            v.setMediaController(mediaController);
            v.setVideoURI(video);

            v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    progressDialog.dismiss();
                    v.start();
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            System.out.println("Greška! Video se ne može prikazati" + e.getMessage());
        }

    }
}
