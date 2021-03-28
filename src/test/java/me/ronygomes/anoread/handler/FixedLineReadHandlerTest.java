package me.ronygomes.anoread.handler;

import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedLineReadHandlerTest {

    private static final String TEST_JOINER = ";";

    private static final String INPUT0 = "";
    private static final String INPUT1 = String.join(System.lineSeparator(), "line1");
    private static final String INPUT2 = String.join(System.lineSeparator(), "line1", "line2");

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

        ReadHandler rh = new FixedLineReadHandler(1, TEST_JOINER, false, false);
        assertEquals("", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals(new byte[0], baosErr.toByteArray());
    }

    @Test
    void testCanReadSingleLineInput() throws IOException {
        this.in = toInputStream(INPUT1);

        ReadHandler rh = new FixedLineReadHandler(1, TEST_JOINER, false, false);

        assertEquals("line1", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals(new byte[0], baosErr.toByteArray());
    }

    @Test
    void testCanReadMultiLineInput() throws IOException {
        this.in = toInputStream(INPUT2);

        ReadHandler rh = new FixedLineReadHandler(2, TEST_JOINER, false, false);
        assertEquals("line1;line2", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals(new byte[0], baosErr.toByteArray());
    }

    @Test
    void testReadMultiLineInputWithPrompt() throws IOException {
        this.in = toInputStream(INPUT2);

        ReadHandler rh = new FixedLineReadHandler(2, TEST_JOINER, true, false);
        assertEquals("line1;line2", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals("(1/2)> (2/2)> ".getBytes(), baosErr.toByteArray());
    }

    @Test
    void testReadMultiLineInputWithInitialNewLine() throws IOException {
        this.in = toInputStream(INPUT2);

        ReadHandler rh = new FixedLineReadHandler(2, TEST_JOINER, false, true);
        assertEquals("line1;line2", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals(System.lineSeparator().getBytes(), baosErr.toByteArray());
    }

    @Test
    void testReadMultiLineInputWithInitialNewLineAndPromptLine() throws IOException {
        this.in = toInputStream(INPUT2);

        ReadHandler rh = new FixedLineReadHandler(2, TEST_JOINER, true, true);
        assertEquals("line1;line2", rh.read(in, out, err));

        assertArrayEquals(new byte[0], baosOut.toByteArray());
        assertArrayEquals((System.lineSeparator() + "(1/2)> (2/2)> ").getBytes(), baosErr.toByteArray());
    }

    private InputStream toInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}
