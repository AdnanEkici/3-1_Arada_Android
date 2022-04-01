package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import com.example.winxbitirmeapp.toDoAndAchivements.ToDoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText email_edit , password_edit;
    private String email, password;
    private TextView ghostText;
    private CheckBox rememberMe;
    private SharedPreferences preferences;
    private ProgressDialog dialog;
    private String token = "A"; // A ve B yi sil
    private String tokenType = "B";





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.checkInternet();
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
           // Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
           // startActivity(intent);
           // finish();//logout button lazim

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
        String email = "";
        String password = "";

        //Yorumu Aç
        dialog = new ProgressDialog(LoginActivity.this , R.style.AppCompatAlertDialogStyle);
        dialog.setMessage("Yükleniyor");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        try
        {
            email = email_edit.getText().toString();
        }catch(Exception e)
        {
            Toast.makeText(LoginActivity.this , "Email Adresini Giriniz." , Toast.LENGTH_SHORT).show();
        }
        try
        {
            password = password_edit.getText().toString();
        }catch(Exception e)
        {
            Toast.makeText(LoginActivity.this , "Şifrenizi Giriniz." , Toast.LENGTH_SHORT).show();
        }


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        final String URL = "http://10.2.38.96:8080/user/signin";
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("username", email);
        params.put("password", password);

        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response177: " + response.toString());
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            tokenType = jsonObject.getString("tokenType");
                            token = jsonObject.getString("accessToken");

                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
                            intent.putExtra("token", token);
                            intent.putExtra("tokenType", tokenType);
                            startActivity(intent);
                            finish();

                            //Alttaki yorumlu kod json arrayi okur
                           // JSONArray jsonArray = jsonObject.getJSONArray("data");
                           // for (int i = 0; i < jsonArray.length(); i++) {
                           //     JSONObject jo = jsonArray.getJSONObject(i);
                           //     System.out.println("Bruh: " + jo.getString("tokenType"));
                           // }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(LoginActivity.this ,"Email veya şifre hatalı." , Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }){

           //Headera gönder
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("winx", "mokoko");
                return headers;
            }
        };

        // add the request object to the queue to be executed
        queue.add(req);

    }

    public void signInBtnAction(View view)
    {
        Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(intent);

    }

    public void moveQuestions(View view)
    {
        Intent intent = new Intent(LoginActivity.this , QuestionnaireActivity.class);
        startActivity(intent);
        finish();
    }

    //Bu metot oncreatete setcontentview'ın hemen altda çağrılmalı
    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Bağlantı Problemi");
            alertDialog.setIcon(getResources().getDrawable(R.drawable.nonnet));
            alertDialog.setMessage("Cihazınız internete bağlı değil.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            System.exit(0);
                        }
                    });
            alertDialog.show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }



    }
