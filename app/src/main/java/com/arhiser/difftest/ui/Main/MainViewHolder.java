package com.arhiser.difftest.ui.Main;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.arhiser.difftest.R;
import com.arhiser.difftest.ui.ViewHolder.ChangesViewHolder;

public class MainViewHolder extends ChangesViewHolder {

    private EditText tempInput;
    InteractionListener listener;

    public MainViewHolder(View root, final InteractionListener interactionListener) {
        super(root);
        tempInput = (EditText) root.findViewById(R.id.temp_input);
        listener = interactionListener;
        tempInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.equals("-")) {
                    text = "";
                }
                interactionListener.onTemperatureEntered(text);
            }
        });
    }

    interface InteractionListener {
        void onTemperatureEntered(String temp);
    }
}
