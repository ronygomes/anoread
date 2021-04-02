package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

public class ClassLevelHookProvider implements HookProvider {

    private HookProvider parentHookProvider;
    private ReadHandler handler;
    private ReadPromptFormatter readPromptFormatter;
    private ErrorPromptFormatter errorPromptFormatter;

    public ClassLevelHookProvider(HookProvider parentHookProvider,
                                  ReadHandler handler,
                                  ReadPromptFormatter readPromptFormatter,
                                  ErrorPromptFormatter errorPromptFormatter) {

        this.parentHookProvider = parentHookProvider;
        this.handler = handler;
        this.readPromptFormatter = readPromptFormatter;
        this.errorPromptFormatter = errorPromptFormatter;
    }

    @Override
    public ReadHandler getHandler() {
        if (Objects.isNull(handler)) {
            return parentHookProvider.getHandler();
        }

        return handler;
    }

    @Override
    public ReadPromptFormatter getReadPromptFormatter() {
        if (Objects.isNull(readPromptFormatter)) {
            return parentHookProvider.getReadPromptFormatter();
        }

        return readPromptFormatter;
    }

    @Override
    public InputExtractor getExtractor(Class<?> type) {
        return parentHookProvider.getExtractor(type);
    }

    @Override
    public InputConverter<?> getConverter(Class<?> type) {
        return parentHookProvider.getConverter(type);
    }

    @Override
    public Consumer<Object> getAssigner(Field field, Object object) {
        return parentHookProvider.getAssigner(field, object);
    }

    @Override
    public ErrorPromptFormatter getErrorPromptFormatter() {
        if (Objects.isNull(errorPromptFormatter)) {
            return parentHookProvider.getErrorPromptFormatter();
        }

        return errorPromptFormatter;
    }
}
