package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.winxbitirmeapp.ChatActivities.ChatActivity;
import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.example.winxbitirmeapp.toDoAndAchivements.AchivementActivity;
import com.example.winxbitirmeapp.toDoAndAchivements.ToDoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView firstNameText, lastnameText, emailText, birthdateText, genderText;
    private TextView firstNameTextSettings, lastnameTextSettings, emailTextSettings, oldPassword ,newPassword;
    private RadioButton femaleButton ,maleButton, otherButton;
    private RelativeLayout profileScreen;
    private ScrollView settingsScreen;
    private String dateForDatabase = "";
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private String name = "";
    private String surname = "";
    private String gender = "";
    private String email = "";
    private String birthdate = "";

    private String token;
    private String tokenType;

    final String URL = "http://10.2.37.139:8080/profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        this.init();

        System.out.println("??nemli Token::::" + token + "  " + tokenType);
    }

    //Private Actions
    private void init() {
        initDatePicker();
        dateButton = (AppCompatButton) findViewById(R.id.BirthdayPickerButton);

        firstNameText = findViewById(R.id.FirstNameTextView);
        lastnameText = findViewById(R.id.LastNameTextView);
        emailText = findViewById(R.id.EmailTextView);
        birthdateText = findViewById(R.id.BirthdateTextView);
        genderText = findViewById(R.id.GenderTextView);


        firstNameTextSettings = findViewById(R.id.FirstNameEditText);
        lastnameTextSettings = findViewById(R.id.LastNameEditText);
        emailTextSettings = findViewById(R.id.EmailEditText);
        femaleButton = findViewById(R.id.GenderPickerFemale);
        maleButton = findViewById(R.id.GenderPickerMale);
        otherButton = findViewById(R.id.GenderPickerOther);
        oldPassword = findViewById(R.id.OldPasswordEditText);
        newPassword = findViewById(R.id.NewPasswordEditText);


        profileScreen = findViewById(R.id.profileLayout);
        settingsScreen = findViewById(R.id.settingsLayout);


        profileScreen.setVisibility(View.VISIBLE);
        settingsScreen.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
        this.getUserDataFrom();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ProfileActivity.this , HomeActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        startActivity(intent);
        finish();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
            alertDialog.setTitle("Ba??lant?? Problemi");
            //alertDialog.setIcon(getResources().getDrawable(R.drawable.nonnet));
            alertDialog.setMessage("Cihaz??n??z internete ba??l?? de??il.");
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

    private void getUserDataFrom() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);



        final String URL = "http://10.2.37.139:8080/profile";
        // Post params to be sent to the server
        System.out.println(tokenType);
        System.out.println(token);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Authorization", tokenType + " " + token);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(String.valueOf(response));
                            name = jsonObject.getString("name");
                            surname = jsonObject.getString("surname");
                            gender = jsonObject.getString("gender");
                            email = jsonObject.getString("email");
                            birthdate = jsonObject.getString("birthDate").replace('-' , '/');

                            firstNameText.setText(name);
                            lastnameText.setText(surname);
                            emailText.setText(email);
                            birthdateText.setText(birthdate);
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

           //Headera g??nder
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

    public void settingsButtonOnClick(View view){
        profileScreen.setVisibility(View.INVISIBLE);
        settingsScreen.setVisibility(View.VISIBLE);

        firstNameTextSettings.setText(firstNameText.getText());
        lastnameTextSettings.setText(lastnameText.getText());
        emailTextSettings.setText(emailText.getText());
        String date = birthdateText.getText().toString().replace('-','/');
        dateButton.setText(date);

        if (genderText.getText().equals("FEMALE")) {
            femaleButton.setChecked(true);
        } else if(genderText.getText().equals("MALE")){
            maleButton.setChecked(true);
        } else{
            otherButton.setChecked(true);
        }

        initDatePicker();

    }

    public void profilePictureChangeButton(View view){
        profileScreen.setVisibility(View.INVISIBLE);
        settingsScreen.setVisibility(View.VISIBLE);
    }

    public void saveBtnAction(View view){

        String email = emailTextSettings.getText().toString();
        String fname = firstNameTextSettings.getText().toString();
        String lname = lastnameTextSettings.getText().toString();
        String gender = femaleButton.isChecked() ? "FEMALE" : (maleButton.isChecked() ? "MALE" : "OTHER");



        RequestQueue queue = Volley.newRequestQueue(this);


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("name" , fname);
        params.put("surname", lname);
        params.put("birthDate" , dateForDatabase);
        params.put("gender" , gender.toUpperCase());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,URL, new JSONObject(params),
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

            //Headera g??nder
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





        finish();
        startActivity(getIntent());
    }

    public void cancelBtnAction(View view){
        finish();
        startActivity(getIntent());
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                try {
                    dateForDatabase = makeDateStringForDatabase(day, month, year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateButton.setText(date);
            }
        };

        if(dateButton == null){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
            datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        }
        else{
            int date [] = parseDate((String) dateButton.getText());
            int year = date[2];
            int month = date[1]-1;
            int day = date[0];
            int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
            datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        }
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String makeDateStringForDatabase(int day, int month, int year) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = year + "-" + month + "-" + day;
        Date date = simpleDateFormat.parse(dateStr);
        return dateStr;
    }

    private String getMonthFormat(int month) {
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

    public int[] parseDate(String date) {
        int bdate [] = new int[3];
        String[] parts = date.split("/");
        bdate[0]= Integer.parseInt(parts[0]); //day
        bdate[1] = Integer.parseInt(parts[1]); //month
        bdate[2] = Integer.parseInt(parts[2]); //year
        return bdate;
    }

    public void goTodo(View view)
    {
        Intent intent = new Intent(ProfileActivity.this , ToDoActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        startActivity(intent);
    }

    public void gotoAchievements(View view)
    {
        Intent intentt = new Intent(ProfileActivity.this , AchivementActivity.class);
        intentt.putExtra("token", token);
        intentt.putExtra("tokenType", tokenType);
        startActivity(intentt);
    }

}

