package me.ronygomes.anoread.context;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnoreadContext {

    private static final Map<Class<?>, InputConverter<?>> CONVERTER = new HashMap<>();
    private static final Map<Class<?>, InputExtractor> EXTRACTOR = new HashMap<>();

    static {
        CONVERTER.put(Integer.class, new IntegerConverter());
        CONVERTER.put(String.class, new StringConverter());
    }

    public static InputConverter<?> getDefaultConverterByType(Class<?> type) {
        return CONVERTER.get(type);
    }

    public static InputExtractor getDefaultExtractorByType(Class<?> type) {
        InputExtractor inputExtractor = EXTRACTOR.get(type);

        if (Objects.isNull(inputExtractor)) {
            return new SingleInputExtractor(true);
        }

        return inputExtractor;
    }

    public static int converterCount() {
        return CONVERTER.size();
    }

    public static int extractorCount() {
        return EXTRACTOR.size();
    }
}
