package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;
import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class ReadLifeCycleHookHolder {

    private TriConsumer<InputStream, PrintStream, PrintStream> readBegin;
    private TriConsumer<InputStream, PrintStream, PrintStream> readEnd;

    private QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre;
    private QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost;
    private Consumer<Object> validator;

    public TriConsumer<InputStream, PrintStream, PrintStream> getReadBegin() {
        return readBegin;
    }

    public void setReadBegin(TriConsumer<InputStream, PrintStream, PrintStream> readBegin) {
        this.readBegin = readBegin;
    }

    public TriConsumer<InputStream, PrintStream, PrintStream> getReadEnd() {
        return readEnd;
    }

    public void setReadEnd(TriConsumer<InputStream, PrintStream, PrintStream> readEnd) {
        this.readEnd = readEnd;
    }

    public QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> getReadEachPre() {
        return readEachPre;
    }

    public void setReadEachPre(QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre) {
        this.readEachPre = readEachPre;
    }

    public QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> getReadEachPost() {
        return readEachPost;
    }

    public void setReadEachPost(QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost) {
        this.readEachPost = readEachPost;
    }

    public Consumer<Object> getValidator() {
        return validator;
    }

    public void setValidator(Consumer<Object> validator) {
        this.validator = validator;
    }
}
