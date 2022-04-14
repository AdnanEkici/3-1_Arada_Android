package com.example.winxbitirmeapp.SleepActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.winxbitirmeapp.HomeActivity;
import com.example.winxbitirmeapp.LoginActivity;
import com.example.winxbitirmeapp.R;

public class SleepCounterActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private Chronometer counter;
    private SoundMeter soundMeter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepcounter);

        soundMeter = new SoundMeter();

        this.init();

    }


    //Life Actions


    //Public Actions


    //Private Actions
    private void init() {
        counter = (Chronometer) findViewById(R.id.counter); // initiate a chronometer
        counter.start();
        startVoiceDetection();
    }

    public void stopButtonAction(View view){
        counter.stop();
        //long elapsedMillis = SystemClock.elapsedRealtime() - counter.getBase();
        Intent intent = new Intent(SleepCounterActivity.this , SleepActivity.class);
        startActivity(intent);
        finish();
    }


    private void startVoiceDetection() {
        //TODO: Do soundMeter.stop() control
        soundMeter.start();

        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                //TODO: save sound data
                System.out.println(soundMeter.getAmplitude());
                h.postDelayed(this, 1000);
            }
        }, 1000); // 1 second delay (takes millis)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //MY_PERMISSIONS_RECORD_AUDIO Sleep activity deki MY_PERMISSIONS_RECORD_AUDIO ile aynı olmalı
        switch (requestCode) {
            case MY_PERMISSIONS_RECORD_AUDIO: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startVoiceDetection();

                } else {
                    //TODO: Do something on voice record permission rejection
                }
                return;
            }

        }
    }


}
