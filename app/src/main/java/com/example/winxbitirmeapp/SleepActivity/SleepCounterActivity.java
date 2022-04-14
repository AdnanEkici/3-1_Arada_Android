package com.example.winxbitirmeapp.SleepActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.HomeActivity;
import com.example.winxbitirmeapp.LoginActivity;
import com.example.winxbitirmeapp.Models.SleepDataModel;
import com.example.winxbitirmeapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SleepCounterActivity extends AppCompatActivity {

    private Chronometer counter;
    private SoundMeter soundMeter;
    private Runnable r;
    private final Handler h = new Handler();
    private ArrayList<SleepDataModel> soundData;
    private String token;
    private String tokenType;
    private final String URL = "http://10.2.37.108:8080/sleep";
    private String email;
    private String password;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepcounter);

        soundMeter = new SoundMeter();


        this.init();


    }


    //Life Actions


    //Public Actions



    @Override
    public void onBackPressed()
    {
        counter.stop();
        soundMeter.stop();
        h.removeCallbacks(r);
        postData();
        Intent intent = new Intent(SleepCounterActivity.this , SleepActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }


    //Private Actions
    private void init() {
        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        soundData = new ArrayList<>();
        r = new Runnable()
        {
            @Override
            public void run()
            {
                //TODO: save sound data
                Double sou = soundMeter.getAmplitude();
                System.out.println("Ses: " + sou);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E MMM d H:m:s O u", Locale.ENGLISH);
                OffsetDateTime odt = OffsetDateTime.parse(new Date().toString(), dtf);
                soundData.add(new SleepDataModel(odt.toString() , sou));
                //System.out.println("DATE: " + new Date());
                h.postDelayed(this, 1000);
            }
        };

        counter = (Chronometer) findViewById(R.id.counter); // initiate a chronometer
        counter.start();
        startVoiceDetection();
    }


    public void stopButtonAction(View view){
        counter.stop();
        soundMeter.stop();
        h.removeCallbacks(r);
        postData();
        System.out.println("HI");
       Intent intent = new Intent(SleepCounterActivity.this , SleepActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        startActivity(intent);
        finish();
    }

    private void postData(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        Gson gson = new Gson();

        String listString = gson.toJson(
                soundData,
                new TypeToken<ArrayList<SleepDataModel>>() {}.getType());

        try {
            JSONArray jsonArray =  new JSONArray(listString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(listString);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("ADOOOO: " + jsonArr);
        HashMap<String, JSONArray> params = new HashMap<>();
        params.put("model", jsonArr);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){

            //Headera gönder
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", tokenType + " " + token);
                return headers;
            }
        };

        queue.add(req);
    }


    private void startVoiceDetection() {
        //TODO: Do soundMeter.stop() control
        soundMeter.start();
        h.postDelayed(r , 1000); // 1 second delay (takes millis)
    }




}
