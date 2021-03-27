package me.ronygomes.anoread.handler;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadHandlerTest {

    @Test
    void testToByteArray() {
        assertThrows(NullPointerException.class, () -> ReadHandler.toByteArray(null));

        List<Byte> input1 = new ArrayList<>();
        input1.add(new Byte("5"));
        byte[] res1 = ReadHandler.toByteArray(input1);
        assertArrayEquals(new byte[]{5}, res1);

        List<Byte> input2 = new ArrayList<>();
        input2.add(new Byte("1"));
        input2.add(new Byte("1"));
        input2.add(new Byte("0"));

        byte[] res2 = ReadHandler.toByteArray(input2);
        assertArrayEquals(new byte[]{1, 1, 0}, res2);
    }

    @Test
    void testReadLineWithUnixLineEnding() throws IOException {
        String input1 = "line1\nline2";
        String input2 = "line1";
        String input3 = "line1\n";

        InputStream is1 = toInputStream(input1);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is1, "\n"));
        assertArrayEquals("line2".getBytes(), ReadHandler.readLine(is1, "\n"));

        InputStream is2 = toInputStream(input2);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is2, "\n"));

        InputStream is3 = toInputStream(input3);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is3, "\n"));
    }

    @Test
    void testReadLineWithWindowsLineEnding() throws IOException {
        String input1 = "line1\r\nline2";
        String input2 = "line1";
        String input3 = "line1\r\n";

        InputStream is1 = toInputStream(input1);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is1, "\r\n"));
        assertArrayEquals("line2".getBytes(), ReadHandler.readLine(is1, "\r\n"));

        InputStream is2 = toInputStream(input2);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is2, "\r\n"));

        InputStream is3 = toInputStream(input3);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is3, "\r\n"));
    }

    @Test
    void testReadLineWithOldMacLineEnding() throws IOException {
        String input1 = "line1\rline2";
        String input2 = "line1";
        String input3 = "line1\r";

        InputStream is1 = toInputStream(input1);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is1, "\r"));
        assertArrayEquals("line2".getBytes(), ReadHandler.readLine(is1, "\r"));

        InputStream is2 = toInputStream(input2);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is2, "\r"));

        InputStream is3 = toInputStream(input3);
        assertArrayEquals("line1".getBytes(), ReadHandler.readLine(is3, "\r"));
    }

    private InputStream toInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }
}
