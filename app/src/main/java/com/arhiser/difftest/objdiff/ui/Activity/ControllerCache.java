package com.arhiser.difftest.objdiff.ui.Activity;

import android.app.Activity;

import com.arhiser.difftest.objdiff.ui.controller.Controller;

import java.util.HashMap;

public class ControllerCache {

    private static ControllerCache instance = new ControllerCache();

    public static ControllerCache getInstance() {
        return instance;
    }

    private ControllerCache() {
    }

    private HashMap<Class<?>, Controller> activityToController = new HashMap<>();

    public void putController(Class<?> hostClass, Controller controller) {
        activityToController.put(hostClass, controller);
    }

    public void removeController(Class<?> hostClass) {
        activityToController.remove(hostClass);
    }

    public Controller getController(Class<?> hostClass) {
        return activityToController.get(hostClass);
    }
}
