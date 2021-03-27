package me.ronygomes.anoread.extractor;

import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleInputExtractorTest {

    private static final String INPUT1 = "";
    private static final String INPUT2 = " data ";
    private static final String INPUT3 = "data ";
    private static final String INPUT4 = " data";

    @Test
    void testSingleLineExtractor() {
        InputExtractor ie = new SingleInputExtractor();

        assertThrows(NullPointerException.class, () -> ie.extract(null));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT1));
        assertArrayEquals(new String[]{"data"}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{"data"}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{"data"}, ie.extract(INPUT4));
    }

    @Test
    void testSingleLineExtractorWithoutTrim() {
        InputExtractor ie = new SingleInputExtractor(false);

        assertThrows(NullPointerException.class, () -> ie.extract(null));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT1));
        assertArrayEquals(new String[]{" data "}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{"data "}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{" data"}, ie.extract(INPUT4));
    }
}
