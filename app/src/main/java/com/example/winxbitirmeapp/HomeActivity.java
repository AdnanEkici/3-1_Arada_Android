package com.example.winxbitirmeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.winxbitirmeapp.SleepActivity.SleepActivity;

public class HomeActivity extends AppCompatActivity {

    //private BottomNavigationView bottomNavigationView;
    private CardView sleepCardView;
    private CardView meditationCardView;
    private CardView chatCardView;
    private RelativeLayout cardViewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //bottomNavigationView = findViewById(R.id.bottomNav);
        sleepCardView = findViewById(R.id.sleepCardView);
        meditationCardView = findViewById(R.id.meditationCardView);
        chatCardView = findViewById(R.id.chatCardView);
        //cardViewLayout = findViewById(R.id.cardViewLayout);


        //bottomNavigationView.setVisibility(View.INVISIBLE);

        sleepCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , SleepActivity.class);
                startActivity(intent);
                //bottomNavigationView.setVisibility(View.VISIBLE);

            }
        });

        meditationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , MeditationActivity.class);
                startActivity(intent);

            }
        });

        chatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new SleepFragment()).commit();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
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