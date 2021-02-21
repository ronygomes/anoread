package me.ronygomes.anoread.engine;

import me.ronygomes.anoread.model.ReadEngineCmd;
import me.ronygomes.anoread.model.ReadTask;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

    public void execute(ReadEngineCmd cmd) throws IOException {

        BufferedReader bin = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        cmd.getBeginConsumer().accept(bin, out, err);

        String line;
        String[] parts;
        Object input;

        for (ReadTask<?> task : cmd.getTasks()) {

            // return false to skip task
            if (!task.getBefore().apply(bin, out, err, task.getMeta())) {
                continue;
            }

            while (true) {
                out.print(task.getReadPromptFormatter().format(task.getMeta()));

                line = task.getHandler().read(bin, out, err);
                parts = task.getExtractor().extract(line);

                input = task.getConverter().convert(parts);

                // TODO: Create a validator Function<Object, Boolean>
                if (task.isValid(input)) {
                    task.getAssigner().accept(input);
                    break;
                }

                err.print(task.getErrorPromptFormatter().format(task.getMeta(), parts));
            }

            task.getAfter().apply(bin, out, err, task.getMeta());
        }

        cmd.getEndConsumer().accept(bin, out, err);
        bin.close();
    }
}
