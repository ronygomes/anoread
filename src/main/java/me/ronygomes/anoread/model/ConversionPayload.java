package me.ronygomes.anoread.model;

import java.util.HashMap;
import java.util.Map;

public class ConversionPayload {

    private Class<?> propertyType;
    private Class<?>[] propertySubTypes;
    private Map<Object, Object> extras;

    public ConversionPayload() {
        this.propertySubTypes = new Class[0];
        this.extras = new HashMap<>();
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    public Class<?>[] getPropertySubTypes() {
        return propertySubTypes;
    }

    public void setPropertySubTypes(Class<?>[] propertySubTypes) {
        this.propertySubTypes = propertySubTypes;
    }

    public Map<Object, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<Object, Object> extras) {
        this.extras = extras;
    }
}
