package ayyash.app.sierra_udj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Abdul Rizal Adompo on 24/09/2016.
 */
public class RegisterActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    public static final String KEY_NIS = "nis";
    public static final String KEY_PWD = "password";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_ID_KELAS = "id_kelas";
    Button btnRegister;
    private String ambilIP;
    private EditText nis, password, nama;
    private int ambilIDKelas;
    //kebutuhan buat list
    private Spinner spinner;
    //An ArrayList for Spinner Items
    private ArrayList<String> students = new ArrayList<>();
    //JSON Array
    private JSONArray result;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nis = (EditText) findViewById(R.id.nis);
        password = (EditText) findViewById(R.id.password);
        nama = (EditText) findViewById(R.id.nama);
        // id_kelas = (EditText)findViewById(R.id.id_kelas);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        //Initializing TextViews
        textViewName = (TextView) findViewById(R.id.textViewName);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nis.getText().toString().isEmpty() || password.getText().toString().isEmpty() || nama.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "pastikan semua field terisi", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser();
                }
//
//                Log.d("UYEEEE", a+ " " + b + " " + c + "  " +d );

            }
        });


        //ngambil IP
        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        ambilIP = sps.getString("IPnya", "");

        Toast.makeText(RegisterActivity.this, "IP Server: " + ambilIP, Toast.LENGTH_LONG).show();

        getListKelas();
    }


    private void getListKelas() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("http://" + ambilIP + "/new_udj/get_kelas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(ConfigKelas.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getNamaKelas(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getNamaKelas(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(ConfigKelas.TAG_NAMA_KEALS));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }


    //Method to get student name of a particular position

    private String getIdKelas(int position) {
        this.ambilIDKelas = position;
        String idKelasnya = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            idKelasnya = json.getString(ConfigKelas.TAG_ID_KELAS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return idKelasnya;
    }


    //coba simpan
    private void registerUser() {
        final String a = nis.getText().toString().trim();
        final String b = password.getText().toString().trim();
        final String c = nama.getText().toString().trim();

        //parsing id kelas
        final String sIdKelas = getIdKelas(ambilIDKelas);
        //final String sIdKelas = "100000";
        //final int saveIdKelas = Integer.parseInt(sIdKelas);

        StringRequest sR = new StringRequest(Request.Method.POST, "http://" + ambilIP + "/new_udj/insert.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_NIS, a);
                params.put(KEY_PWD, b);
                params.put(KEY_NAMA, c);
                params.put(KEY_ID_KELAS, sIdKelas);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sR);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textViewName.setText(getIdKelas(position)+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // nothing to do
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
