package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    private EditText email_edit , password_edit;
    private String email, password;
    private TextView ghostText;
    private CheckBox rememberMe;
    private SharedPreferences preferences;
    private ProgressDialog dialog;
    private String token;
    private String tokenType;
    private boolean flag = false;

    private FirebaseAuth auth;





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
        rememberMe = findViewById(R.id.rememberMe);
        preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("rememberMe", "");


        auth = FirebaseAuth.getInstance();

        if(!flag)
        {
            if (checkbox.equals("true"))
            {
                rememberMe.setChecked(true);

                dialog = new ProgressDialog(LoginActivity.this , R.style.AppCompatAlertDialogStyle);
                dialog.setMessage("Yükleniyor");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();
                String emailFromPref = preferences.getString("email", "def");
                String passFromPref = preferences.getString("password", "def");
                RequestQueue queue = Volley.newRequestQueue(this);
                auth.signInWithEmailAndPassword(emailFromPref, passFromPref)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseFirestore.getInstance().collection("User").document(auth.getCurrentUser().getEmail())
                                            .update("isOnline", "1").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused)
                                        {

                                            // Instantiate the RequestQueue.
                                            final String URL = "http://10.5.37.112:8080/user/signin";
                                            // Post params to be sent to the server
                                            HashMap<String, String> params = new HashMap<String, String>();
                                            params.put("email", emailFromPref);
                                            params.put("username", emailFromPref);
                                            params.put("password", passFromPref);

                                            JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
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
                                            if(dialog.isShowing())
                                            {
                                                dialog.dismiss();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {


                                        }
                                    });
                                } else {
                                    if(dialog.isShowing())
                                    {
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });




            }else
            {
                rememberMe.setChecked(false);
            }
        }


    }



    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Bağlantı Problemi");
            //alertDialog.setIcon(getResources().getDrawable(R.drawable.nonnet));
            alertDialog.setMessage("Cihazınız internete bağlı değil.");
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Tamam",
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

    //DB Actions






    //Button Actions

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&-]+)@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void loginBtnAction(View view)
    {



        flag = true;
        RequestQueue queue = Volley.newRequestQueue(this);
        // db gelince burasi degiscek


        String email = email_edit.getText().toString();
        String password = password_edit.getText().toString();

        if (email.length()==0){
            email_edit.setError("Enter an email");
        }
        else if (!isValid(email)){
            email_edit.setError("Enter a valid email");
        }
        else if (password.length()<6){
            password_edit.setError("Enter a valid password");
        }else {
            dialog = new ProgressDialog(LoginActivity.this , R.style.AppCompatAlertDialogStyle);
            dialog.setMessage("Yükleniyor");
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseFirestore.getInstance().collection("User").document(auth.getCurrentUser().getEmail())
                                        .update("isOnline", "1").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused)
                                    {

                                        // Instantiate the RequestQueue.
                                        final String URL = "http://10.5.37.112:8080/user/signin";
                                        // Post params to be sent to the server
                                        HashMap<String, String> params = new HashMap<String, String>();
                                        params.put("email", email);
                                        params.put("username", email);
                                        params.put("password", password);

                                        JsonObjectRequest req = new JsonObjectRequest(URL, new JSONObject(params),
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        JSONObject jsonObject = null;
                                                        try {
                                                            jsonObject = new JSONObject(String.valueOf(response));
                                                            tokenType = jsonObject.getString("tokenType");
                                                            token = jsonObject.getString("accessToken");

                                                            if(dialog.isShowing())
                                                            {
                                                                dialog.dismiss();
                                                            }
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
                                                            if(dialog.isShowing())
                                                            {
                                                                dialog.dismiss();
                                                            }
                                                        }


                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                if(dialog.isShowing())
                                                {
                                                    dialog.dismiss();
                                                }
                                                VolleyLog.e("Error: ", error.getMessage());
                                                Toast.makeText(LoginActivity.this ,"Email veya şifre hatalı." , Toast.LENGTH_LONG).show();
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
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        if(dialog.isShowing())
                                        {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                if(dialog.isShowing())
                                {
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
        }



        dialog = new ProgressDialog(LoginActivity.this , R.style.AppCompatAlertDialogStyle);

        if(rememberMe.isChecked())
        {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("rememberMe","true");
            editor.putString("email", email_edit.getText().toString());
            editor.putString("password", password_edit.getText().toString());
            editor.apply();
        }
        else
        {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("rememberMe","false");
            editor.putString("email",  "");
            editor.putString("password","");
            editor.apply();
        }


        // Instantiate the RequestQueue.
        final String URL = "http://10.5.39.181:8080/user/signin";
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
                            //System.out.println("Bruh182: " + jsonObject.getString("accessToken"));
                            //System.out.println("Bruh183: " + token);

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

}