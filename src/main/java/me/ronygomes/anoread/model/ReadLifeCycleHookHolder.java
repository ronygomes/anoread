package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;
import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.BiConsumer;

public class ReadLifeCycleHookHolder {

    private TriConsumer<InputStream, PrintStream, PrintStream> readBegin;
    private TriConsumer<InputStream, PrintStream, PrintStream> readEnd;

    private QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre;
    private QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost;
    private BiConsumer<Object, ReadMeta> validator;

    public ReadLifeCycleHookHolder() {
    }

    public ReadLifeCycleHookHolder(QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre,
                                   QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost,
                                   BiConsumer<Object, ReadMeta> validator) {

        this.readEachPre = readEachPre;
        this.readEachPost = readEachPost;
        this.validator = validator;
    }

    public ReadLifeCycleHookHolder(TriConsumer<InputStream, PrintStream, PrintStream> readBegin,
                                   TriConsumer<InputStream, PrintStream, PrintStream> readEnd,
                                   QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre,
                                   QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost,
                                   BiConsumer<Object, ReadMeta> validator) {

        this.readBegin = readBegin;
        this.readEnd = readEnd;
        this.readEachPre = readEachPre;
        this.readEachPost = readEachPost;
        this.validator = validator;
    }

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

    public BiConsumer<Object, ReadMeta> getValidator() {
        return validator;
    }

    public void setValidator(BiConsumer<Object, ReadMeta> validator) {
        this.validator = validator;
    }
}
