package com.arhiser.difftest.model;

import com.arhiser.difftest.difs.AndroidIntrospector;

import java.util.HashSet;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

public class ChangeDispatcher<T extends Copyable<T>> {
    private T currentValue;
    private T changed;

    private HashSet<ChangesListener> listeners = new HashSet<>();

    public ChangeDispatcher(T initialValue) {
        this.changed = initialValue;
    }

    public T startChange() {
        changed = currentValue.copy();
        return changed;
    }

    public void apply() {
        ObjectDifferBuilder builder = ObjectDifferBuilder.startBuilding();
        builder.introspection().setDefaultIntrospector(new AndroidIntrospector());
        DiffNode changes = builder.build().compare(changed, currentValue);
        for(ChangesListener listener: listeners) {
            listener.onChanges(this, new DiffData<>(changed, changes));
        }
        currentValue = changed;
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
        void onChanges(ChangeDispatcher source, DiffData changes);
    }

    public static class DiffData<T> {
        private T object;
        private DiffNode difs;

        public DiffData(T object, DiffNode difs) {
            this.object = object;
            this.difs = difs;
        }

        public T getObject() {
            return object;
        }

        public DiffNode getDifs() {
            return difs;
        }
    }
}
