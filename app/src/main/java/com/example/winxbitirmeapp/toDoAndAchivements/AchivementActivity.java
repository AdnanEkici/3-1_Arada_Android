package com.example.winxbitirmeapp.toDoAndAchivements;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.Adapters.ToDoListAdapter;
import com.example.winxbitirmeapp.LoginActivity;
import com.example.winxbitirmeapp.Models.ToDoModel;
import com.example.winxbitirmeapp.R;
import com.example.winxbitirmeapp.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AchivementActivity extends AppCompatActivity {

    //OnBackPressed Ekle
    private String token;
    private String tokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement);


        this.init();
        this.getAchievement();
    }

    private void init()
    {
        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
    }


    private void getAchievement()
    {

        RequestQueue queue = Volley.newRequestQueue(this);

        final String URL = "http://10.5.37.112:8080/achievement";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<>();
        params.put("none", "none");

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            JSONArray jsonArray = jsonObject.getJSONArray("modelList");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                System.out.println("ARRAY: " + jo.toString());


                            }


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





}