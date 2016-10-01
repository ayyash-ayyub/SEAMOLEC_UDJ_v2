package ayyash.app.seamolec_udj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Abdul Rizal Adompo on 24/09/2016.
 */
public class SoalActivity extends AppCompatActivity {

    TextView tv, tempatWaktu;
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    ProgressBar progressBar;
    LinearLayout mLinearLayout;

    //script tampan tanggal 9 sept
    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 0;
    private CountDownTimer countDownTimer; // built in android class
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off
    private int a;

    List<ModelSoal> listSoal;

    String JSON_ID_SOAL = "id_soal";
    String JSON_QUESTION_TEXT = "question_text";
    String JSON_GAMBAR = "gambar";
    String JSON_OPSI_SOAL = "opsi";

    String JSON_ANSWER_TEXT = "text";
    String JSON_ANSWER_STATUS = "status";

    private int id_quiz;
    private int answer_soal;
    private int answer_benar;
    private int nRadio;
    private int nJawab;
    private String ambilIP;


    //ulala
    public static final String KEY_ID_QUIZ = "id_quiz";
    public static final String KEY_NIS = "nis";
    public static final String KEY_NILAI = "nilai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        tv = (TextView) findViewById(R.id.textView8);
        tempatWaktu = (TextView) findViewById(R.id.textView10);
        Intent i = getIntent();
        a = i.getIntExtra("kirimanDurasi", 0);
       // a = 1;


        id_quiz = i.getIntExtra("kirimanIDQuiz", 0);
        tv.setText("Menjawab 0/0 soal");
        timerHariKamis();
        tempatWaktu.setText("Waktu tersisa: ");

        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        ambilIP = sps.getString("IPnya", "");


        loadListSoal();

        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);

        String[] Soal = {"Berapa?", "Siapa?", "Kapan?", "Dimana?"};
        String[][] opsiSoal = {
                {"1", "2", "3"},
                {"saya", "kamu", "dia", "mereka", "kita"},
                {"sekarang", "waktu itu"},
                {"disini", "disana"}
        };

