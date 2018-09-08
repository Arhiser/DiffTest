package com.arhiser.difftest.model;

import com.arhiser.difftest.difs.Cancellable;
import com.arhiser.difftest.difs.DiffService;

import java.util.HashSet;

public class ChangeDispatcher<T extends Copyable<T>> {
    private T currentValue;
    private T changed;

    private HashSet<ChangesListener> listeners = new HashSet<>();

    public ChangeDispatcher(T initialValue) {
        this.changed = initialValue;
    }

    public T startChange() {
        if (currentValue != null) {
            changed = currentValue.copy();
        }
        return changed;
    }

    public Cancellable apply(DiffService diffService) {
        return diffService.getDiff(currentValue, changed, diff -> {
            for(ChangesListener listener: listeners) {
                listener.onChanges(this, diff);
            }
            currentValue = changed;
        });
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
