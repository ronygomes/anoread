package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.model.ReadTask;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public interface EngineComponentProvider {

    ReadHandler getHandler();

    ReadPromptFormatter getReadPromptFormatter();

    InputExtractor getExtractor(Class<?> type);

    InputConverter<?> getConverter(Class<?> type);

    Consumer<Object> getAssigner(final Field field, final Object object);

    ErrorPromptFormatter getErrorPromptFormatter();

    default void updateTask(Object target, Field field, ReadTask task) {
        task.setHandler(getHandler());
        task.setReadPromptFormatter(getReadPromptFormatter());
        task.setExtractor(getExtractor(field.getType()));
        task.setConverter(getConverter(field.getType()));
        task.setAssigner(getAssigner(field, target));
        task.setErrorPromptFormatter(getErrorPromptFormatter());
    }
}
