package com.arhiser.difftest.ui.ViewHolder;

import android.view.View;

public abstract class BaseViewHolder<T> implements ViewHolder<T> {
    private View root;

    public BaseViewHolder(View root) {
        this.root = root;
    }

    @Override
    public View getView() {
        return root;
    }
}
