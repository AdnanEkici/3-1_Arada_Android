package com.example.winxbitirmeapp.Questionnaires;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winxbitirmeapp.Adapters.QuestionnaireAdapter;
import com.example.winxbitirmeapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireActivity extends AppCompatActivity {


    private ImageView logo;
    private ArrayList<Integer> selectedAnswers;
    private ArrayList<String> questions;
    private ArrayList<RadioGroup> radioGroups;
    private ListView questionlistView;
    private String token;
    private String tokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        this.checkInternet();
        System.out.println("AD");
        this.init();

    }

    //Life Actions





    //Public Actions





    //Private Actions
    private void init()
    {

    }

    public void nextQuestion(int i){
        radioGroups.get(i).setVisibility(View.GONE);
        if (i < 9){
            radioGroups.get(i+1).setVisibility(View.VISIBLE);
        }
    }


    //DB Actions






    //Button Actions

    public void nextPage(View view)
    {
        boolean questionnaireDone = false;
        for(int i = 0; i < selectedAnswers.size(); i++)
        {
            if(selectedAnswers.get(i) == -1)
            {
                Toast.makeText(QuestionnaireActivity.this , "Lütfen Anketi Tamamlayınız" , Toast.LENGTH_SHORT).show();break;
            }
            else if(i == selectedAnswers.size()-1)
            {
                if (selectedAnswers.get(i) != 1)
                    questionnaireDone = true;
            }
        }
        if(questionnaireDone)
        {
            Toast.makeText(QuestionnaireActivity.this , "Anket Bitti Sonraki Sayfaya Geçilebilir" , Toast.LENGTH_SHORT).show();
        }
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




}//activity end