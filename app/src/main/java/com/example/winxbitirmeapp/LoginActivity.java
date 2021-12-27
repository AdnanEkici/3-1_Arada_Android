package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        email = email_edit.getText().toString();
        password = password_edit.getText().toString();


        Toast.makeText(LoginActivity.this , email + " VE " + password , Toast.LENGTH_LONG).show();
    }



}