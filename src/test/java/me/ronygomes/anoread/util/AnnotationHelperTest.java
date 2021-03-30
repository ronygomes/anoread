package me.ronygomes.anoread.util;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.AnnotationTestModel;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static me.ronygomes.anoread.util.AnnotationHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class AnnotationHelperTest {

    @Test
    void testFieldWithoutHandler() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");

        ReadHandler rh = extractReadHandler(field0.getDeclaredAnnotations());
        assertNull(rh);
    }

    @Test
    void testFieldWithSingleLineHandler() throws NoSuchFieldException {
        Field field1 = AnnotationTestModel.class.getDeclaredField("field1");

        ReadHandler rh = extractReadHandler(field1.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof SingleLineReadHandler);
    }

    @Test
    void testFieldWithMultiLineHandler() throws NoSuchFieldException {
        Field field2 = AnnotationTestModel.class.getDeclaredField("field2");

        ReadHandler rh = extractReadHandler(field2.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof MultiLineReadHandler);
    }

    @Test
    void testFieldWithFixedLineHandler() throws NoSuchFieldException {
        Field field3 = AnnotationTestModel.class.getDeclaredField("field3");

        ReadHandler rh = extractReadHandler(field3.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof FixedLineReadHandler);
    }

    @Test
    void testFieldWithMixedSingleLineHandler() throws NoSuchFieldException {
        Field field4 = AnnotationTestModel.class.getDeclaredField("field4");

        ReadHandler rh = extractReadHandler(field4.getDeclaredAnnotations());
        assertNotNull(rh);
        assertTrue(rh instanceof SingleLineReadHandler);
    }

    @Test
    void testFieldWithMultipleSingleLineHandler() throws NoSuchFieldException {
        Field field5 = AnnotationTestModel.class.getDeclaredField("field5");

        ReadHandler rh = extractReadHandler(field5.getDeclaredAnnotations());
        assertNotNull(rh);

        // Although it will always return `SingleLineReadHandler'
        // Adding both as annotation order is undocumented jdk feature
        assertTrue(rh instanceof SingleLineReadHandler || rh instanceof MultiLineReadHandler);
    }

    @Test
    void testFieldWithoutExtractor() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");

        InputExtractor ie = extractInputExtractor(field0.getDeclaredAnnotations());
        assertNull(ie);
    }

    @Test
    void testFieldWithSingleInputExtractor() throws NoSuchFieldException {
        Field field1 = AnnotationTestModel.class.getDeclaredField("field1");

        InputExtractor ie = extractInputExtractor(field1.getDeclaredAnnotations());
        assertNotNull(ie);
        assertTrue(ie instanceof SingleInputExtractor);
    }

    @Test
    void testFieldWithDelimiterSeparatedInputExtractor() throws NoSuchFieldException {
        Field field2 = AnnotationTestModel.class.getDeclaredField("field2");

        InputExtractor ie = extractInputExtractor(field2.getDeclaredAnnotations());
        assertNotNull(ie);
        assertTrue(ie instanceof DelimiterSeparatedInputExtractor);
    }

    @Test
    void testFieldWithConverter() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");

        InputConverter<?> c = extractConverter(field0.getDeclaredAnnotations());
        assertNotNull(c);
        assertTrue(c instanceof IntegerConverter);
    }
}
