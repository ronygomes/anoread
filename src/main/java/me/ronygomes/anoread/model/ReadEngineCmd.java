package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.TriConsumer;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

public class ReadEngineCmd implements Serializable {

    private TriConsumer<BufferedReader, PrintStream, PrintStream> beginConsumer;
    private TriConsumer<BufferedReader, PrintStream, PrintStream> endConsumer;
    private List<ReadTask<?>> tasks;

    public ReadEngineCmd(TriConsumer<BufferedReader, PrintStream, PrintStream> beginConsumer,
                         TriConsumer<BufferedReader, PrintStream, PrintStream> endConsumer,
                         List<ReadTask<?>> tasks) {

        this.beginConsumer = beginConsumer;
        this.endConsumer = endConsumer;
        this.tasks = tasks;
    }

    public TriConsumer<BufferedReader, PrintStream, PrintStream> getBeginConsumer() {
        return beginConsumer;
    }

    public void setBeginConsumer(TriConsumer<BufferedReader, PrintStream, PrintStream> beginConsumer) {
        this.beginConsumer = beginConsumer;
    }

    public TriConsumer<BufferedReader, PrintStream, PrintStream> getEndConsumer() {
        return endConsumer;
    }

    public void setEndConsumer(TriConsumer<BufferedReader, PrintStream, PrintStream> endConsumer) {
        this.endConsumer = endConsumer;
    }

    public List<ReadTask<?>> getTasks() {
        return tasks;
    }

    public void setTasks(List<ReadTask<?>> tasks) {
        this.tasks = tasks;
    }
}
