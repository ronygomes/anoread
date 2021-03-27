package me.ronygomes.anoread.handler;

import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleLineReadHandlerTest {

    private static final String INPUT0 = "";
    private static final String INPUT1 = String.join(System.lineSeparator(), "line1", "line2", "line3");
    private static final String INPUT2 = String.join(System.lineSeparator(), "line1");
    private static final String INPUT3 = "line1";

    private InputStream in;

    private PrintStream out;

    private PrintStream err;

    private ByteArrayOutputStream baosOut;

    private ByteArrayOutputStream baosErr;

    @BeforeEach
    public void setup() {
        this.baosOut = new ByteArrayOutputStream();
        this.out = new PrintStream(this.baosOut);

        this.baosErr = new ByteArrayOutputStream();
        this.err = new PrintStream(this.baosErr);

    }

    @Test
    void testCanReadEmptyInput() throws IOException {
        this.in = toInputStream(INPUT0);

        ReadHandler rh = new SingleLineReadHandler();
        assertEquals("", rh.read(in, out, err));

        assertArrayEquals(baosOut.toByteArray(), new byte[0]);
        assertArrayEquals(baosErr.toByteArray(), new byte[0]);
    }

    @Test
    void testCanReadMultiLineInput() throws IOException {
        this.in = toInputStream(INPUT1);

        ReadHandler rh = new SingleLineReadHandler();
        assertEquals("line1", rh.read(in, out, err));
        assertEquals("line2", rh.read(in, out, err));
        assertEquals("line3", rh.read(in, out, err));

        assertArrayEquals(baosOut.toByteArray(), new byte[0]);
        assertArrayEquals(baosErr.toByteArray(), new byte[0]);
    }

    @Test
    void testCanReadSingleLineInput() throws IOException {
        this.in = toInputStream(INPUT2);

        ReadHandler rh = new SingleLineReadHandler();
        assertEquals("line1", rh.read(in, out, err));

        assertArrayEquals(baosOut.toByteArray(), new byte[0]);
        assertArrayEquals(baosErr.toByteArray(), new byte[0]);
    }

    @Test
    void testCanReadWithoutLineEnding() throws IOException {
        this.in = toInputStream(INPUT3);

        ReadHandler rh = new SingleLineReadHandler();
        assertEquals("line1", rh.read(in, out, err));

        assertArrayEquals(baosOut.toByteArray(), new byte[0]);
        assertArrayEquals(baosErr.toByteArray(), new byte[0]);
    }

    private InputStream toInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}
