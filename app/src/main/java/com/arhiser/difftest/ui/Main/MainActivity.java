package com.arhiser.difftest.ui.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.arhiser.difftest.R;
import com.arhiser.difftest.objdiff.difs.Cancellable;
import com.arhiser.difftest.objdiff.difs.DiffService;
import com.arhiser.difftest.objdiff.dispatch.ChangeDispatcher;
import com.arhiser.difftest.objdiff.ui.Activity.DiffBindActivity;
import com.arhiser.difftest.objdiff.ui.ViewHolder.ViewHolder;
import com.arhiser.difftest.objdiff.ui.controller.Controller;

public class MainActivity extends DiffBindActivity<MainViewModel, MainViewHolder, MainController> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainController getController(MainViewModel model) {
        return new MainController(model, diffService);
    }

    @Override
    protected MainViewModel getModel() {
        return new MainViewModel();
    }

    @Override
    protected MainViewHolder getViewHolder(View root) {
        return new MainViewHolder(getRootView(), controller);
    }
}
