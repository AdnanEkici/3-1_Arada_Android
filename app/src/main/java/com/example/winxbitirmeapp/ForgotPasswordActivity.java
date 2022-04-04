package com.example.winxbitirmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = (EditText) findViewById(R.id.forgotPassEmailEditText);
        setContentView(R.layout.activity_forgot_password);
    }

    public void sendEmailAddress(View view) {

    }
}