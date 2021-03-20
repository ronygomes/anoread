package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.ronygomes.anoread.handler.ReadHandler.readLine;

public class MultiLineReadHandler implements ReadHandler {

    private static final String PROMPT_LINE = "> ";

    private String joiner;

    public MultiLineReadHandler(String joiner) {
        this.joiner = joiner;
    }

    @Override
    public String read(InputStream in, PrintStream out, PrintStream err) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = readLine(in);
        while (!line.trim().equals("")) {
            lines.add(line);

            err.print(PROMPT_LINE);
            line = readLine(in);
        }

        return lines.stream().collect(Collectors.joining(joiner));
    }
}
