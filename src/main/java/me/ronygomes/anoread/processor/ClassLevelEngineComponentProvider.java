package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

public class ClassLevelEngineComponentProvider implements EngineComponentProvider {

    private EngineComponentProvider parentEngineComponentProvider;
    private ReadHandler handler;
    private ReadPromptFormatter readPromptFormatter;
    private ErrorPromptFormatter errorPromptFormatter;

    public ClassLevelEngineComponentProvider(EngineComponentProvider parentEngineComponentProvider,
                                             ReadHandler handler,
                                             ReadPromptFormatter readPromptFormatter,
                                             ErrorPromptFormatter errorPromptFormatter) {

        this.parentEngineComponentProvider = parentEngineComponentProvider;
        this.handler = handler;
        this.readPromptFormatter = readPromptFormatter;
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
        return parentEngineComponentProvider.getExtractor(type);
    }

    @Override
    public InputConverter<?> getConverter(Class<?> type) {
        return parentEngineComponentProvider.getConverter(type);
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
