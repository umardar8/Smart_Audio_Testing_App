package project.smartatha;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AfterMainActivity extends AppCompatActivity {

	Button startbutton;
	Button aboutusbutton;
	Button helpbutton;
	Button exitbutton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_after_main);
		startbutton=(Button)findViewById(R.id.b1);
		aboutusbutton=(Button)findViewById(R.id.b3);
		helpbutton=(Button)findViewById(R.id.b2);
		exitbutton=(Button)findViewById(R.id.BackButton);
		
		helpbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),HelpActivity.class);
                startActivity(i);
				
			}
		});
		
		aboutusbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),AboutUsActivity.class);
                startActivity(i);
				
			}
		});
		
		startbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getBaseContext(),StartOptionsActivity.class);
                startActivity(i);
				
			}
		});

		exitbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	
	
	}



}
