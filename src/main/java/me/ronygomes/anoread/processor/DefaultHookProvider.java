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

public class DefaultHookProvider implements HookProvider {

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
        return Objects.requireNonNull(AnoreadContext.getDefaultConverterBytType(type));
    }

    @Override
    public Consumer<Object> getAssigner(Field field, Object object, Object value) {
        return o -> {
            try {
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
}
