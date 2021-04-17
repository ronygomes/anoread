package me.ronygomes.anoread.converter.impl;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.model.ConversionPayload;

import static me.ronygomes.anoread.converter.InputConverter.throwIllegalStateExceptionIfArrayIsNotSingleton;

public class StringConverter implements InputConverter<String> {

    @Override
    public String convert(String[] parts, ConversionPayload payload) {
        throwIllegalStateExceptionIfArrayIsNotSingleton(parts);
        return parts[0];
    }
}
