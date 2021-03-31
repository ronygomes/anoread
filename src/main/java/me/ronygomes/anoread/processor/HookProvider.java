package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public interface HookProvider {

    ReadHandler getHandler();

    ReadPromptFormatter getReadPromptFormatter();

    InputExtractor getExtractor(Class<?> type);

    InputConverter<?> getConverter(Class<?> type);

    Consumer<Object> getAssigner(Field field, Object object, Object value);

    ErrorPromptFormatter getErrorPromptFormatter();
}
