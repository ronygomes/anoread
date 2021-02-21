package me.ronygomes.anoread.handler;

import java.io.InputStream;
import java.io.PrintStream;

public interface ReadHandler {

    String read(InputStream in, PrintStream out, PrintStream err);

}
