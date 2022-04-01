package com.example.winxbitirmeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.winxbitirmeapp.SleepActivity.SleepActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        sleepCardView = findViewById(R.id.sleepCardView);
        meditationCardView = findViewById(R.id.meditationCardView);
        chatCardView = findViewById(R.id.chatCardView);

        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");


        //Burada Null poiner exeption yiyorsanız adnanı arayın.
        System.out.println("Token TEEST: " + tokenType + " " + token);
        System.out.println("Token:  " + FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());


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

                FirebaseFirestore.getInstance().collection("User")
                        .whereEqualTo("isOnline", "1")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int counter = 0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        counter++;

                                    }

                                    intent.putExtra("onlineNumber",""+counter);
                                    intent.putExtra("token", token);
                                    intent.putExtra("tokenType", tokenType);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });



            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
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

    //buton metotları geçici
    public void logout(View view)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this , LoginActivity.class);
        startActivity(intent);
        finish();
    }



}