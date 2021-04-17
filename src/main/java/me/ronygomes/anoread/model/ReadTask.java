package me.ronygomes.anoread.model;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ReadTask implements Serializable {

    private ReadMeta meta;

    private QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> before;
    private QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> after;

    private ReadHandler handler;
    private ReadPromptFormatter readPromptFormatter;

    private InputExtractor extractor;
    private InputConverter<?> converter;
    private ConversionPayload payload;

    private BiConsumer<Object, ReadMeta> validator;
    private Consumer<Object> assigner;
    private ErrorPromptFormatter errorPromptFormatter;

    public ReadMeta getMeta() {
        return meta;
    }

    public void setMeta(ReadMeta meta) {
        this.meta = meta;
    }

    public QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> getBefore() {
        return before;
    }

    public void setBefore(QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> before) {
        this.before = before;
    }

    public QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> getAfter() {
        return after;
    }

    public void setAfter(QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> after) {
        this.after = after;
    }

    public ReadHandler getHandler() {
        return handler;
    }

    public void setHandler(ReadHandler handler) {
        this.handler = handler;
    }

    public ReadPromptFormatter getReadPromptFormatter() {
        return readPromptFormatter;
    }

    public void setReadPromptFormatter(ReadPromptFormatter readPromptFormatter) {
        this.readPromptFormatter = readPromptFormatter;
    }

    public InputExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(InputExtractor extractor) {
        this.extractor = extractor;
    }

    public InputConverter<?> getConverter() {
        return converter;
    }

    public void setConverter(InputConverter<?> converter) {
        this.converter = converter;
    }

    public ConversionPayload getPayload() {
        return payload;
    }

    public void setPayload(ConversionPayload payload) {
        this.payload = payload;
    }

    public BiConsumer<Object, ReadMeta> getValidator() {
        return validator;
    }

    public void setValidator(BiConsumer<Object, ReadMeta> validator) {
        this.validator = validator;
    }

    public Consumer<Object> getAssigner() {
        return assigner;
    }

    public void setAssigner(Consumer<Object> assigner) {
        this.assigner = assigner;
    }

    public ErrorPromptFormatter getErrorPromptFormatter() {
        return errorPromptFormatter;
    }

    public void setErrorPromptFormatter(ErrorPromptFormatter errorPromptFormatter) {
        this.errorPromptFormatter = errorPromptFormatter;
    }
}
