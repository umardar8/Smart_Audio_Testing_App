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

public class DisperesHumanActivity extends AppCompatActivity {
	private AdView mAdView;

	Button everyone;
	Button younger24;
	Button younger39;
	Button younger49;
	Button younger59;
	Button backbutton;
	
	Thread t;
    int sr = 44100;
    boolean isRunning = true;
    double sliderval = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disperes_human);

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		mAdView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		everyone=(Button)findViewById(R.id.bplus20);
		younger24=(Button)findViewById(R.id.Younger24ButtonId);
		younger39=(Button)findViewById(R.id.Younger39ButtonId);
		younger49=(Button)findViewById(R.id.Younger49ButtonId);
		younger59=(Button)findViewById(R.id.Younger59ButtonId);
		backbutton=(Button)findViewById(R.id.BackButton);

		everyone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sliderval==0 || sliderval==10000 || sliderval==12000 || sliderval==15000 || sliderval==17000) {
					sliderval=8000;
					everyone.setText("stop");
					younger24.setText("24 younger");
					younger39.setText("39 younger");
					younger49.setText("49 younger");
					younger59.setText("59 younger");
				}
				else{
					sliderval=0;
					everyone.setText("everyone");
				}
			}
		});

		younger59.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sliderval==0 || sliderval==8000 || sliderval==12000 || sliderval==15000 || sliderval==17000) {
					sliderval=10000;
					everyone.setText("everyone");
					younger24.setText("24 younger");
					younger39.setText("39 younger");
					younger49.setText("49 younger");
					younger59.setText("stop");
				}
				else{
					sliderval=0;
					younger59.setText("59 younger");
				}
			}
		});

		younger49.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sliderval==0 || sliderval==10000 || sliderval==8000 || sliderval==15000 || sliderval==17000) {
					sliderval=12000;
					everyone.setText("everyone");
					younger24.setText("24 younger");
					younger39.setText("39 younger");
					younger49.setText("stop");
					younger59.setText("59 younger");
				}
				else{
					sliderval=0;
					younger49.setText("49 younger");
				}
			}
		});

		younger39.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sliderval==0 || sliderval==10000 || sliderval==12000 || sliderval==8000 || sliderval==17000) {
					sliderval=15000;
					everyone.setText("everyone");
					younger24.setText("24 younger");
					younger39.setText("stop");
					younger49.setText("49 younger");
					younger59.setText("59 younger");
				}
				else{
					sliderval=0;
					younger39.setText("39 younger");
				}
			}
		});

		younger24.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (sliderval==0 || sliderval==10000 || sliderval==12000 || sliderval==15000 || sliderval==8000) {
					sliderval=17000;
					everyone.setText("everyone");
					younger24.setText("stop");
					younger39.setText("39 younger");
					younger49.setText("49 younger");
					younger59.setText("59 younger");
				}
				else{
					sliderval=0;
					younger24.setText("24 younger");
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
	         // set process priority
	         setPriority(Thread.MAX_PRIORITY);
	         // set the buffer size
	        int buffsize = AudioTrack.getMinBufferSize(sr,
	                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
	        // create an audiotrack object
	        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
	                                          sr, AudioFormat.CHANNEL_OUT_MONO,
	                                  AudioFormat.ENCODING_PCM_16BIT, buffsize,
	                                  AudioTrack.MODE_STREAM);

	        short samples[] = new short[buffsize];
	        int amp = 10000;
	        double twopi = 8.*Math.atan(1.);
	        double fr = 440.f;
	        double ph = 0.0;

	        // start audio
	       audioTrack.play();

	       // synthesis loop
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