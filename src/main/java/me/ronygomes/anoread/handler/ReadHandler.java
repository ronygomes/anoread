package me.ronygomes.anoread.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface ReadHandler {

    String read(InputStream in, PrintStream out, PrintStream err) throws IOException;

    static byte[] readLine(InputStream in, String separator) throws IOException {
        List<Byte> bytes = new ArrayList<>();

        char[] separatorChars = separator.toCharArray();
        int i = 0;

        int c;
        boolean isSeparatorChar = false;
        do {
            c = in.read();
            if (c == separatorChars[i]) {
                isSeparatorChar = true;
                i++;
            }

            if (!isSeparatorChar && c != -1) {
                bytes.add((byte) c);
            }

            if (i == separatorChars.length) {
                break;
            }
        } while (c != -1);

        return toByteArray(bytes);
    }

    static byte[] toByteArray(List<Byte> bytes) {
        Objects.requireNonNull(bytes);

        int len = bytes.size();
        byte[] data = new byte[len];

        for (int i = 0; i < len; i++) {
            data[i] = bytes.get(i);
        }

        return data;
    }

    static String toUtf8String(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
