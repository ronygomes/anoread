package me.ronygomes.anoread.engine;

import me.ronygomes.anoread.exception.AnoReadException;
import me.ronygomes.anoread.model.ReadEngineCmd;
import me.ronygomes.anoread.model.ReadTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class ReadEngine {

    private InputStream in;
    private PrintStream out;
    private PrintStream err;

    public ReadEngine() {
        this.in = System.in;
        this.out = System.out;
        this.err = System.err;
    }

    public ReadEngine(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        this.err = out;
    }

    public ReadEngine(InputStream in, PrintStream out, PrintStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public void execute(ReadEngineCmd cmd) throws IOException {

        if (cmd.getTasks().isEmpty()) {
            return;
        }

        cmd.getBeginConsumer().accept(in, out, err);

        String line;
        String[] parts = null;
        Object input;
        boolean doContinue;

        for (ReadTask<?> task : cmd.getTasks()) {

            boolean processThisTask = task.getBefore().apply(in, out, err, task.getMeta());
            if (!processThisTask) {
                continue;
            }

            doContinue = true;
            while (doContinue) {
                out.print(task.getReadPromptFormatter().format(task.getMeta()));

                line = task.getHandler().read(in, out, err);

                try {
                    parts = task.getExtractor().extract(line);

                    input = task.getConverter().convert(parts);

                    task.validate(input);
                    task.getAssigner().accept(input);

                    doContinue = false;
                } catch (AnoReadException e) {
                    err.print(task.getErrorPromptFormatter().format(task.getMeta(), line, e));
                }
            }

            task.getAfter().accept(in, out, err, task.getMeta());
        }

        cmd.getEndConsumer().accept(in, out, err);
    }
}
