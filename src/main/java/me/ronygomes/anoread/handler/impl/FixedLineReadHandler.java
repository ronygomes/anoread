package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FixedLineReadHandler implements ReadHandler {

    private static final String PROMPT_LINE = "(%d / %d) > ";

    private int lineCount;
    private String joiner;
    private boolean showPromptLine;

    public FixedLineReadHandler(int lineCount, String joiner, boolean showPromptLine) {
        this.lineCount = lineCount;
        this.joiner = joiner;
        this.showPromptLine = showPromptLine;
    }

    @Override
    public String read(BufferedReader in, PrintStream out, PrintStream err) throws IOException {
        List<String> lines = new ArrayList<>();

        err.println();
        for (int i = 0; i < lineCount; i++) {
            if (showPromptLine) {
                err.printf(PROMPT_LINE, i + 1, lineCount);
            }

            lines.add(in.readLine());
        }

        return lines.stream().collect(Collectors.joining(joiner));
    }
}
