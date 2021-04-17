package me.ronygomes.anoread.converter;

import me.ronygomes.anoread.converter.impl.StringConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringConverterTest {

    public static final String[] INPUT0 = null;
    public static final String[] INPUT1 = {"a", "b"};
    public static final String[] INPUT2 = {""};
    public static final String[] INPUT3 = {" "};
    public static final String[] INPUT4 = {" data "};

    @Test
    void testStringConverter() {

        InputConverter<String> ic = new StringConverter();

        assertThrows(IllegalStateException.class, () -> ic.convert(INPUT0, null));
        assertThrows(IllegalStateException.class, () -> ic.convert(INPUT1, null));

        assertEquals("", ic.convert(INPUT2, null));
        assertEquals(" ", ic.convert(INPUT3, null));
        assertEquals(" data ", ic.convert(INPUT4, null));
    }
}
