package me.ronygomes.anoread.util;

import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.ReadHandlerTypeTestModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static me.ronygomes.anoread.util.AnnotationHelper.extractReadHandler;
import static org.junit.jupiter.api.Assertions.*;

public class AnnotationHelperTest {

    @Test
    void testFieldWithoutHandler() throws NoSuchFieldException {
        Field field0 = ReadHandlerTypeTestModel.class.getDeclaredField("field0");

        ReadHandler rh = extractReadHandler(field0.getDeclaredAnnotations());
        assertNull(rh);
    }

    @Test
    void testFieldWithSingleLineHandler() throws NoSuchFieldException {
        Field field1 = ReadHandlerTypeTestModel.class.getDeclaredField("field1");

        ReadHandler rh = extractReadHandler(field1.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof SingleLineReadHandler);
    }

    @Test
    void testFieldWithMultiLineHandler() throws NoSuchFieldException {
        Field field2 = ReadHandlerTypeTestModel.class.getDeclaredField("field2");

        ReadHandler rh = extractReadHandler(field2.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof MultiLineReadHandler);
    }

    @Test
    void testFieldWithFixedLineHandler() throws NoSuchFieldException {
        Field field3 = ReadHandlerTypeTestModel.class.getDeclaredField("field3");

        ReadHandler rh = extractReadHandler(field3.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof FixedLineReadHandler);
    }

    @Test
    void testFieldWithMixedSingleLineHandler() throws NoSuchFieldException {
        Field field4 = ReadHandlerTypeTestModel.class.getDeclaredField("field4");

        ReadHandler rh = extractReadHandler(field4.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof SingleLineReadHandler);
    }

    @Test
    void testFieldWithMultipleSingleLineHandler() throws NoSuchFieldException {
        Field field5 = ReadHandlerTypeTestModel.class.getDeclaredField("field5");

        ReadHandler rh = extractReadHandler(field5.getDeclaredAnnotations());
        assertNotNull(rh);

        // Although it will always return `SingleLineReadHandler'
        // Adding both as annotation order is undocumented jdk feature
        assertTrue(rh instanceof SingleLineReadHandler || rh instanceof MultiLineReadHandler);
    }
}