////        int jml_soal = opsiSoal.length - 1;
////        for (int x =0; x<Soal.length; x++){
////            System.out.println("soal "+ Soal[x]);
////            for (int y=0; y < opsiSoal[x].length; y++){
////                System.out.println(opsiSoal[x][y]);
////            }
////        }
//
//        for (int k = 0; k < Soal.length; k++) {
//            //create text button
//
//            TextView tempatSoal = new TextView(this);
//            tempatSoal.setText(Soal[k]);
//            tempatSoal.setTextColor(Color.BLUE);
//            mLinearLayout.addView(tempatSoal);
//            // create radio button
//            final RadioButton[] rb = new RadioButton[6];
//            RadioGroup rg = new RadioGroup(this);
//            rg.setOrientation(RadioGroup.VERTICAL);
//            for (int l=0; l<opsiSoal[k].length; l++ ){
//                rb[l] = new RadioButton(this);
//                rg.addView(rb[l]);
//                rb[l].setText(opsiSoal[k][l]);
//            }
//
//            mLinearLayout.addView(rg);
//        }
    }

    private void loadListSoal() {
        listSoal = new ArrayList<ModelSoal>();
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        // progressBar.setVisibility(View.GONE);
        //buttonLoadPaket = (Button) findViewById(R.id.buttonLoadPaket);
        //recyclerView.setHasFixedSize(true);
        //recyclerViewlayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(recyclerViewlayoutManager);
        /*buttonLoadPaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                JSON_DATA_WEB_CALL();
            }
        });*/
        JSON_DATA_WEB_CALL();
    }

    public void JSON_DATA_WEB_CALL() {
        int eco = id_quiz;
        //tv.setText("http://" + ambilIP + "/new_udj/list_soal.php?id_quiz=" + eco);
        jsonArrayRequest = new JsonArrayRequest("http://" + ambilIP + "/new_udj/list_soal.php?id_quiz=" + eco,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //tv.setText("load selesai");
                        //     progressBar.setVisibility(View.GONE);
                        //Log.d("DEBUG", response.toString());

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
                        error.printStackTrace();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {
        //tv.setText("parsing...");
        answer_soal = array.length();
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            ModelSoal mSoal = new ModelSoal();

            try {
                json = array.getJSONObject(i);

                //Log.d("DEBUG", json.getString(JSON_QUESTION_TEXT));
                mSoal.setId_soal(json.getInt(JSON_ID_SOAL));
                mSoal.setText(json.getString(JSON_QUESTION_TEXT));
                mSoal.setGambar(json.getString(JSON_GAMBAR));
                //busuk
                JSONArray arrayOpsi = json.getJSONArray(JSON_OPSI_SOAL);
                for (int j = 0; j < arrayOpsi.length(); j++) {
                    JSONObject jsonOpsi = arrayOpsi.getJSONObject(j);
                    ModelOpsiSoal mOpsiSoal = new ModelOpsiSoal();

                    mOpsiSoal.setText(jsonOpsi.getString(JSON_ANSWER_TEXT));
                    if (jsonOpsi.getString(JSON_ANSWER_STATUS).equals("1")) {
                        mOpsiSoal.setStatus(true);
                    } else {
                        mOpsiSoal.setStatus(false);
                    }

                    mSoal.addOpsiSoal(mOpsiSoal);
                }


                listSoal.add(mSoal);
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        tv.setText("Menjawab 0/" + array.length() + " soal");

        loadTampilan();
    }

    private void loadTampilan() {
        //Log.d("DEBUG", "adding soal...");
        for (int k = 0; k < listSoal.size(); k++) {
            //Log.d("DEBUG", "soal ke-"+k);
            // get Model
            ModelSoal mSoal = listSoal.get(k);

            //create text button
            TextView tempatSoal = new TextView(this);
            ImageView gambar = new ImageView(this);

            Picasso.with(getApplicationContext()).load("http://" + ambilIP + "/udj/assets/img/soal/"+mSoal.getGambar()).into(gambar);

            System.out.println("gambar"+mSoal.getGambar());
            tempatSoal.setText((k + 1) + ". " + mSoal.getText());
            tempatSoal.setTextColor(Color.BLUE);
            mLinearLayout.addView(tempatSoal);
            mLinearLayout.addView(gambar);

            // create radio button
            final int opsiSize = mSoal.getModelOpsiSoal().size();
            final RadioButton[] rb = new RadioButton[opsiSize];
            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(RadioGroup.VERTICAL);
            //Log.d("DEBUG", "jumlah opsi soal: "+opsiSize);
            for (int i1 = 0; i1 < opsiSize; i1++) {
                //Log.d("DEBUG", "opsi soal ke-"+i1);
                ModelOpsiSoal mOpsiSoal = mSoal.getModelOpsiSoal().get(i1);
                rb[i1] = new RadioButton(this);
                rg.addView(rb[i1]);
                rb[i1].setText(mOpsiSoal.getText());
                rb[i1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // cek sesuatu
                        hitungTerjawab();
                        tv.setText("Menjawab " + nJawab + "/" + nRadio + " soal");
                    }
                });
            }
            mLinearLayout.addView(rg);
        }


        Button btnSubmit = new Button(this);
        btnSubmit.setText("Submit Jawaban!");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pemanis belaka
                // hitungTerjawab();


                isCanceled = true;
                countDownTimer.cancel();
                hitungNilai();
            }
        });
        mLinearLayout.addView(btnSubmit);
    }

    private void hitungNilai(){
        answer_benar = 0;
        int index_soal = 0;
        int child = mLinearLayout.getChildCount();
        for (int i = 0; i < child; i++) {
            if (mLinearLayout.getChildAt(i) instanceof RadioGroup) {
                RadioGroup nrg = (RadioGroup) mLinearLayout.getChildAt(i);
                int radioButtonID = nrg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(radioButtonID);
                int idx = nrg.indexOfChild(radioButton);

                if(idx != -1){
                    ModelSoal tmSoal = listSoal.get(index_soal);
                    ModelOpsiSoal tmOpsiSoal = tmSoal.getModelOpsiSoal().get(idx);
                    if (tmOpsiSoal.isStatus()) {
                        answer_benar++;
                    }
                }
                index_soal++;
            }
        }
        Log.d("RADIO_BUTTON", "Jawaban Benar: " + answer_benar);
        simpanUye();

    }

    private void hitungTerjawab(){
        int layoutChild = mLinearLayout.getChildCount();
        nRadio = 0;
        nJawab = 0;
        for (int i = 0; i < layoutChild; i++) {
            if (mLinearLayout.getChildAt(i) instanceof RadioGroup) {
                nRadio++;
                RadioGroup nrg = (RadioGroup) mLinearLayout.getChildAt(i);
                if (nrg.getCheckedRadioButtonId() != -1) {
                    nJawab++;
                }
            }
        }

    }

    public void simpanUye() {
        isPaused = true;
        isCanceled = true;

        SharedPreferences sps = getSharedPreferences("", MODE_PRIVATE);
        String ambilNIS = sps.getString("NISnya", "");

        final String a = String.valueOf(id_quiz).toString().trim();
        final String b = String.valueOf(ambilNIS).trim();
        final String c = String.valueOf(answer_benar*100/answer_soal).trim();
        //benar * 100 / total

     //   Toast.makeText(getApplicationContext(), "Hasil: "+a+" : "+b+" : "+c,Toast.LENGTH_LONG).show();

        StringRequest sR = new StringRequest(Request.Method.POST, "http://" + ambilIP + "/new_udj/simpanNilai.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(SoalActivity.this, response, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SoalActivity.this, Akhirnya.class);
                        intent.putExtra("answer_total", answer_soal);
                        intent.putExtra("answer_true", answer_benar);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SoalActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID_QUIZ, a);
                params.put(KEY_NIS, b);
                params.put(KEY_NILAI, c);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sR);
    }

    public void timerHariKamis() {

        isPaused = false;
        isCanceled = false;
        int time = 60;

        totalTimeCountInMilliseconds = a * time * 1000;
        timeBlinkInMilliseconds = 30 * 1000;

        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                if (isPaused || isCanceled) {
                    Log.e("TIMER", "Timer dibatalkan");
                    cancel();
                } else {
                    if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                        //   timerTampil.setTextAppearance(getApplicationContext(),R.style.normalText);
                        tempatWaktu.setTextColor(Color.rgb(255, 255, 255));
                        // change the style of the textview .. giving a red
                        // alert style


                        if (blink) {
                            tempatWaktu.setVisibility(View.VISIBLE);
                            tempatWaktu.setTextColor(Color.RED);
                            // if blink is true, textview will be visible
                        } else {
                            tempatWaktu.setVisibility(View.INVISIBLE);
                            tempatWaktu.setTextColor(Color.BLACK);
                        }

                        blink = !blink; // toggle the value of blink
                    }
                }
                tempatWaktu.setText("sisa waktu: " + String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                isPaused = true;
                isPaused = true;

                hitungNilai();
                Toast.makeText(getApplicationContext(), "Waktu Habis", Toast.LENGTH_LONG).show();
                finish();
            }


        }.start();

    }


}
