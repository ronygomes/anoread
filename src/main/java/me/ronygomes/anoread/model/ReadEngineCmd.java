package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

public class ReadEngineCmd implements Serializable {

    private TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer;
    private TriConsumer<InputStream, PrintStream, PrintStream> endConsumer;
    private List<ReadTask<?>> tasks;

    public ReadEngineCmd(TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer,
                         TriConsumer<InputStream, PrintStream, PrintStream> endConsumer,
                         List<ReadTask<?>> tasks) {

        this.beginConsumer = beginConsumer;
        this.endConsumer = endConsumer;
        this.tasks = tasks;
    }

    public TriConsumer<InputStream, PrintStream, PrintStream> getBeginConsumer() {
        return beginConsumer;
    }

    public TriConsumer<InputStream, PrintStream, PrintStream> getEndConsumer() {
        return endConsumer;
    }

    public List<ReadTask<?>> getTasks() {
        return tasks;
    }
}
