package com.arhiser.difftest.ui.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arhiser.difftest.R;
import com.arhiser.difftest.difs.Cancellable;
import com.arhiser.difftest.difs.DiffService;
import com.arhiser.difftest.model.ChangeDispatcher;
import com.arhiser.difftest.ui.ViewHolder.ChangesViewHolder;
import com.arhiser.difftest.ui.ViewHolder.ViewHolder;

public class MainActivity extends AppCompatActivity {

    ViewHolder<DiffService.DiffData> viewHolder;
    DiffService diffService = new DiffService(4);
    Cancellable diffCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ChangeDispatcher<MainViewModel> changeDispatcher = new ChangeDispatcher<>(new MainViewModel(""));
        diffCalculation = changeDispatcher.apply(diffService);
        changeDispatcher.addListener(new ChangeDispatcher.ChangesListener() {
            @Override
            public void onChanges(ChangeDispatcher source, DiffService.DiffData changes) {
                viewHolder.bind(changes);
            }
        });
        viewHolder = new MainViewHolder(findViewById(R.id.root), new MainViewHolder.InteractionListener() {
            @Override
            public void onTemperatureEntered(String temp) {
                diffCalculation.cancel();
                changeDispatcher.startChange().setTemperatureToConvert(temp);
                diffCalculation = changeDispatcher.apply(diffService);
            }
        });
    }

    @Override
    protected void onStop() {
        diffCalculation.cancel();
        super.onStop();
    }
}
