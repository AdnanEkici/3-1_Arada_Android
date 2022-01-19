package com.example.winxbitirmeapp.Questionnaires;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.winxbitirmeapp.R;

public class QuestionnaireActivity extends AppCompatActivity {


    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        this.init();
        this.checkInternet();


    }

    //Life Actions





    //Public Actions





    //Private Actions
    private void init()
    {
        logo = findViewById(R.id.QuestionnaireBackgroundLogoID);
        logo.setAlpha(100); //value: [0-255]. Where 0 is fully transparent and 255 is fully opaque.

    }


    //DB Actions






    //Button Actions

    public void nextPage(View view)
    {


    }







    //Check Internet
    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(QuestionnaireActivity.this).create();
            alertDialog.setTitle("Bağlantı Problemi");
            alertDialog.setIcon(getResources().getDrawable(R.drawable.nowifi));
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