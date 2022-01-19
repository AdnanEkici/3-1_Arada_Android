package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText email_edit , password_edit;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();

    }



    //Life Actions





    //Public Actions





    //Private Actions
    private void init()
    {
        email_edit = findViewById(R.id.LoginEditTextEmailID);
        password_edit = findViewById(R.id.LoginEditTextPasswordID);
    }






    //DB Actions






    //Button Actions

    public void loginBtnAction(View view)
    {


    }


    public void moveQuestions(View view)
    {
        Intent intent = new Intent(LoginActivity.this , QuestionnaireActivity.class);
        startActivity(intent);
        finish();
    }
}