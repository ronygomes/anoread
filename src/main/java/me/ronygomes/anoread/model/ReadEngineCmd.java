package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

public class ReadEngineCmd implements Serializable {

    private TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer;
    private TriConsumer<InputStream, PrintStream, PrintStream> endConsumer;
    private List<ReadTask> tasks;

    public ReadEngineCmd(TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer,
                         TriConsumer<InputStream, PrintStream, PrintStream> endConsumer) {

        this.beginConsumer = beginConsumer;
        this.endConsumer = endConsumer;
    }

    public ReadEngineCmd(TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer,
                         TriConsumer<InputStream, PrintStream, PrintStream> endConsumer,
                         List<ReadTask> tasks) {

        this.beginConsumer = beginConsumer;
        this.endConsumer = endConsumer;
        this.tasks = tasks;
    }

    public TriConsumer<InputStream, PrintStream, PrintStream> getBeginConsumer() {
        return beginConsumer;
    }

    public void setBeginConsumer(TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer) {
        this.beginConsumer = beginConsumer;
    }

    public TriConsumer<InputStream, PrintStream, PrintStream> getEndConsumer() {
        return endConsumer;
    }

    public void setEndConsumer(TriConsumer<InputStream, PrintStream, PrintStream> endConsumer) {
        this.endConsumer = endConsumer;
    }

    public List<ReadTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ReadTask> tasks) {
        this.tasks = tasks;
    }
}
