package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.winxbitirmeapp.ChatActivities.ChatActivity;
import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MeditationActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private String token;
    private String tokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        this.init();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MeditationActivity.this , HomeActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        startActivity(intent);
        finish();
    }

    private void init()
    {
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.yoga);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(MeditationActivity.this).create();
            alertDialog.setTitle("Bağlantı Problemi");
            //alertDialog.setIcon(getResources().getDrawable(R.drawable.nonnet));
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

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;

            switch (item.getItemId()){
                case R.id.sleep:
                    intent = new Intent(MeditationActivity.this , SleepActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.yoga:
                    intent = new Intent(MeditationActivity.this , MeditationActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.chat:
                    intent = new Intent(MeditationActivity.this , ChatActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.profile:
                    intent = new Intent(MeditationActivity.this , ProfileActivity.class);
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
}