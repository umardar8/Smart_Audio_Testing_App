package project.smartatha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2*1000);
                    Intent i=new Intent(getBaseContext(),AfterMainActivity.class);
                    startActivity(i);
                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }



}