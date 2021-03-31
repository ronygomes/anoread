package me.ronygomes.anoread.context;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnoreadContext {

    public static final Map<Class<?>, InputConverter<?>> CONVERTER = new HashMap<>();
    public static final Map<Class<?>, InputExtractor> EXTRACTOR = new HashMap<>();

    public static InputConverter<?> getDefaultConverterBytType(Class<?> type) {
        return CONVERTER.get(type);
    }

    public static InputExtractor getDefaultExtractorByType(Class<?> type) {
        InputExtractor inputExtractor = EXTRACTOR.get(type);

        if (Objects.isNull(inputExtractor)) {
            return new SingleInputExtractor(true);
        }

        return inputExtractor;
    }
}
