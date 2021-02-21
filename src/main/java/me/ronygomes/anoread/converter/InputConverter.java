package me.ronygomes.anoread.converter;

public interface InputConverter<T> {

    T convert(String[] parts);
}
