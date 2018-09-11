package com.arhiser.difftest.objdiff.ui.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arhiser.difftest.objdiff.difs.DiffService;
import com.arhiser.difftest.objdiff.dispatch.ChangeDispatcher;
import com.arhiser.difftest.objdiff.dispatch.Copyable;
import com.arhiser.difftest.objdiff.ui.ViewHolder.ChangesViewHolder;
import com.arhiser.difftest.objdiff.ui.controller.Controller;

public abstract class DiffBindActivity<M extends Copyable<M>, V extends ChangesViewHolder, C extends Controller<M>> extends AppCompatActivity {
    protected C controller;
    protected V viewHolder;
    protected DiffService diffService = new DiffService(4);

    private ChangeDispatcher.ChangesListener changesListener;

    protected abstract int getLayoutId();

    protected abstract C getController(M model);
    protected abstract M getModel();
    protected abstract V getViewHolder(View root);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        controller = (C)ControllerCache.getInstance().getController(this.getClass());
        if (controller == null) {
            controller = getController(getModel());
            ControllerCache.getInstance().putController(this.getClass(), controller);
        }

        viewHolder = getViewHolder(getRootView());
        changesListener = new ChangeDispatcher.ChangesListener() {
            @Override
            public void onChanges(ChangeDispatcher source, DiffService.DiffData changes) {
                viewHolder.bind(changes);
            }
        };
        controller.addChangesListener(changesListener);

        controller.onViewAttached();
    }

    protected View getRootView() {
        View view = findViewById(android.R.id.content);
        if (view == null) {
            view = getWindow().getDecorView().findViewById(android.R.id.content);
        }
        return view;
    }

    @Override
    protected void onDestroy() {
        controller.removeChangesListener(changesListener);
        if (isFinishing()) {
            ControllerCache.getInstance().removeController(this.getClass());
        }
        super.onDestroy();
    }
}
