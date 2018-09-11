package com.arhiser.difftest.objdiff.dispatch;

import com.arhiser.difftest.objdiff.difs.Cancellable;
import com.arhiser.difftest.objdiff.difs.DiffService;

import java.util.HashSet;

import de.danielbechler.diff.node.DiffNode;

public class ChangeDispatcher<T extends Copyable<T>> {
    private T currentValue;
    private T changed;

    private HashSet<ChangesListener> listeners = new HashSet<>();

    public ChangeDispatcher(T initialValue) {
        this.changed = initialValue;
    }

    public T startChanges() {
        if (currentValue != null) {
            changed = currentValue.copy();
        }
        return changed;
    }

    public Cancellable dispatchChanges(DiffService diffService) {
        return diffService.getDiff(currentValue, changed, diff -> {
            if (diff.getDifs().getState() != DiffNode.State.UNTOUCHED) {
                for (ChangesListener listener : listeners) {
                    listener.onChanges(this, diff);
                }
            }
            currentValue = changed;
        });
    }

    public void dispatchAllModel(DiffService diffService) {
        T model = currentValue != null ? currentValue : changed;
        currentValue = null;
        changed = model;
        dispatchChanges(diffService);
    }

    public void addListener(ChangesListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangesListener listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public interface ChangesListener {
        void onChanges(ChangeDispatcher source, DiffService.DiffData changes);
    }
}
