package me.ronygomes.anoread.converter.impl;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.exception.ConversionException;
import me.ronygomes.anoread.model.ConversionPayload;

import static me.ronygomes.anoread.converter.InputConverter.throwIllegalStateExceptionIfArrayIsNotSingleton;

public class IntegerConverter implements InputConverter<Integer> {

    @Override
    public Integer convert(String[] parts, ConversionPayload payload) {
        throwIllegalStateExceptionIfArrayIsNotSingleton(parts);

        try {
            return Integer.parseInt(parts[0]);
        } catch (NumberFormatException ne) {
            throw new ConversionException("Invalid java.lang.Integer: " + parts[0]);
        }
    }
}
