package me.ronygomes.anoread.converter;

import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.exception.ConversionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerConverterTest {

    public static final String[] INPUT0 = null;
    public static final String[] INPUT1 = {"a", "b"};
    public static final String[] INPUT2 = {""};
    public static final String[] INPUT3 = {" "};
    public static final String[] INPUT4 = {" 3 "};
    public static final String[] INPUT5 = {"1000"};
    public static final String[] INPUT6 = {"-1000"};
    public static final String[] INPUT7 = {"-0"};
    public static final String[] INPUT8 = {"99999999999999999999999999"};
    public static final String[] INPUT9 = {"invalid"};

    @Test
    void testIntegerConverter() {
        InputConverter<Integer> ic = new IntegerConverter();

        Assertions.assertThrows(IllegalStateException.class, () -> ic.convert(INPUT0));
        Assertions.assertThrows(IllegalStateException.class, () -> ic.convert(INPUT1));

        ConversionException ce2 = Assertions.assertThrows(ConversionException.class, () -> ic.convert(INPUT2));
        assertEquals("Invalid java.lang.Integer: ", ce2.getDisplayMessage());

        ConversionException ce3 = Assertions.assertThrows(ConversionException.class, () -> ic.convert(INPUT3));
        assertEquals("Invalid java.lang.Integer:  ", ce3.getDisplayMessage());

        ConversionException ce4 = Assertions.assertThrows(ConversionException.class, () -> ic.convert(INPUT4));
        assertEquals("Invalid java.lang.Integer:  3 ", ce4.getDisplayMessage());

        assertEquals(1000, ic.convert(INPUT5));
        assertEquals(-1000, ic.convert(INPUT6));
        assertEquals(0, ic.convert(INPUT7));

        ConversionException ce8 = Assertions.assertThrows(ConversionException.class, () -> ic.convert(INPUT8));
        assertEquals("Invalid java.lang.Integer: 99999999999999999999999999", ce8.getDisplayMessage());

        ConversionException ce9 = Assertions.assertThrows(ConversionException.class, () -> ic.convert(INPUT9));
        assertEquals("Invalid java.lang.Integer: invalid", ce9.getDisplayMessage());
    }
}
