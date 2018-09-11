package com.arhiser.difftest.objdiff.difs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.danielbechler.diff.access.PropertyAwareAccessor;
import de.danielbechler.diff.instantiation.TypeInfo;
import de.danielbechler.diff.introspection.PropertyAccessor;

public class AndroidIntrospector implements de.danielbechler.diff.introspection.Introspector  {

    public TypeInfo introspect(final Class<?> type) {
        final TypeInfo typeInfo = new TypeInfo(type);

        Method[] methods = type.getDeclaredMethods();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.getName().contains("$") && !field.getName().contains("serialVersionUI")) {
                final String propertyName = field.getName();
                Method getMethod = null;
                Method setMethod = null;
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase("get" + field.getName())) {
                        getMethod = method;
                    } else if (method.getName().equalsIgnoreCase("is" + field.getName())) {
                        getMethod = method;
                    }
                    if (method.getName().equalsIgnoreCase("set" + field.getName())) {
                        setMethod = method;
                    }
                }
                final PropertyAwareAccessor accessor = new PropertyAccessor(propertyName, getMethod, setMethod);
                typeInfo.addPropertyAccessor(accessor);
            }
        }
        return typeInfo;
    }

}
