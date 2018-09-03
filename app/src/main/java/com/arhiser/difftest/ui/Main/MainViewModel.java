package com.arhiser.difftest.ui.Main;

import android.text.TextUtils;

import com.arhiser.difftest.model.Copyable;

public class MainViewModel implements Copyable<MainViewModel> {
    private String tempCelsius;
    private String tempFaren;
    private String tempKelvin;
    private String belowZeroMessage;

    public String getTempFaren() {
        return tempFaren;
    }

    public String getTempCelsius() {
        return tempCelsius;
    }

    public String getTempKelvin() {
        return tempKelvin;
    }

    public String getBelowZeroMessage() {
        return belowZeroMessage;
    }

    public MainViewModel(String tempCelsius, String tempFaren, String tempKelvin) {
        this.tempFaren = tempFaren;
        this.tempCelsius = tempCelsius;
        this.tempKelvin = tempKelvin;
    }

    public MainViewModel(String tempCelsius) {
        setTemperatureToConvert(tempCelsius);
    }

    public void setTemperatureToConvert(String tempCelsius) {
        this.tempCelsius = tempCelsius;
        if (TextUtils.isEmpty(tempCelsius)) {
            this.tempKelvin = "";
            this.tempFaren = "";
            this.belowZeroMessage = "";
        } else {
            int temp = Integer.parseInt(tempCelsius);
            this.tempKelvin = Integer.toString(temp + 273);
            this.tempFaren = Integer.toString(Math.round(1.8f * temp + 32));
            this.belowZeroMessage = temp > 0 ? "temperature is above zero" : "temperature is below zero";
        }
    }

    @Override
    public MainViewModel copy() {
        return new MainViewModel(tempCelsius);
    }
}
