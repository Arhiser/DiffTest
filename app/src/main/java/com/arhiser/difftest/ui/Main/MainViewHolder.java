package com.arhiser.difftest.ui.Main;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.arhiser.difftest.R;
import com.arhiser.difftest.objdiff.ui.ViewHolder.ChangesViewHolder;

public class MainViewHolder extends ChangesViewHolder<MainController> {

    private EditText tempInput;

    public MainViewHolder(View root, MainController controller) {
        super(root, controller);
        tempInput = (EditText) root.findViewById(R.id.temp_input);
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
                controller.onTemperatureTextChanged(text);
            }
        });
    }
}
