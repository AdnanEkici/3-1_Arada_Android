package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView firstNameText, lastnameText, emailText, birthdateText, genderText;
    private RelativeLayout profileScreen;
    private ScrollView settingsScreen;

    private String name = "";
    private String surname = "";
    private String gender = "";
    private String email = "";
    private String birthdate = "";

    private String token;
    private String tokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        this.init();
    }

    //Private Actions
    private void init()
    {
        firstNameText = findViewById(R.id.FirstNameTextView);
        lastnameText = findViewById(R.id.LastNameTextView);
        emailText = findViewById(R.id.EmailTextView);
        birthdateText = findViewById(R.id.BirthdateTextView);
        genderText = findViewById(R.id.GenderTextView);
        profileScreen = findViewById(R.id.profileLayout);
        settingsScreen = findViewById(R.id.settingsLayout);

        profileScreen.setVisibility(View.VISIBLE);
        settingsScreen.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
        System.out.println("Bruh11:" + tokenType + " " + token);
        this.getUserDataFrom();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;

            switch (item.getItemId()){
                case R.id.sleep:
                    intent = new Intent(ProfileActivity.this , SleepActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.yoga:
                    intent = new Intent(ProfileActivity.this , MeditationActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.chat:
                    intent = new Intent(ProfileActivity.this , ChatActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.profile:
                    intent = new Intent(ProfileActivity.this , ProfileActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            return true;
        }
    };

    private void getUserDataFrom()
    {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        final String URL = "http://192.168.1.82:8080/profile";
      // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("email", email);
        //params.put("password", password);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("He: " + response.toString());

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            System.out.println("Bruh140:" + jsonObject);
                            name = jsonObject.getString("name");
                            surname = jsonObject.getString("surname");
                            gender = jsonObject.getString("gender");
                            email = jsonObject.getString("email");
                            birthdate = jsonObject.getString("birthDate");

                            System.out.println("Bruh 142: " + name);
                            firstNameText.setText(name);
                            lastnameText.setText(surname);
                            emailText.setText(email);
                            birthdateText.setText("14/02/1998");
                            genderText.setText(gender);
                            //Alttaki yorumlu kod json arrayi okur
                           // JSONArray jsonArray = jsonObject.getJSONArray("data");
                           // for (int i = 0; i < jsonArray.length(); i++)
                            //{
                           //     JSONObject jo = jsonArray.getJSONObject(i);
                           //     System.out.println("Bruh: " + jo.getString("tokenType"));
                           // }

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

        // add the request object to the queue to be executed
        queue.add(req);



    }

    public void settingsButton(View view){
        profileScreen.setVisibility(View.INVISIBLE);
        settingsScreen.setVisibility(View.VISIBLE);
    }

    public void profilePictureChangeButton(View view){
        profileScreen.setVisibility(View.INVISIBLE);
        settingsScreen.setVisibility(View.VISIBLE);
    }

    public void saveBtnAction(View view){
        finish();
        startActivity(getIntent());
    }

    public void cancelBtnAction(View view){
        finish();
        startActivity(getIntent());
    }


}

