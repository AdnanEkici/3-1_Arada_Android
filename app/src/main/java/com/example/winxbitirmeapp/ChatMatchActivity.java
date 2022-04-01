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
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.winxbitirmeapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatMatchActivity extends AppCompatActivity {

    ArrayList<User> userList = new ArrayList<User>(); //onlinelari cek hem de chat butonuna basmislari
    private ImageView imageAnimation;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 2500; //Delay for 15 seconds.  One second = 1000 milliseconds.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_match);
        init();
    }

    private void init(){

        imageAnimation = findViewById(R.id.animationImage);
        imageAnimation.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shake_animation));

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChatMatchActivity.this , ChatMainActivity.class);
                String rec = "";
                if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("ados@gmail.com"))
                    rec = "busra@gmail.com";
                else
                    rec = "ados@gmail.com";
                intent.putExtra("rec",rec);
                startActivity(intent);
                finish();
            }
        }, 5000);*/




/*

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("***********************************************************************");
                userList = new ArrayList<>();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                System.out.println("Document var mi 72 " + document.getString("isMatched"));
                                if (document.getString("isMatched").equals("1")){
                                    System.out.println("is matched 1 74");
                                    Intent intent = new Intent(ChatMatchActivity.this , ChatMainActivity.class);
                                    intent.putExtra("matchedEmail",document.getString("matchedEmail"));
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }

                    }
                });

                db.collection("User")
                        .whereEqualTo("chatClick", "1")
                        .whereEqualTo("isMatched","0")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete( Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(document.getString("email"))){
                                            User user= new User(document.getString("email"),document.getString("chatClick"),document.getString("isMatched"));
                                            userList.add(user);
                                        }

                                    }
                                    if (userList.size()>0) {
                                        int random = (int) (Math.random() * userList.size());
                                        DocumentReference docRef = db.collection("User").document(userList.get(random).getUserMatchMail());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete( Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        if (document.getString("chatClick").equals("1") && document.getString("isMatched").equals("0")){
                                                            db.collection("User").document(userList.get(random).getUserMatchMail())
                                                                    .update("isMatched","1");
                                                            db.collection("User").document(userList.get(random).getUserMatchMail())
                                                                    .update("matchedEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                                            db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                                    .update("isMatched","1");
                                                            db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                                    .update("matchedEmail",userList.get(random).getUserMatchMail());


                                                            Intent intent = new Intent(ChatMatchActivity.this , ChatMainActivity.class);
                                                            intent.putExtra("matchedEmail",userList.get(random).getUserMatchMail());
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                    }
                                                } else {
                                                    //progress end
                                                }
                                            }
                                        });
                                    }
                                } else {

                                }
                            }
                        });
            }
        }, 2500);*/


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(ChatMatchActivity.this).create();
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

    @Override
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                userList = new ArrayList<>();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (document.getString("isMatched").equals("1") && !document.getString("matchedEmail").equals("-1")){
                                    Intent intent = new Intent(ChatMatchActivity.this , ChatMainActivity.class);
                                    intent.putExtra("matchedEmail",document.getString("matchedEmail"));
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }

                    }
                });

                db.collection("User")
                        .whereEqualTo("chatClick", "1")
                        .whereEqualTo("isMatched","0")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete( Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(document.getString("email"))){
                                            User user= new User(document.getString("email"),document.getString("chatClick"),document.getString("isMatched"));
                                            userList.add(user);
                                        }

                                    }
                                    if (userList.size()>0) {
                                        int random = (int) (Math.random() * userList.size());
                                        DocumentReference docRef = db.collection("User").document(userList.get(random).getUserMatchMail());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete( Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        if (document.getString("chatClick").equals("1") && document.getString("isMatched").equals("0")){
                                                            db.collection("User").document(userList.get(random).getUserMatchMail())
                                                                    .update("isMatched","1");
                                                            db.collection("User").document(userList.get(random).getUserMatchMail())
                                                                    .update("matchedEmail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                                            db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                                    .update("isMatched","1");
                                                            db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                                    .update("matchedEmail",userList.get(random).getUserMatchMail());


                                                            Intent intent = new Intent(ChatMatchActivity.this , ChatMainActivity.class);
                                                            intent.putExtra("matchedEmail",userList.get(random).getUserMatchMail());
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                    }
                                                } else {
                                                    //progress end
                                                }
                                            }
                                        });
                                    }
                                } else {

                                }
                            }
                        });
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }




    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }


}