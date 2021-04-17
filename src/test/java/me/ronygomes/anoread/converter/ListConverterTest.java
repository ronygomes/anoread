package me.ronygomes.anoread.converter;

import me.ronygomes.anoread.converter.impl.ListConverter;
import me.ronygomes.anoread.model.ConversionPayload;
import me.ronygomes.anoread.model.test.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListConverterTest {

    private ListConverter converter = new ListConverter();
    private String[] INPUT1 = new String[]{"one", "two", "three"};
    private String[] INPUT2 = new String[]{"1", "2", "3"};

    @Test
    void throwsIllegalStateIfInvalidPayload() {
        Assertions.assertThrows(NullPointerException.class, () -> converter.convert(INPUT2, null));

        ConversionPayload payload = new ConversionPayload();
        payload.setPropertyType(List.class);
        Assertions.assertThrows(IllegalStateException.class, () -> converter.convert(INPUT2, payload));

        payload.setPropertySubTypes(new Class[]{Integer.class, String.class});
        Assertions.assertThrows(IllegalStateException.class, () -> converter.convert(INPUT2, payload));

        payload.setPropertySubTypes(new Class[]{Person.class});
        Assertions.assertThrows(IllegalStateException.class, () -> converter.convert(INPUT2, payload));

        payload.setPropertyType(Map.class);
        Assertions.assertThrows(IllegalStateException.class, () -> converter.convert(INPUT2, payload));
    }

    @Test
    void testCanConvertStringType() {
        ConversionPayload payload = new ConversionPayload();
        payload.setPropertyType(List.class);
        payload.setPropertySubTypes(new Class[]{String.class});

        List<String> data = (List<String>) converter.convert(INPUT1, payload);

        assertEquals(3, data.size());
        assertEquals(String.class, data.get(0).getClass());
        assertEquals("one;two;three", data.stream().collect(Collectors.joining(";")));
    }

    @Test
    void testCanConvertIntegerType() {
        ConversionPayload payload = new ConversionPayload();
        payload.setPropertyType(List.class);
        payload.setPropertySubTypes(new Class[]{Integer.class});

        List<Integer> data = (List<Integer>) converter.convert(INPUT2, payload);

        assertEquals(3, data.size());
        assertEquals(Integer.class, data.get(0).getClass());
        assertEquals(6, data.stream().mapToInt(Integer::intValue).sum());
    }
}
