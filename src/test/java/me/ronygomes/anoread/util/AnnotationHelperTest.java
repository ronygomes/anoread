package me.ronygomes.anoread.util;

import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.ReadHandlerTypeTestModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static me.ronygomes.anoread.util.AnnotationHelper.extractReadHandler;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotationHelperTest {

    @Test
    void testFieldWithoutHandler() throws NoSuchFieldException {
        Field field0 = ReadHandlerTypeTestModel.class.getDeclaredField("field0");

        Optional<? extends ReadHandler> rh = extractReadHandler(field0.getDeclaredAnnotations());
        assertFalse(rh.isPresent());
    }

    @Test
    void testFieldWithSingleLineHandler() throws NoSuchFieldException {
        Field field1 = ReadHandlerTypeTestModel.class.getDeclaredField("field1");

        Optional<? extends ReadHandler> rh = extractReadHandler(field1.getDeclaredAnnotations());
        assertTrue(rh.isPresent());
        rh.ifPresent(h -> assertTrue(h instanceof SingleLineReadHandler));
    }

    @Test
    void testFieldWithMultiLineHandler() throws NoSuchFieldException {
        Field field2 = ReadHandlerTypeTestModel.class.getDeclaredField("field2");

        Optional<? extends ReadHandler> rh = extractReadHandler(field2.getDeclaredAnnotations());
        assertTrue(rh.isPresent());
        rh.ifPresent(h -> assertTrue(h instanceof MultiLineReadHandler));
    }

    @Test
    void testFieldWithFixedLineHandler() throws NoSuchFieldException {
        Field field3 = ReadHandlerTypeTestModel.class.getDeclaredField("field3");

        Optional<? extends ReadHandler> rh = extractReadHandler(field3.getDeclaredAnnotations());
        assertTrue(rh.isPresent());
        rh.ifPresent(h -> assertTrue(h instanceof FixedLineReadHandler));
    }

    @Test
    void testFieldWithMixedSingleLineHandler() throws NoSuchFieldException {
        Field field4 = ReadHandlerTypeTestModel.class.getDeclaredField("field4");

        Optional<? extends ReadHandler> rh = extractReadHandler(field4.getDeclaredAnnotations());
        assertTrue(rh.isPresent());
        rh.ifPresent(h -> assertTrue(h instanceof SingleLineReadHandler));
    }

    @Test
    void testFieldWithMultipleSingleLineHandler() throws NoSuchFieldException {
        Field field5 = ReadHandlerTypeTestModel.class.getDeclaredField("field5");

        Optional<? extends ReadHandler> rh = extractReadHandler(field5.getDeclaredAnnotations());
        assertTrue(rh.isPresent());

        // Although it will always return `SingleLineReadHandler'
        // Adding both as annotation order is undocumented jdk feature
        rh.ifPresent(h -> assertTrue(h instanceof SingleLineReadHandler
                || h instanceof MultiLineReadHandler));
    }
}
