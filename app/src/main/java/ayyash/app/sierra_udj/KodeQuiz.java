package ayyash.app.sierra_udj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class KodeQuiz extends AppCompatActivity {

    public static final String KEY_KODE = "kdQuiz";

    String ambilIP;
    private int a, id_quiz;
    Button btnSubmitKode;
    EditText txtQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kode_quiz);
        Intent i = getIntent();
        a = i.getIntExtra("kirimanDurasi", 0);
        id_quiz = i.getIntExtra("kirimanIDQuiz", 0);

        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        ambilIP = sps.getString("IPnya", "");

        txtQuiz = (EditText)findViewById(R.id.txtKode);

        btnSubmitKode = (Button)findViewById(R.id.btnSubmitKode);
        btnSubmitKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CekQuiz();
            }
        });

    }

    private void CekQuiz(){
        final String kdQuiz = txtQuiz.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ambilIP + "/new_udj/get_quiz_code.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Sukses")) {

                            Intent i = new Intent(KodeQuiz.this, SoalActivity.class);
                            i.putExtra("kirimanIDQuiz", id_quiz);
                            i.putExtra("kirimanDurasi", a);
                            startActivity(i);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            finish();

                        } else {

                            Toast.makeText(KodeQuiz.this, "Kode Kuis yang anda masukan tidak sesuai", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_KODE, kdQuiz);



                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
