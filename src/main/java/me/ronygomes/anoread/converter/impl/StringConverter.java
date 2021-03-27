package me.ronygomes.anoread.converter.impl;

import me.ronygomes.anoread.converter.InputConverter;

import static me.ronygomes.anoread.converter.InputConverter.throwIllegalStateExceptionIfArrayIsNotSingleton;

public class StringConverter implements InputConverter<String> {

    @Override
    public String convert(String[] parts) {
        throwIllegalStateExceptionIfArrayIsNotSingleton(parts);
        return parts[0];
    }
}
