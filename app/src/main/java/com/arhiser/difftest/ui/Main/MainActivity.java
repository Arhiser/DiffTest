package com.arhiser.difftest.ui.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arhiser.difftest.R;
import com.arhiser.difftest.model.ChangeDispatcher;
import com.arhiser.difftest.ui.ViewHolder.ChangesViewHolder;
import com.arhiser.difftest.ui.ViewHolder.ViewHolder;

public class MainActivity extends AppCompatActivity {

    ViewHolder<ChangeDispatcher.DiffData> viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ChangeDispatcher<MainViewModel> changeDispatcher = new ChangeDispatcher<>(new MainViewModel(""));
        changeDispatcher.apply();
        changeDispatcher.addListener(new ChangeDispatcher.ChangesListener() {
            @Override
            public void onChanges(ChangeDispatcher source, ChangeDispatcher.DiffData changes) {
                viewHolder.bind(changes);
            }
        });
        viewHolder = new MainViewHolder(findViewById(R.id.root), new MainViewHolder.InteractionListener() {
            @Override
            public void onTemperatureEntered(String temp) {
                changeDispatcher.startChange().setTemperatureToConvert(temp);
                changeDispatcher.apply();
            }
        });
    }
}
