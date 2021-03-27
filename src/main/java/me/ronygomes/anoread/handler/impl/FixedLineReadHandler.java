package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.ronygomes.anoread.handler.ReadHandler.readLine;
import static me.ronygomes.anoread.handler.ReadHandler.toUtf8String;

public class FixedLineReadHandler implements ReadHandler {

    private static final String PROMPT_LINE = "(%d/%d)> ";

    private int lineCount;
    private String joiner;
    private boolean showPromptLine;
    private boolean startInNewLine;

    public FixedLineReadHandler(int lineCount, String joiner, boolean showPromptLine, boolean startInNewLine) {
        this.lineCount = lineCount;
        this.joiner = joiner;
        this.showPromptLine = showPromptLine;
        this.startInNewLine = startInNewLine;
    }

    @Override
    public String read(InputStream in, PrintStream out, PrintStream err) throws IOException {
        List<String> lines = new ArrayList<>();

        if (startInNewLine) {
            err.println();
        }

        for (int i = 0; i < lineCount; i++) {
            if (showPromptLine) {
                err.printf(PROMPT_LINE, i + 1, lineCount);
            }

            lines.add(toUtf8String(readLine(in, System.lineSeparator())));
        }

        return lines.stream().collect(Collectors.joining(joiner));
    }
}
