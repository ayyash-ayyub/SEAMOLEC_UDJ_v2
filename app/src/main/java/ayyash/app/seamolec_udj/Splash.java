package ayyash.app.seamolec_udj;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Abdul Rizal Adompo on 9/18/2016.
 */
public class Splash extends AppCompatActivity {

    private static int splashInterval = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            Log.e("EXIT", "bye, bye Activity...");
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                    finish();


                    //jeda selesai Splashscreen
                    this.finish();
                }

                private void finish() {
                    // TODO Auto-generated method stub

                }
            }, splashInterval);
        }
    }
}

