package project.smartatha;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HearingAidActivity extends AppCompatActivity{

    private AdView mAdView;

    Thread t;
    int sr = 44100;
    boolean isRunning = true;
    double sliderval = 0;

    Button hearingaidbutton;
    Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_aid);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        hearingaidbutton = (Button)findViewById(R.id.button1);
        backbutton = (Button)findViewById(R.id.button2);

        hearingaidbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(sliderval==0){
                    sliderval = 8000;
                    hearingaidbutton.setText("stop");
                }else{
                    hearingaidbutton.setText("start hearing aid");
                    sliderval = 0;
                }
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        t = new Thread(){
            public void run(){
                setPriority(Thread.MAX_PRIORITY);

                int buffsize = AudioTrack.getMinBufferSize(sr,
                        AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sr, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, buffsize,
                        AudioTrack.MODE_STREAM);

                short samples[] = new short[buffsize];
                int amp = 10000;
                double twopi = 8.*Math.atan(1.);
                double fr = 440.f;
                double ph = 0.0;

                audioTrack.play();

                while(isRunning){
                    fr = sliderval;
                    for(int i=0; i < buffsize; i++){
                        samples[i] = (short) (amp*Math.sin(ph));
                        ph += twopi*fr/sr;
                    }
                    audioTrack.write(samples, 0, buffsize);
                }
                audioTrack.stop();
                audioTrack.release();
            }
        };
        t.start();
    }


    public void onDestroy(){
        super.onDestroy();
        isRunning = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = null;
    }
}