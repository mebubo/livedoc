package org.hildan.livedoc.core.scanners.properties;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.util.Objects;

public class Property implements Comparable<Property> {

    private final String name;

    private final Class<?> type;

    private final Type genericType;

    private final AccessibleObject accessibleObject;

    private boolean required = false;

    private int order = Integer.MAX_VALUE;

    private Object defaultValue;

    public Property(String name, Class<?> type, AccessibleObject accessibleObject) {
        this(name, type, type, accessibleObject);
    }

    public Property(String name, Class<?> type, Type genericType, AccessibleObject accessibleObject) {
        this.name = name;
        this.type = type;
        this.genericType = genericType;
        this.accessibleObject = accessibleObject;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public Type getGenericType() {
        return genericType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AccessibleObject getAccessibleObject() {
        return accessibleObject;
    }

    /**
     * This comparison is the same as the one in ApiObjectFieldDoc class
     */
    @Override
    public int compareTo(Property o) {
        if (order == o.order) {
            return name.compareTo(o.name);
        } else {
            return order - o.order;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        return Objects.equals(name, property.name) && Objects.equals(type, property.type) && Objects.equals(genericType,
                property.genericType) && Objects.equals(accessibleObject, property.accessibleObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, genericType, accessibleObject);
    }

    @Override
    public String toString() {
        return name + " (" + genericType + ')';
    }
}
