package ayyash.app.seamolec_udj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Abdul Rizal Adompo on 9/18/2016.
 */
public class MainActivity extends AppCompatActivity {

    Button btnSentIP;
    EditText txtIP;
    public String SIERRA_IP;
    public SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        txtIP =(EditText)findViewById(R.id.txtIP);
        btnSentIP = (Button) findViewById(R.id.btnSentIP);


    //tamban
        btnSentIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               if(txtIP.getText().toString().isEmpty()){
//                   Toast.makeText(MainActivity.this, "masukan IP Server", Toast.LENGTH_SHORT).show();
//               }else {

                   //for dinamis
                   //SIERRA_IP = txtIP.getText().toString();

                   //for develop
                   SIERRA_IP = "192.168.50.128";

                   // nyimpan IP di SP
                   sp = getSharedPreferences("",MODE_PRIVATE);
                   SharedPreferences.Editor ed = sp.edit();
                   ed.putString("IPnya", SIERRA_IP);
                   ed.commit();

                   Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                   startActivity(i);
                   finish();
               }
      //      }
        });
    }
}
