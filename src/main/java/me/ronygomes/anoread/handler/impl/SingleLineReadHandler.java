package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static me.ronygomes.anoread.handler.ReadHandler.readLine;

public class SingleLineReadHandler implements ReadHandler {

    @Override
    public String read(InputStream in, PrintStream out, PrintStream err) throws IOException {
        return readLine(in);
    }
}
