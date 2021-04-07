package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.context.AnoreadContext;
import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

public class DefaultEngineComponentProvider implements EngineComponentProvider {

    private static EngineComponentProvider INSTANCE;

    @Override
    public ReadHandler getHandler() {
        return new SingleLineReadHandler();
    }

    @Override
    public ReadPromptFormatter getReadPromptFormatter() {
        return new BasicReadPromptFormatter(false, false);
    }

    @Override
    public InputExtractor getExtractor(Class<?> type) {
        return AnoreadContext.getDefaultExtractorByType(type);
    }

    @Override
    public InputConverter<?> getConverter(Class<?> type) {
        return Objects.requireNonNull(AnoreadContext.getDefaultConverterByType(type));
    }

    @Override
    public Consumer<Object> getAssigner(final Field field, final Object object) {
        return value -> {
            try {
                field.setAccessible(true);
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public ErrorPromptFormatter getErrorPromptFormatter() {
        return new BasicErrorPromptFormatter(false);
    }

    public static EngineComponentProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (DefaultEngineComponentProvider.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DefaultEngineComponentProvider();
                }
            }
        }

        return INSTANCE;
    }
}
