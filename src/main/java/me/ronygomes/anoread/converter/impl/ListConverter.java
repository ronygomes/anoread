package me.ronygomes.anoread.converter.impl;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.model.ConversionPayload;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.ronygomes.anoread.context.AnoreadContext.getDefaultConverterByType;

public class ListConverter implements InputConverter<List<?>> {

    @Override
    public List<?> convert(String[] parts, ConversionPayload payload) {
        if (!payload.getPropertyType().equals(List.class)) {
            throw new IllegalStateException("ListConverter is invoked with non-list type");
        }

        if (payload.getPropertySubTypes().length != 1) {
            throw new IllegalStateException("List Expects exactly 1 sub-type");
        }

        Class<?> propertySubType = payload.getPropertySubTypes()[0];
        InputConverter<?> converter = getDefaultConverterByType(propertySubType);

        if (Objects.isNull(converter)) {
            throw new IllegalStateException("No Converter found for type: " + propertySubType);
        }

        return Arrays.stream(parts)
                .map(e -> converter.convert(new String[]{e}, null))
                .collect(Collectors.toList());
    }
}
