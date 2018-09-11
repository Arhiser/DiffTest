package com.arhiser.difftest.objdiff.ui.controller;

import com.arhiser.difftest.objdiff.difs.DiffService;
import com.arhiser.difftest.objdiff.dispatch.ChangeDispatcher;
import com.arhiser.difftest.objdiff.dispatch.Copyable;

public class Controller<M extends Copyable<M>> {
    protected ChangeDispatcher<M> changeDispatcher;
    protected DiffService diffService;

    public Controller(M model, DiffService diffService) {
        changeDispatcher = new ChangeDispatcher<>(model);
        this.diffService = diffService;
    }

    public void addChangesListener(ChangeDispatcher.ChangesListener changesListener) {
        changeDispatcher.addListener(changesListener);
    }

    public void removeChangesListener(ChangeDispatcher.ChangesListener changesListener) {
        changeDispatcher.removeListener(changesListener);
    }

    public void onViewAttached() {
        changeDispatcher.dispatchAllModel(diffService);
    }
}
