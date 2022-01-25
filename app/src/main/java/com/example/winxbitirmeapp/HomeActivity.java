package com.example.winxbitirmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
            }
        });

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new SleepFragment()).commit();

    }

    /*private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;

            switch (item.getItemId()){
                case R.id.sleep:
                    intent = new Intent(HomeActivity.this , SleepActivity.class);
                    startActivity(intent);
                    break;
                case R.id.yoga:
                    intent = new Intent(HomeActivity.this , MeditationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.chat:
                    intent = new Intent(HomeActivity.this , ChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.profile:
                    intent = new Intent(HomeActivity.this , ProfileActivity.class);
                    startActivity(intent);
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            return true;
        }
    };*/


}