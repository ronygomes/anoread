package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

public class FieldLevelEngineComponentProvider implements EngineComponentProvider {

    private EngineComponentProvider parentEngineComponentProvider;

    private ReadHandler handler;
    private ReadPromptFormatter readPromptFormatter;

    private InputExtractor extractor;
    private InputConverter<?> converter;

    private ErrorPromptFormatter errorPromptFormatter;

    public FieldLevelEngineComponentProvider(EngineComponentProvider parentEngineComponentProvider,
                                             ReadHandler handler,
                                             ReadPromptFormatter readPromptFormatter,
                                             InputExtractor extractor,
                                             InputConverter<?> converter,
                                             ErrorPromptFormatter errorPromptFormatter) {

        this.parentEngineComponentProvider = parentEngineComponentProvider;
        this.handler = handler;
        this.readPromptFormatter = readPromptFormatter;
        this.extractor = extractor;
        this.converter = converter;
        this.errorPromptFormatter = errorPromptFormatter;
    }

    @Override
    public ReadHandler getHandler() {
        if (Objects.isNull(handler)) {
            return parentEngineComponentProvider.getHandler();
        }

        return handler;
    }

    @Override
    public ReadPromptFormatter getReadPromptFormatter() {
        if (Objects.isNull(readPromptFormatter)) {
            return parentEngineComponentProvider.getReadPromptFormatter();
        }

        return readPromptFormatter;
    }

    @Override
    public InputExtractor getExtractor(Class<?> type) {
        if (Objects.isNull(extractor)) {
            return parentEngineComponentProvider.getExtractor(type);
        }

        return extractor;
    }

    @Override
    public InputConverter<?> getConverter(Class<?> type) {
        if (Objects.isNull(converter)) {
            return parentEngineComponentProvider.getConverter(type);
        }

        return converter;
    }

    @Override
    public Consumer<Object> getAssigner(Field field, Object object) {
        return parentEngineComponentProvider.getAssigner(field, object);
    }

    @Override
    public ErrorPromptFormatter getErrorPromptFormatter() {
        if (Objects.isNull(errorPromptFormatter)) {
            return parentEngineComponentProvider.getErrorPromptFormatter();
        }

        return errorPromptFormatter;
    }
}
