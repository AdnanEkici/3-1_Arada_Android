package com.example.winxbitirmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    //private BottomNavigationView bottomNavigationView;
    private CardView sleepCardView;
    private CardView meditationCardView;
    private CardView chatCardView;
    private RelativeLayout cardViewLayout;


    private String token;
    private String tokenType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //bottomNavigationView = findViewById(R.id.bottomNav);
        sleepCardView = findViewById(R.id.sleepCardView);
        meditationCardView = findViewById(R.id.meditationCardView);
        chatCardView = findViewById(R.id.chatCardView);
        //cardViewLayout = findViewById(R.id.cardViewLayout);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");




        //bottomNavigationView.setVisibility(View.INVISIBLE);

        sleepCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , SleepActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("tokenType", tokenType);
                startActivity(intent);
                //bottomNavigationView.setVisibility(View.VISIBLE);

            }
        });

        meditationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , MeditationActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("tokenType", tokenType);
                startActivity(intent);

            }
        });

        chatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this , ChatActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("tokenType", tokenType);
                startActivity(intent);
            }
        });

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new SleepFragment()).commit();

    }



}