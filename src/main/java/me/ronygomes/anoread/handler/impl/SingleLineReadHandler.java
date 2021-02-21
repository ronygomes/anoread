package me.ronygomes.anoread.handler.impl;

import me.ronygomes.anoread.handler.ReadHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class SingleLineReadHandler implements ReadHandler {

    @Override
    public String read(BufferedReader in, PrintStream out, PrintStream err) throws IOException {
        return in.readLine();
    }
}
