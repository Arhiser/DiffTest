package com.arhiser.difftest.ui.ViewHolder;

import android.view.View;

public interface ViewHolder<T> {
    void bind(T data);
    View getView();
}
