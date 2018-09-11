package com.arhiser.difftest.ui.Main;

import android.text.TextUtils;

import com.arhiser.difftest.objdiff.dispatch.Copyable;

public class MainViewModel implements Copyable<MainViewModel> {
    private String tempCelsius = "";
    private String tempFaren = "";
    private String tempKelvin = "";
    private String belowZeroMessage = "";

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

    public void setTempCelsius(String tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public void setTempFaren(String tempFaren) {
        this.tempFaren = tempFaren;
    }

    public void setTempKelvin(String tempKelvin) {
        this.tempKelvin = tempKelvin;
    }

    public void setBelowZeroMessage(String belowZeroMessage) {
        this.belowZeroMessage = belowZeroMessage;
    }

    public MainViewModel() {
    }

    public MainViewModel(String tempCelsius, String tempFaren, String tempKelvin, String belowZeroMessage) {
        this.tempFaren = tempFaren;
        this.tempCelsius = tempCelsius;
        this.tempKelvin = tempKelvin;
        this.belowZeroMessage = belowZeroMessage;
    }

    @Override
    public MainViewModel copy() {
        return new MainViewModel(getTempCelsius(), getTempFaren(), getTempKelvin(), getBelowZeroMessage());
    }
}
