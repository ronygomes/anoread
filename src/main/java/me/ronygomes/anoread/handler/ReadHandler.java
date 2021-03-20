package me.ronygomes.anoread.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public interface ReadHandler {

    String read(InputStream in, PrintStream out, PrintStream err) throws IOException;

    static String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;

        do {
            c = in.read();
            if (c == '\n')
                break;
            sb.append((char) c);
        } while (c != -1);

        return sb.toString();
    }
}
