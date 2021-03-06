package com.example.winxbitirmeapp.ChatActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.winxbitirmeapp.Adapters.MessageAdapter;
import com.example.winxbitirmeapp.HomeActivity;
import com.example.winxbitirmeapp.Models.ChatMessage;
import com.example.winxbitirmeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class ChatMainActivity extends AppCompatActivity {

    //ADNAN TOKEN VE TOKENTYPE'IN INTENT ILE AL AMA KULLANMIYCAKSIN SADECE GECIS ICIN
    FirebaseUser firebaseUser;
    AppCompatImageView sendButton;
    EditText messageText;
    MessageAdapter messageAdapter;
    ArrayList<ChatMessage> mChat;
    String senderEmail;
    String receiverEmail;
    RecyclerView recyclerView;
    TextView nameOfChatter ;
    String reportText;
    private String token;
    private String tokenType;
    private String email;
    private String password;
    private Instant start = Instant.now();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        init();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = messageText.getText().toString();
                if (!msg.equals("")) {
                    senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    sendMessage(senderEmail, receiverEmail, msg);
                }
                messageText.setText("");
            }
        });
        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        FirebaseFirestore.getInstance().collection("rooms").document(senderEmail+receiverEmail)
                .collection("messages").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            readMessages();
                        }
                    }
                });

        System.out.println("??nemli Token::::" + token + "  " + tokenType);

    }

    /*private void logoutChat() {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("User")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot value,
                                         FirebaseFirestoreException e) {

                        if (e != null) {
                            return;
                        }

                        for (QueryDocumentSnapshot dc : value) {
                            if (dc.getString("email").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) && dc.getString("isMatched").equals("-1")) {
                                Intent intent = new Intent(ChatMainActivity.this,RedirectActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }
                    }
                });

    }*/

    private void init() {

        Intent intent = getIntent();
        nameOfChatter = (TextView) findViewById(R.id.nameOfChatter);
        String name = getRandomizeName();
        nameOfChatter.setText(name);
        sendButton = (AppCompatImageView) findViewById(R.id.sendButton);
        messageText = (EditText) findViewById(R.id.inputMessage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        senderEmail = firebaseUser.getEmail();
        receiverEmail = intent.getStringExtra("matchedEmail");
        mChat = new ArrayList<>();
        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setHasFixedSize(true); //emin degilim ne ise yariyro
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(ChatMainActivity.this,mChat);
        recyclerView.setAdapter(messageAdapter);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ChatMainActivity.this , ChatActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("tokenType", tokenType);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(ChatMainActivity.this).create();
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

    private String getRandomizeName() {
        String result = "";

        String[] names = new String[]{
                "AnonymousAlpaca" ,
                "AnonymousBadger " ,
                "AnonymousBat" ,
                "AnonymousBear" ,
                "AnonymousBird" ,
                "AnonymousBug" ,
                "AnonymousCat" ,
                "AnonymousChicken" ,
                "AnonymousChinchilla " ,
                "AnonymousCow" ,
                "AnonymousCrab" ,
                "AnonymousDeer " ,
                "AnonymousDinosaur" ,
                "AnonymousDog" ,
                "AnonymusBruh",
                "AnonymousDolphin " ,
                "AnonymousDonkey " ,
                "AnonymousDuck" ,
                "AnonymousElephant" ,
                "AnonymousFish",
                "AnonymousFlamingo",
                "AnonymousFox",
                "AnonymousFrog " ,
                "AnonymousGecko " ,
                "AnonymousGiraffe " ,
                "AnonymousGuineaPig " ,
                "AnonymousHamster " ,
                "AnonymousHedgehog " ,
                "AnonymousHermitCrab " ,
                "AnonymousHorse " ,
                "AnonymousInsect " ,
                "AnonymousJellyfish " ,
                "AnonymousKoala " ,
                "AnonymousLion " ,
                "AnonymousLizard " ,
                "AnonymousLlama " ,
                "AnonymousMonkey " ,
                "AnonymousMoth " ,
                "AnonymousMouse " ,
                "AnonymousOwl " ,
                "AnonymousPanda " ,
                "AnonymousParrot " ,
                "AnonymousPenguin " ,
                "AnonymousPig " ,
                "AnonymousPolarBear " ,
                "AnonymousRabbit " ,
                "AnonymousRacehorse " ,
                "AnonymousRat " ,
                "AnonymousReptile " ,
                "AnonymousShark " ,
                "AnonymousSheep " ,
                "AnonymousSnake " ,
                "AnonymousSpider" ,
                "AnonymousSquid" ,
                "AnonymousTiger" ,
                "AnonymousTortoise" ,
                "AnonymousTurtle" ,
                "AnonymousWhale " ,
                "AnonymousWolf "
        };
        int rand = (int)(Math.random() * names.length);
        result = names[rand];

        return result;
    }

    private void sendMessage(String sender, String receiver, String message) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("User")
                .whereEqualTo("email",firebaseUser.getEmail())
                .whereEqualTo("matchedEmail","-1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(ChatMainActivity.this);
                                builder1.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg,null));

                                builder1.setCancelable(false);

                                builder1.setNeutralButton(
                                        "Home Ekran??na Git",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                //ADOS PROGRESS BAR YAP



                                                Intent intent = new Intent(ChatMainActivity.this, RedirectActivity.class);

                                                intent.putExtra("token", token);
                                                intent.putExtra("tokenType", tokenType);
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);
                                                startActivity(intent);
                                                db.collection("rooms").document(senderEmail+receiverEmail)
                                                        .collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                document.getReference().delete();
                                                            }
                                                        } else {
                                                        }
                                                    }
                                                });

                                                db.collection("rooms").document(receiverEmail+senderEmail)
                                                        .collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                document.getReference().delete();
                                                            }
                                                        } else {
                                                        }
                                                    }
                                                });

                                                finish();
                                            }
                                        });



                                TextView message = new TextView(ChatMainActivity.this);
                                // You Can Customise your Title here
                                message.setText("Sohbet Arkada????n??z Konu??may?? sonland??rd??.");
                                message.setTextColor(getColor(R.color.dark_green));
                                message.setTextSize(15);
                                builder1.setCustomTitle(message);

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                                alert11.getButton(alert11.BUTTON_NEUTRAL).setTextColor(getColor(R.color.brown4));

                            }
                        }else{

                        }
                    }
                });


        Date date = new Date();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("date", date.getTime());
        hashMap.put("created", Timestamp.now());
        db.collection("rooms").document(senderEmail+receiverEmail)
                .collection("messages").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("rooms").document(receiverEmail+senderEmail)
                        .collection("messages").add(hashMap);
            }
        });
    }

    private void readMessages() {
        mChat = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("rooms").document(senderEmail+receiverEmail)
                .collection("messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot value,
                                         FirebaseFirestoreException e) {

                        if (e != null) {
                            return;
                        }

                        /*db.collection("User")
                                .whereEqualTo("email",firebaseUser.getEmail())
                                .whereEqualTo("chatClick", "0")
                                .whereEqualTo("isMatched","0")
                                .whereEqualTo("matchedEmail","-1")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Intent intent = new Intent(ChatMainActivity.this,RedirectActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }else{

                                        }
                                    }
                                });*/


                        mChat.clear();
                        for (QueryDocumentSnapshot dc : value) {
                            if (dc.getString("receiver").equals(senderEmail) && dc.getString("sender").equals(receiverEmail)){
                                ChatMessage chatMessage = new ChatMessage(receiverEmail,senderEmail,dc.getString("message"),dc.getLong("date"),dc.getTimestamp("created"));
                                mChat.add(chatMessage);

                            }

                            if (dc.getString("receiver").equals(receiverEmail) && dc.getString("sender").equals(senderEmail)){
                                ChatMessage chatMessage = new ChatMessage(senderEmail,receiverEmail,dc.getString("message"),dc.getLong("date"),dc.getTimestamp("created"));
                                mChat.add(chatMessage);
                            }

                            // sorting by date >
                            Collections.sort(mChat, new Comparator<ChatMessage>() {
                                @Override
                                public int compare(ChatMessage o1, ChatMessage o2) {
                                    return o1.getCreated().compareTo(o2.getCreated());
                                }
                            });
                            messageAdapter = new MessageAdapter(ChatMainActivity.this, mChat);
                            recyclerView.setAdapter(messageAdapter);
                        }
                    }
                });
    }

    public void goBackToHome(View view) {
        MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(ChatMainActivity.this);
        builder1.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg,null));

        builder1.setCancelable(true);



        builder1.setPositiveButton(
                "Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        //ADOS PROGRESS BAR YAP



                        db.collection("User").document(senderEmail)
                                .update("isMatched","0");
                        db.collection("User").document(senderEmail)
                                .update("chatClick","0");
                        db.collection("User").document(senderEmail)
                                .update("matchedEmail","-1");

                        db.collection("User").document(receiverEmail)
                                .update("isMatched","0");
                        db.collection("User").document(receiverEmail)
                                .update("chatClick","0");
                        db.collection("User").document(receiverEmail)
                                .update("matchedEmail","-1");

                        Instant end = Instant.now();
                        Duration timeElapsed = Duration.between(start, end);
                        long minutes = (timeElapsed.toMillis() / 1000) / 60;
                        System.out.println("Time taken: " + minutes +" dk");
                        Intent intent = new Intent(ChatMainActivity.this,RedirectActivity.class);
                        intent.putExtra("token", token);
                        intent.putExtra("tokenType", tokenType);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        db.collection("rooms").document(senderEmail+receiverEmail)
                                .collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                } else {
                                }
                            }
                        });

                        db.collection("rooms").document(receiverEmail+senderEmail)
                                .collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete();
                                    }
                                } else {
                                }
                            }
                        });
                        finish();
                    }
                });


        builder1.setNegativeButton(
                "Hay??r",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        TextView message = new TextView(this);
        // You Can Customise your Title here
        message.setText("Konu??may?? sonland??rmak istiyor musunuz ?");
        message.setTextColor(getColor(R.color.dark_green));
        message.setTextSize(15);
        builder1.setCustomTitle(message);

        AlertDialog alert11 = builder1.create();
        alert11.show();

        alert11.getButton(alert11.BUTTON_NEGATIVE).setTextColor(getColor(R.color.dark_green));
        alert11.getButton(alert11.BUTTON_POSITIVE).setTextColor(getColor(R.color.brown4));



    }

    public void reportButton(View view) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(ChatMainActivity.this);
        dialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg,null));
        EditText editText = new EditText(ChatMainActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        dialog.setView(editText);
        dialog.setPositiveButton("G??nder", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reportText = editText.getText().toString();
                dialogInterface.cancel();

            }
        });
        dialog.setNegativeButton("??ptal", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        TextView title = new TextView(this);
        title.setText("Sohbet arkada????n?? bildir?");
        title.setTextColor(getColor(R.color.dark_green));
        title.setTextSize(20);
        dialog.setCustomTitle(title);
        AlertDialog alert11 = dialog.create();
        alert11.show();

        alert11.getButton(alert11.BUTTON_NEGATIVE).setTextColor(getColor(R.color.dark_green));
        alert11.getButton(alert11.BUTTON_POSITIVE).setTextColor(getColor(R.color.brown4));




    }
}