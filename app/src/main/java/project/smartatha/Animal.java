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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Animal extends AppCompatActivity {

	private AdView mAdView;

	Button lizard;
	Button petAnimal;
	Button insects;
	Button rats;
	Button backbutton;

	Thread t;
	int sr = 122000;
	boolean isRunning = true;
	double sliderval = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animal);

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		lizard=(Button)findViewById(R.id.lizard);
		petAnimal=(Button)findViewById(R.id.pet_animal);
		insects=(Button)findViewById(R.id.insects);
		rats=(Button)findViewById(R.id.rats);
		backbutton=(Button)findViewById(R.id.BackButton);

		lizard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sliderval==0 || sliderval==22000 || sliderval==38000 || sliderval==60000){
					sliderval=52000;
					lizard.setText("Stop");
					petAnimal.setText("Pet Animals");
					insects.setText("Insects");
					rats.setText("Rats");
				}
				else{
					sliderval=0;
					lizard.setText("Lizard");
				}
			}
		});

		petAnimal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sliderval==0 || sliderval==52000 || sliderval==38000 || sliderval==60000){
					sliderval=22000;
					petAnimal.setText("Stop");
					lizard.setText("Lizard");
					insects.setText("Insects");
					rats.setText("Rats");
				}
				else{
					sliderval=0;
					petAnimal.setText("Pet Animals");
				}
			}
		});

		insects.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sliderval==0 || sliderval==52000 || sliderval==22000 || sliderval==60000){
					sliderval=38000;
					insects.setText("Stop");
					lizard.setText("Lizard");
					petAnimal.setText("Pet Animals");
					rats.setText("Rats");
				}
				else{
					sliderval=0;
					insects.setText("Insects");
				}
			}
		});

		rats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sliderval==0 || sliderval==52000 || sliderval==22000 || sliderval==38000){
					sliderval=60000;
					rats.setText("Stop");
					lizard.setText("Lizard");
					petAnimal.setText("Pet Animals");
					insects.setText("Insects");
				}
				else{
					sliderval=0;
					rats.setText("Rats");
				}
			}
		});

		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		t = new Thread() {
			public void run() {

				setPriority(Thread.MAX_PRIORITY);

				int buffsize = AudioTrack.getMinBufferSize(sr, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

				AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sr, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffsize, AudioTrack.MODE_STREAM);

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