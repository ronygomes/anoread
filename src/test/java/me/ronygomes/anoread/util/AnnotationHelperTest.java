package me.ronygomes.anoread.util;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.AnnotationTestModel;
import me.ronygomes.anoread.model.ReadFieldTest;
import me.ronygomes.anoread.model.ReadMeta;
import me.ronygomes.anoread.processor.HookProvider;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static me.ronygomes.anoread.util.AnnotationHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testReadMetaWhenAbsent() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");
        assertNull(createMeta(field0));
    }

    @Test
    void testReadMetaWhenPresent() throws NoSuchFieldException {
        Field field6 = AnnotationTestModel.class.getDeclaredField("field6");
        ReadMeta meta = createMeta(field6);

        assertNotNull(meta);
        assertEquals("field6", meta.getName());
        assertEquals("Enter field6: ", meta.getPrompt());
        assertEquals("eg. Dog, Cat", meta.getHint());
    }

    @Test
    void testFieldWithoutReadFormatter() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");
        ReadPromptFormatter formatter = extractReadPromptFormatter(field0.getDeclaredAnnotations());
        assertNull(formatter);
    }

    @Test
    void testFieldWithReadFormatter() throws NoSuchFieldException {
        Field field6 = AnnotationTestModel.class.getDeclaredField("field6");
        ReadPromptFormatter formatter = extractReadPromptFormatter(field6.getDeclaredAnnotations());
        assertNotNull(formatter);
        assertTrue(formatter instanceof BasicReadPromptFormatter);
    }

    @Test
    void testFieldWithoutErrorFormatter() throws NoSuchFieldException {
        Field field0 = AnnotationTestModel.class.getDeclaredField("field0");
        ErrorPromptFormatter formatter = extractErrorPromptFormatter(field0.getDeclaredAnnotations());
        assertNull(formatter);
    }

    @Test
    void testFieldWithErrorFormatter() throws NoSuchFieldException {
        Field field3 = AnnotationTestModel.class.getDeclaredField("field3");
        ErrorPromptFormatter formatter = extractErrorPromptFormatter(field3.getDeclaredAnnotations());
        assertNotNull(formatter);
        assertTrue(formatter instanceof BasicErrorPromptFormatter);
    }

    @Test
    void testGetOrderedReadFieldsWhenEmpty() {
        List<Field> fields = getOrderedReadFields(AnnotationTestModel.class.getDeclaredFields());
        assertEquals(0, fields.size());
    }

    @Test
    void testGetOrderedReadFieldsWithData() {
        List<Field> fields = getOrderedReadFields(ReadFieldTest.class.getDeclaredFields());
        assertEquals(3, fields.size());
        assertIterableEquals(asList("field4", "field1", "field2"),
                fields.stream().map(Field::getName).collect(toList()));
    }

    @Test
    void testGetFields() {
        ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(baosErr);

        PrintStream stdErr = System.err;
        System.setErr(err);

        List<Field> fields = getFields(ReadFieldTest.class, asList("field4", "field7", "field3"));

        assertEquals(2, fields.size());
        assertIterableEquals(asList("field4", "field3"),
                fields.stream().map(Field::getName).collect(toList()));
        assertTrue(baosErr.toString().startsWith("java.lang.NoSuchFieldException: field7"));

        System.setErr(stdErr);
    }

    @Test
    void testCreateClassHookProviderWithEmptyClass() {
        HookProvider hookProvider = mock(HookProvider.class);
        HookProvider c = createClassHookProvider(hookProvider, ReadFieldTest.class.getAnnotations());

        c.getHandler();
        verify(hookProvider, times(1)).getHandler();

        c.getReadPromptFormatter();
        verify(hookProvider, times(1)).getReadPromptFormatter();

        c.getErrorPromptFormatter();
        verify(hookProvider, times(1)).getErrorPromptFormatter();

        c.getAssigner(null, null);
        verify(hookProvider, times(1)).getAssigner(null, null);

        c.getConverter(Integer.class);
        verify(hookProvider, times(1)).getConverter(Integer.class);

        c.getExtractor(Integer.class);
        verify(hookProvider, times(1)).getExtractor(Integer.class);
    }

    @Test
    void testCreateClassHookProviderWithValue() {
        HookProvider hookProvider = mock(HookProvider.class);
        HookProvider c = createClassHookProvider(hookProvider, AnnotationTestModel.class.getAnnotations());

        assertTrue(c.getHandler() instanceof MultiLineReadHandler);
        verify(hookProvider, times(0)).getHandler();

        assertTrue(c.getReadPromptFormatter() instanceof BasicReadPromptFormatter);
        verify(hookProvider, times(0)).getReadPromptFormatter();

        assertTrue(c.getErrorPromptFormatter() instanceof BasicErrorPromptFormatter);
        verify(hookProvider, times(0)).getErrorPromptFormatter();

        c.getAssigner(null, null);
        verify(hookProvider, times(1)).getAssigner(null, null);

        c.getConverter(Integer.class);
        verify(hookProvider, times(1)).getConverter(Integer.class);

        c.getExtractor(Integer.class);
        verify(hookProvider, times(1)).getExtractor(Integer.class);
    }
}
