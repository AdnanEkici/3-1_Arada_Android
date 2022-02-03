package com.example.winxbitirmeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //bottomNavigationView = findViewById(R.id.bottomNav);
        //bottomNavigationView.setSelectedItemId(R.id.chat);

        //bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

    public void goToChatAction(View view) {
        Intent intent = new Intent(ChatActivity.this , ChatMainActivity.class);
        startActivity(intent);
        finish();
    }

    /*private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;

            switch (item.getItemId()){
                case R.id.sleep:
                    intent = new Intent(ChatActivity.this , SleepActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.yoga:
                    intent = new Intent(ChatActivity.this , MeditationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.chat:
                    intent = new Intent(ChatActivity.this , ChatActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
                case R.id.profile:
                    intent = new Intent(ChatActivity.this , ProfileActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            return true;
        }
    };*/
}