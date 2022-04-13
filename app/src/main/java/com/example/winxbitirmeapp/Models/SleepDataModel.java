package com.example.winxbitirmeapp.Models;

import java.util.Date;

public class SleepDataModel
{
    private Date date;
    private Double frequency;

    public SleepDataModel(Date date, Double frequency) {
        this.date = date;
        this.frequency = frequency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }
}
