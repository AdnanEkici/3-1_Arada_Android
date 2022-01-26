package com.example.winxbitirmeapp.SleepActivity;

public class SleepData
{
    private double quality;
    private double time;

    public SleepData(double quality, double time) {
        this.quality = quality;
        this.time = time;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }



}
