package com.example.winxbitirmeapp.SleepActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.winxbitirmeapp.ChatActivity;
import com.example.winxbitirmeapp.MeditationActivity;
import com.example.winxbitirmeapp.ProfileActivity;
import com.example.winxbitirmeapp.Questionnaires.QuestionnaireActivity;
import com.example.winxbitirmeapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private BottomNavigationView bottomNavigationView;
    private LineChart chart;
    private TextView dateView, timeView;
    
    private SoundMeter soundMeter;

    private String token;
    private String tokenType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        this.checkInternet();

        soundMeter = new SoundMeter();


        this.init();
        this.initGrap();
    }




    //Life Actions

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = null;

            switch (item.getItemId()){
                case R.id.sleep:
                    intent = new Intent(SleepActivity.this , SleepActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    break;
                case R.id.yoga:
                    intent = new Intent(SleepActivity.this , MeditationActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    break;
                case R.id.chat:
                    intent = new Intent(SleepActivity.this , ChatActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    break;
                case R.id.profile:
                    intent = new Intent(SleepActivity.this , ProfileActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("tokenType", tokenType);
                    startActivity(intent);
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            return true;
        }
    };

    //Grafiği başlatır dataları yükler.



    //Public Actions





    //Private Actions
    private void init()
    {
        chart = (LineChart) findViewById(R.id.SleepActivityChartID); // Önemsiz dese bile bu activityde viewları cast et.
        bottomNavigationView = findViewById(R.id.bottomNav);// <--  Bu hariç
        bottomNavigationView.setSelectedItemId(R.id.sleep);
        dateView = (TextView) findViewById(R.id.SleepActivityDateTextID);;
        timeView = (TextView) findViewById(R.id.SleepActivityTimeTextID);;
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Intent intent = getIntent();
        tokenType = intent.getStringExtra("tokenType");
        token = intent.getStringExtra("token");

        //Date currentTime = Calendar.getInstance().getTime();
        //Wed Jan 26 20:39:35 GMT+03:00 2022
        //String dateAndTime = currentTime.toString().substring(0 , 10);
        //dateView.setText(dateAndTime);
        //dateAndTime = currentTime.toString().substring(11 , 19);
        //timeView.setText(dateAndTime);

    }

    private void initGrap()
    {

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("PZT", "SAL", "ÇAR", "PER", "CUM", "CMT","PAZ"));
        List<Entry> incomeEntries = getIncomeEntries();
        dataSets = new ArrayList<>();
        LineDataSet set1;

        set1 = new LineDataSet(incomeEntries, "Uyku Kalitesi");
        set1.setColor(Color.rgb(65, 168, 121));
        set1.setValueTextColor(Color.rgb(55, 70, 73));
        dataSets.add(set1);

//customization

        chart.setExtraBottomOffset(1);
        chart.fitScreen();
        chart.setScaleEnabled(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setPinchZoom(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawGridBackground(false);
//to hide background lines
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);

//to hide right Y and top X border
        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setEnabled(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(360-45);

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawFilled(true);
        set1.setFillColor(ContextCompat.getColor(SleepActivity.this,R.color.green));
        set1.setLineWidth(4f);
        set1.setCircleRadius(3f);
        set1.setDrawValues(false);

        chart.setViewPortOffsets(0f, 0f, 0f, 0f);

//String setter in x-Axis
        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);




    }

    //Get Data
    private List<Entry> getIncomeEntries() {
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        incomeEntries.add(new Entry(0, 8));
        incomeEntries.add(new Entry(1, 11));
        incomeEntries.add(new Entry(2, 13));
        incomeEntries.add(new Entry(3, 7));
        incomeEntries.add(new Entry(4, 9));
        incomeEntries.add(new Entry(5, 10));
        incomeEntries.add(new Entry(6, 6));
        return incomeEntries.subList(0, 7);
    }


    //DB Actions






    //Button Actions



    //Check Internet
    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkInternet()
    {
        if (!isNetworkConnected())
        {
            AlertDialog alertDialog = new AlertDialog.Builder(SleepActivity.this).create();
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

    public void startVoiceButton(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }
        else {
            startVoiceDetection();
        }

    }

    public void startVoiceDetection() {
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