package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;
import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.telephony.ClosedSubscriberGroupInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.Date;


public class RegisterActivity extends AppCompatActivity{

    public Button dateButton,loginButton;
    private DatePickerDialog datePickerDialog;

    private EditText firstNameText, lastnameText, passwordText, emailText;
    private RadioGroup radioGroup;

    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String dateForDatabase;

    private final String URL = "http://10.2.37.139:8080/user/signup";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.init();


    }

    //Private Actions
    private void init()
    {
        initDatePicker();
        dateButton = (AppCompatButton) findViewById(R.id.BirthdayPickerButton);
        loginButton = (MaterialButton) findViewById(R.id.SignInButton);
        dateButton.setText("Birthdate");

        firstNameText = findViewById(R.id.FirstNameEditText);
        lastnameText = findViewById(R.id.LastNameEditText);
        passwordText = findViewById(R.id.PasswordEditText);
        emailText = findViewById(R.id.EmailEditText);

        radioGroup = findViewById(R.id.GenderPicker);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(RegisterActivity.this).create();
            alertDialog.setTitle("Ba??lant?? Problemi");
            //alertDialog.setIcon(getResources().getDrawable(R.drawable.nonnet));
            alertDialog.setMessage("Cihaz??n??z internete ba??l?? de??il.");
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

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                //System.out.println("date ---> " + date);
                try {
                    dateForDatabase = makeDateStringForDatabase(day, month, year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());



    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String makeDateStringForDatabase(int day, int month, int year) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(month < 10){
            String dateStr = year + "-" + "0" + month + "-" + day;
            Date date = simpleDateFormat.parse(dateStr);
            return dateStr;
        }
        else{
            String dateStr = year + "-" + month + "-" + day;
            Date date = simpleDateFormat.parse(dateStr);
            return dateStr;
        }
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


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

    //Button Action
    public void registerBtn(View view)//Kontrolleri failsafeleri yap??lmal??d??r.
    {
        //Date al??nmad??;
        String name = firstNameText.getText().toString();
        String surname = lastnameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        int genid=radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(genid);
        String gender=radioButton.getText().toString();

        if (name.length()==0){
            firstNameText.setError("Enter name");
        }
        else if (surname.length()==0){
            lastnameText.setError("Enter surname");
        }
        else if (email.length()==0){
            emailText.setError("Enter an email");
        }
        else if (!isValid(email)){
            emailText.setError("Enter a valid email");
        }
        else if (password.length()<6){
            passwordText.setError("Enter a valid password");
        }
        //BURAYA DEVAM ET KONTROLLERE HATA OLDUGU ICIN KONTROL EDEMEDIM



        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            //if else icine al
                            assert firebaseUser != null;
                            String userEmail = firebaseUser.getEmail();
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("email",userEmail);
                            hashMap.put("chatClick","0"); //logout ta 0 yap burayi
                            hashMap.put("isMatched","0"); //logout ta 0 yap burayi
                            hashMap.put("matchedEmail","-1");
                            hashMap.put("isOnline","0");

                            //username falan koymadim

                            FirebaseFirestore.getInstance().collection("User")
                                    .document(userEmail)
                                    .set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("SUCCESSFUL ADD");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("FAILED TO ADD");
                                }
                            });

                        }else{
                            Toast.makeText(RegisterActivity.this,"BRUH YOU CANT REGISTER",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            String URL = "http://10.2.36.78:8080/user/signup";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("username", email);
            jsonBody.put("password", password);
            jsonBody.put("name", name);
            jsonBody.put("surname", surname);
            jsonBody.put("gender", gender.toUpperCase());
            jsonBody.put("birthDate", dateForDatabase);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Bruh" + response);
                    if (response.contains("200"))
                    {
                        Toast.makeText(RegisterActivity.this , "Kay??t Ba??ar??l?? L??tfen Giri?? Yap??n??z" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this , "Ba??ar??s??z kay??t l??tfen tekrar deneyin" , Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this , "Bu email adresi kullan??lmaktad??r." , Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        //System.out.println("response -----> " + responseString);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    public void gotoSignIn(View view) {
        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
        startActivity(intent);
    }
}