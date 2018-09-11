package com.arhiser.difftest.ui.Main;

import android.text.TextUtils;

import com.arhiser.difftest.objdiff.difs.DiffService;
import com.arhiser.difftest.objdiff.ui.controller.Controller;

public class MainController extends Controller<MainViewModel> {
    public MainController(MainViewModel model, DiffService diffService) {
        super(model, diffService);
    }

    public void onTemperatureTextChanged(String tempText) {
        MainViewModel model = changeDispatcher.startChanges();

        model.setTempCelsius(tempText);
        if (TextUtils.isEmpty(tempText)) {
            model.setTempKelvin("");
            model.setTempFaren("");
            model.setBelowZeroMessage("");
        } else {
            int temp = Integer.parseInt(tempText);
            model.setTempKelvin(Integer.toString(temp + 273));
            model.setTempFaren(Integer.toString(Math.round(1.8f * temp + 32)));
            model.setBelowZeroMessage(temp > 0 ? "temperature is above zero" : "temperature is below zero");
        }

        changeDispatcher.dispatchChanges(diffService);
    }
}
