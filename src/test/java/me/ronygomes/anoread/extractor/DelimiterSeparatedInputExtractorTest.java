package me.ronygomes.anoread.extractor;

import me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DelimiterSeparatedInputExtractorTest {

    private static final String CUSTOM_DELIMITER = ";";

    private static final String INPUT0 = null;
    private static final String INPUT1 = "a, b ,c , d";
    private static final String INPUT2 = " data ";
    private static final String INPUT3 = "";
    private static final String INPUT4 = "   ";
    private static final String DELIMITER_INPUT = "a; b ;c ; d";

    @Test
    void testDefaultDelimiterWithTrim() {
        InputExtractor ie = new DelimiterSeparatedInputExtractor(true);

        assertThrows(NullPointerException.class, () -> ie.extract(INPUT0));
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, ie.extract(INPUT1));
        assertArrayEquals(new String[]{"data"}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT4));
    }

    @Test
    void testDefaultDelimiterWithoutTrim() {
        InputExtractor ie = new DelimiterSeparatedInputExtractor(false);

        assertThrows(NullPointerException.class, () -> ie.extract(INPUT0));
        assertArrayEquals(new String[]{"a", " b ", "c ", " d"}, ie.extract(INPUT1));
        assertArrayEquals(new String[]{" data "}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{"   "}, ie.extract(INPUT4));
    }

    @Test
    void testCustomDelimiterWithTrim() {
        InputExtractor ie = new DelimiterSeparatedInputExtractor(CUSTOM_DELIMITER, true);

        assertThrows(NullPointerException.class, () -> ie.extract(INPUT0));
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, ie.extract(DELIMITER_INPUT));
        assertArrayEquals(new String[]{"data"}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT4));
    }

    @Test
    void testCustomDelimiterWithoutTrim() {
        InputExtractor ie = new DelimiterSeparatedInputExtractor(CUSTOM_DELIMITER, false);

        assertThrows(NullPointerException.class, () -> ie.extract(INPUT0));
        assertArrayEquals(new String[]{"a", " b ", "c ", " d"}, ie.extract(DELIMITER_INPUT));
        assertArrayEquals(new String[]{" data "}, ie.extract(INPUT2));
        assertArrayEquals(new String[]{""}, ie.extract(INPUT3));
        assertArrayEquals(new String[]{"   "}, ie.extract(INPUT4));
    }
}
