package me.ronygomes.anoread.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public interface ReadHandler {

    String read(BufferedReader in, PrintStream out, PrintStream err) throws IOException;

}
