package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winxbitirmeapp.Adapters.SliderAdapter;

import java.net.InetAddress;

public class QuestionnaireActivity extends AppCompatActivity {

    private LinearLayout dotsLayout;
    private SliderAdapter adapter;
    private ViewPager2 viewPager2;
    private String list[];
    private TextView dots[];


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
        dotsLayout = findViewById(R.id.QuestionnaireLLID);
        viewPager2 = findViewById(R.id.QuestionnaireViewPager2ID);
        dots = new TextView[3];
        dotsIndicator();
        list = new String[15];
        list[0] = "Soru1"; list[5] = "Soru6"; list[10] = "Soru11";
        list[1] = "Soru2"; list[6] = "Soru7"; list[11] = "Soru12";
        list[2] = "Soru3"; list[7] = "Soru8"; list[12] = "Soru13";
        list[3] = "Soru4"; list[8] = "Soru9"; list[13] = "Soru14";
        list[4] = "Soru5"; list[9] = "Soru10"; list[14] = "Soru15";



        adapter = new SliderAdapter(list , dots);
        viewPager2.setAdapter(adapter);



        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position)
            {

                selectedIndicator(position);
                super.onPageSelected(position);
            }
        });

    }

    private void selectedIndicator(int position)
    {
        for (int i = 0; i < dots.length; i++)
        {
            if(i == position)
            {
                dots[i].setTextColor(getResources().getColor(R.color.grey));
            }
            else
            {
                dots[i].setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    private void dotsIndicator()
    {
        for (int i = 0; i < dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#9679"));
            dots[i].setTextSize(10);
            dotsLayout.addView(dots[i]);
        }
    }


    //DB Actions






    //Button Actions









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