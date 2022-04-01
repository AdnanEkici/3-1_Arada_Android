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
    private ListView questionlistView;
    private String token;
    private String tokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        this.checkInternet();
        this.init();



    }

    //Life Actions





    //Public Actions





    //Private Actions
    private void init()
    {
        questionlistView = findViewById(R.id.QuestionnaireListViewID);
        logo = findViewById(R.id.QuestionnaireBackgroundLogoID);
        logo.setAlpha(10); //value: [0-255]. Where 0 is fully transparent and 255 is fully opaque.
        questions = new ArrayList<>(10);
        questions.add("Soru1 :");
        questions.add("Soru2 :");
        questions.add("Soru3 :");
        questions.add("Soru4 :");
        questions.add("Soru5 :");
        questions.add("Soru6 :");
        questions.add("Soru7 :");
        questions.add("Soru8 :");
        questions.add("Soru9 :");
        questions.add("Soru10 :");
        selectedAnswers = new ArrayList<Integer>(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            selectedAnswers.add(-1);
        }
        QuestionnaireAdapter adapter = new QuestionnaireAdapter(this , this.questions , selectedAnswers);
        questionlistView.setAdapter(adapter);


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




}//activity end