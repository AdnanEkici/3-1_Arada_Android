package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText email_edit , password_edit;
    private String email, password;
    private String token;
    private TextView ghostText;
    private CheckBox rememberMe;
    private SharedPreferences preferences;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.init();

    }



    //Life Actions





    //Public Actions





    //Private Actions
    private void init()
    {
        email_edit = findViewById(R.id.LoginEditTextEmailID);
        password_edit = findViewById(R.id.LoginEditTextPasswordID);
        ghostText = findViewById(R.id.GhostTextViewID);
        rememberMe = findViewById(R.id.rememberMe);
        preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("rememberMe", "");

        if (checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
            startActivity(intent);
            finish();//logout button lazim

        }else {

        }

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("rememberMe","true");
                    editor.apply();

                }else{
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("rememberMe","false");
                    editor.apply();

                }
            }
        });

    }






    //DB Actions






    //Button Actions

    public void loginBtnAction(View view)
    {
        // db gelince burasi degiscek
        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
        startActivity(intent);
        finish();
//        String email = "";
//        String password = "";
//
//        try
//        {
//            email = email_edit.getText().toString();
//        }catch(Exception e)
//        {
//            Toast.makeText(LoginActivity.this , "Email Adresini Giriniz." , Toast.LENGTH_SHORT).show();
//        }
//        try
//        {
//            password = password_edit.getText().toString();
//        }catch(Exception e)
//        {
//            Toast.makeText(LoginActivity.this , "Şifrenizi Giriniz." , Toast.LENGTH_SHORT).show();
//        }
//
//
//    /*
//        // db gelince burasi degiscek
//        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
//        startActivity(intent);
//        finish();
//    */
//
//
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        //String url ="http://10.2.36.41:8080/deneme";
//        //String URL = "http://10.2.38.242:8080/api/auth/signin";
//
//        final String URL = "http://10.2.38.242:8080/api/auth/signin";
//// Post params to be sent to the server
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("email", email);
//        params.put("password", password);
//
//        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("He: " + response.toString());
//                        JSONObject jsonObject = null;
//                        try {
//                            jsonObject = new JSONObject(String.valueOf(response));
//                            System.out.println("Bruh: " + jsonObject.getString("tokenType"));
//                            token = jsonObject.getString("tokenType");
//                            ghostText.setText(token.toString());
//                            System.out.println("Bruh: " + jsonObject.getString("accessToken"));
//                            //Alttaki yorumlu kod json arrayi okur
//                           // JSONArray jsonArray = jsonObject.getJSONArray("data");
//                           // for (int i = 0; i < jsonArray.length(); i++) {
//                           //     JSONObject jo = jsonArray.getJSONObject(i);
//                           //     System.out.println("Bruh: " + jo.getString("tokenType"));
//                           // }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        }){
//
//           //Headera gönder
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json");
//                headers.put("winx", "mokoko");
//                return headers;
//            }
//        };
//
//// add the request object to the queue to be executed
//        queue.add(req);
//        //System.out.println("ADO:" + ghostText.getText());
//
//
//
//      /*  try {
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            String URL = "http://10.2.38.242:8080/api/auth/signin";
//            JSONObject jsonBody = new JSONObject();
//            jsonBody.put("email", email);
//            jsonBody.put("password", password);
//            final String requestBody = jsonBody.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response)
//                {
//                    JSONObject jsonObj = null;
//                    try {
//                        jsonObj = new JSONObject(response);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        String response_value = jsonObj.getString("response");
//                        System.out.println("Hi:" + response_value);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return requestBody == null ? null : requestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                        // can get more details such as response.headers
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            requestQueue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }*/

    }

    public void signInBtnAction(View view)
    {
        //System.out.println("ADO:" + ghostText.getText());
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);
        finish();

    }

    public void moveQuestions(View view)
    {
        Intent intent = new Intent(LoginActivity.this , QuestionnaireActivity.class);
        startActivity(intent);
        finish();
    }


    }
