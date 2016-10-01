package ayyash.app.seamolec_udj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Abdul Rizal Adompo on 9/18/2016.
 */
public class PaketSoalActivity extends AppCompatActivity {

    //current
    public static final String KEY_NAMA = "nama";
    public static final String KEY_NIS = "NIS";
    public static final String KEY_ID_KELAS = "id_kelas";
    public TextView tampilCurrentUser;
    List<ModelQuiz> GetDataAdapter1;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    ProgressBar progressBar;
    public SharedPreferences sp;
    String nis;
    //String GET_JSON_DATA_HTTP_URL = "http://192.168.50.38/new_udj/jsonData.php";
    String JSON_ID_KELAS = "id_kelas";
    String JSON_NAMA_QUIZ = "nama_quiz";
    String JSON_ID_QUIZ = "id_quiz";
    String JSON_TGL_SELESAI = "tgl_selesai";
    String JSON_DURASI = "durasi";
    Button buttonLoadPaket;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String nama = "";
    String cur_id_kelas = "";
    private String ambilIP;
    private ArrayList<String> students = new ArrayList<>();
    private JSONArray result;
    private ProgressDialog loading;
    TextView txtAwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_paket_soal);

        tampilCurrentUser = (TextView) findViewById(R.id.textView6);
        buttonLoadPaket = (Button) findViewById(R.id.buttonLoadPaket);
        txtAwal = (TextView)findViewById(R.id.txtAwal);

        /* Top toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        /* Bottom toolbar. */

        //proses load list soal
        loadListPaket();

        SharedPreferences sharedPreferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        nis = sharedPreferences.getString(ConfigUmum.NIS_SHARED_PREF, "tidak tersedia");

        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        ambilIP = sps.getString("IPnya", "");

        Toast.makeText(PaketSoalActivity.this, "Connected! : " + ambilIP, Toast.LENGTH_LONG).show();

        cocokanCurrentUser();
        tampilCurrentUser.setText("selamat datang:  : " + nis);


        // // nyimpan NIS  di SP

        sp = getSharedPreferences("",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("NISnya", nis);
        ed.commit();

    }

    private void loadListPaket() {
        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        buttonLoadPaket = (Button) findViewById(R.id.buttonLoadPaket);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        buttonLoadPaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                txtAwal.setVisibility(View.GONE);
                JSON_DATA_WEB_CALL();

            }
        });

    }

    public void JSON_DATA_WEB_CALL() {
        String eco = cur_id_kelas;
        jsonArrayRequest = new JsonArrayRequest("http://" + ambilIP + "/new_udj/loadQuiz.php?id_kelas=" + eco,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        txtAwal.setVisibility(View.GONE);

                        //aktifkan
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);

//                       try {
//                           for (int i = 0; i < response.length(); i++) {
//                               JSONObject row = response.getJSONObject(i);
//                               ambilIDKelas = row.getInt("id_kelas");
//                               ambilNamaQuiz = row.getString("nama_quiz");
//                           }
//                           Toast.makeText(getApplicationContext(),"ID Kelas : "+ambilIDKelas+ "Nama Quiz : " +ambilNamaQuiz, Toast.LENGTH_SHORT).show();
//                       } catch (JSONException e) {
//                           e.printStackTrace();
//                       }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Quiz Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        txtAwal.setVisibility(View.VISIBLE);
                        txtAwal.setText("Quiz tidak ditemukan.");
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            ModelQuiz GetDataAdapter2 = new ModelQuiz();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId_quiz(json.getInt(JSON_ID_QUIZ));
                GetDataAdapter2.setNama_quiz(json.getString(JSON_NAMA_QUIZ));
                //  GetDataAdapter2.setPassword(json.getString(JSON_PASSWORD));
                GetDataAdapter2.setTgl_selesai(json.getString(JSON_TGL_SELESAI));
                GetDataAdapter2.setDurasi(json.getInt(JSON_DURASI));

                // String aa = GetDataAdapter2.setNama(json.getString(JSON_NAMA).toString());
                tampilCurrentUser.setText("LoginActivity as: " + nama);


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerViewQuizAdapter(GetDataAdapter1, this);


        recyclerView.setAdapter(recyclerViewadapter);


    }

    private void cocokanCurrentUser() {
        // "http://"+ambilIP+"/new_udj/get_current_user.php?id="
        String eco = nis;

        loading = ProgressDialog.show(this, "Loading...", "Fetching...", false, false);

        String url = "http://" + ambilIP + "/new_udj/get_current_user.php?nis=" + eco;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaketSoalActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        String niss = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigLogin.JSON_ARRAY);
            JSONObject ar = result.getJSONObject(0);
            niss = ar.getString(ConfigLogin.KEY_CURRENT_NIS);
            nama = ar.getString(ConfigLogin.KEY_CURRENT_NAMA);
            cur_id_kelas = ar.getString(ConfigLogin.KEY_CURRENT_ID_KELAS);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("eco: ", response);
    }


    private void logout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Anda akan logout dari aplikasi?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        SharedPreferences preferences = getSharedPreferences(ConfigUmum.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(ConfigUmum.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(ConfigUmum.NIS_SHARED_PREF, "");
                        editor.commit();

                        //clear sp IP

                        Intent intent = new Intent(PaketSoalActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {

            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
