package me.ronygomes.anoread.converter;

import me.ronygomes.anoread.model.ConversionPayload;

public interface InputConverter<T> {

    T convert(String[] parts, ConversionPayload payload);

    static void throwIllegalStateExceptionIfArrayIsNotSingleton(Object[] data) {
        if (data == null || data.length != 1) {
            throw new IllegalStateException("Given Single value extractor but provided multiple data");
        }
    }
}
