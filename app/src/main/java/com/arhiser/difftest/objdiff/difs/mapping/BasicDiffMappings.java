package com.arhiser.difftest.objdiff.difs.mapping;

import java.util.ArrayList;

public class BasicDiffMappings extends DiffMappings {

    private ArrayList<Mapping> mappings = new ArrayList<>();

    @Override
    public void map(Object receiver, Object value) {
        for(Mapping mapping: mappings) {
            if (mapping.getTargetClass().isAssignableFrom(receiver.getClass())
                    &&  (value == null || mapping.getValueClass().isAssignableFrom(value.getClass()))) {
                mapping.map(receiver, value);
                return;
            }
        }
        throw new MappingNotFoundException("Can't find mapping for type: " + receiver.getClass().getName());
    }

    public <T, V> BasicDiffMappings addMapping(Class<T> targetClass, Class<V> valueClass, MapFunc<T, V> mapFunc) {
        mappings.add(new Mapping<>(targetClass, valueClass, mapFunc));
        return this;
    }

    public interface MapFunc<T, V> {
        void map(T object, V value);
    }

    private static class Mapping<T, V> {
        private Class<T> targetClass;
        private Class<V> valueClass;
        private MapFunc<T, V> mapFunc;

        public Mapping(Class<T> targetClass, Class<V> valueClass, MapFunc<T, V> mapFunc) {
            this.targetClass = targetClass;
            this.valueClass = valueClass;
            this.mapFunc = mapFunc;
        }

        public Class<T> getTargetClass() {
            return targetClass;
        }

        public Class<V> getValueClass() {
            return valueClass;
        }

        public void map(T target, V value) {
            mapFunc.map(target, value);
        }
    }

    private class MappingNotFoundException extends RuntimeException {
        public MappingNotFoundException(String message) {
            super(message);
        }
    }
}
