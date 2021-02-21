package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiLineReadHandler implements ReadHandler {

    private static final String PROMPT_LINE = "> ";

    private String joiner;

    public MultiLineReadHandler(String joiner) {
        this.joiner = joiner;
    }

    @Override
    public String read(BufferedReader in, PrintStream out, PrintStream err) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = in.readLine();
        while (!line.trim().equals("")) {
            lines.add(line);

            err.print(PROMPT_LINE);
            line = in.readLine();
        }

        return lines.stream().collect(Collectors.joining(joiner));
    }
}
