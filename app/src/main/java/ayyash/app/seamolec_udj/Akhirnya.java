package ayyash.app.seamolec_udj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Abdul Rizal Adompo on 24/09/2016.
 */

public class Akhirnya extends Activity {

    TextView tvJawabanBenar;
    TextView tvJawabanPersen;
    Button btnAkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvJawabanBenar = (TextView) findViewById(R.id.tvJawabanBenar);
        tvJawabanPersen = (TextView) findViewById(R.id.tvJawabanPersen);
        btnAkhir = (Button) findViewById(R.id.tombolAkhir);

        Intent intent = getIntent();
        int total = intent.getExtras().getInt("answer_total");
        int benar = intent.getExtras().getInt("answer_true");

        tvJawabanBenar.setText(benar + " dari " + total+" soal");
        tvJawabanPersen.setText((benar * 100 / total)+"%");


        btnAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }
}
