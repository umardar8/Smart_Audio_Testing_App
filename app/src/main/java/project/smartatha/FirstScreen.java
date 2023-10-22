package project.smartatha;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FirstScreen extends AppCompatActivity {

	Thread t;
    int sr = 44100;
    boolean isRunning = true;
    double sliderval = 0;

    TextView view;
	Button bplay;
	Button backbutton;
	Button bplus20;
	Button bminus20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_screen);

		view=(TextView)findViewById(R.id.editText1);
		bplay=(Button)findViewById(R.id.bplay);
		bplus20=(Button)findViewById(R.id.bplus20);
		bminus20=(Button)findViewById(R.id.bminus20);
		backbutton=(Button)findViewById(R.id.backbutton);

		bplus20.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String freq=view.getText().toString();
				try{
					int fre=Integer.parseInt(freq);
					fre=fre+20;
					view.setText(""+fre);
				}
				catch(Exception exp){
					view.setText("0");
					Toast.makeText(getApplicationContext(), "Sorry, we got some problem", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		bminus20.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String freq=view.getText().toString();
				try{
					int fre=Integer.parseInt(freq);
					fre=fre-20;
					view.setText(""+fre);
				}
				catch(Exception exp){
					view.setText("0");
					Toast.makeText(getApplicationContext(), "Sorry, we got some problem", Toast.LENGTH_SHORT).show();
				}
			}
		});

		bplay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(bplay.getText().equals("Play")){
					String valueinstring=view.getText().toString();
					double d=0;
					try{
						d=Double.parseDouble(valueinstring);	
					}
					catch(Exception exp){
						Toast.makeText(getApplicationContext(), "String Casting Error", Toast.LENGTH_SHORT).show();
						bplay.setText("Play");
						return;
					}
					
					if(d>=20000){
						Toast.makeText(getApplicationContext(), "not audible",Toast.LENGTH_SHORT).show();
						sliderval=0;
						bplay.setText("Play");
					}
					else{
						sliderval=d;
						bplay.setText("Stop");
					}
					
				}
				else{
					sliderval=0;
					bplay.setText("Play");
				}
			}
		});

		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		t = new Thread() {
			public void run() {
	         // set process priority
	         setPriority(Thread.MAX_PRIORITY);
	         // set the buffer size
	        int buffsize = AudioTrack.getMinBufferSize(sr, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
	        // create an audiotrack object
	        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sr, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffsize, AudioTrack.MODE_STREAM);

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