package com.example.winxbitirmeapp.StaticAnket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.winxbitirmeapp.R;

public class first_questionnaire_questions extends AppCompatActivity {


    private EditText secondQuestionAnswerEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_questionnaire_questions);
        this.init();


        //Buradan -- yaz
    }


    //Button metotları

    public void mokoko(View view)
    {
        System.out.println("Butona Bastım !");
        System.out.println("SATIR 37: " + secondQuestionAnswerEdit.getText().toString());
    }



    //private metotlar
    private void init()
    {
        secondQuestionAnswerEdit = findViewById(R.id.FirstQustionnairesecondanswerID);

    }





    //database metotları






    //misc











}