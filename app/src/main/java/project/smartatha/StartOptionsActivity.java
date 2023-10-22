package project.smartatha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartOptionsActivity extends AppCompatActivity {

	Button manualhearing;
	Button dispersehumanbutton;
	Button animalbutton;
	Button hearingaid;
	Button backbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_options);
		manualhearing=(Button)findViewById(R.id.ManualHearingButton);
		dispersehumanbutton=(Button)findViewById(R.id.b2);
		animalbutton=(Button)findViewById(R.id.bplus20);
		hearingaid=(Button)findViewById(R.id.HearingAid);
		backbutton=(Button)findViewById(R.id.BackButton);

		manualhearing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),FirstScreen.class);
				startActivity(i);
			}
		});
		
		dispersehumanbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),DisperesHumanActivity.class);
                startActivity(i);
			}
		});

		animalbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),Animal.class);
				startActivity(i);
			}
		});

		hearingaid.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(getBaseContext(),TOne.class);
				startActivity(i);
			}
		});

		backbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}



}
