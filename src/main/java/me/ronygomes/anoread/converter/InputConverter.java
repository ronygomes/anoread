package me.ronygomes.anoread.converter;

public interface InputConverter<T> {

    T convert(String[] parts);

    static void throwIllegalStateExceptionIfArrayIsNotSingleton(Object[] data) {
        if (data == null || data.length != 1) {
            throw new IllegalStateException("Given Single value extractor but provided multiple data");
        }
    }
}
