package com.arhiser.difftest.objdiff.ui.ViewHolder;

import android.view.View;

public interface ViewHolder<T> {
    void bind(T data);
    View getView();
}
