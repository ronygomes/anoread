package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotatedReadTaskTest {

    private String field0;

    private List<String> field1;

    private Map<String, Integer> field2;

    @Test
    void testGetPayloadReturnsExistingPayload() {
        AnnotatedReadTask task = new AnnotatedReadTask();
        ConversionPayload payload = new ConversionPayload();
        task.setPayload(payload);

        assertSame(payload, task.getPayload());
    }

    @Test
    void testGetPayloadReturnsCorrectDataForNotGenericData() throws NoSuchFieldException {
        AnnotatedReadTask task = new AnnotatedReadTask();
        task.setField(AnnotatedReadTaskTest.class.getDeclaredField("field0"));

        ConversionPayload payload = task.getPayload();

        assertSame(String.class, payload.getPropertyType());
        assertEquals(0, payload.getPropertySubTypes().length);
        assertEquals(0, payload.getExtras().size());
    }

    @Test
    void testGetPayloadReturnsCorrectDataForSingleGenericData() throws NoSuchFieldException {
        AnnotatedReadTask task = new AnnotatedReadTask();
        task.setField(AnnotatedReadTaskTest.class.getDeclaredField("field1"));

        ConversionPayload payload = task.getPayload();

        assertSame(List.class, payload.getPropertyType());
        assertEquals(1, payload.getPropertySubTypes().length);
        assertSame(String.class, payload.getPropertySubTypes()[0]);
        assertEquals(0, payload.getExtras().size());
    }

    @Test
    void testGetPayloadReturnsCorrectDataForMultiGenericData() throws NoSuchFieldException {
        AnnotatedReadTask task = new AnnotatedReadTask();
        task.setField(AnnotatedReadTaskTest.class.getDeclaredField("field2"));

        ConversionPayload payload = task.getPayload();

        assertSame(Map.class, payload.getPropertyType());
        assertEquals(2, payload.getPropertySubTypes().length);
        assertSame(String.class, payload.getPropertySubTypes()[0]);
        assertSame(Integer.class, payload.getPropertySubTypes()[1]);
        assertEquals(0, payload.getExtras().size());
    }

    @Test
    void testAnnotatedReadTaskReadsFromNonNullHolderWhenMethodsAreNull() {
        Object target = new Object();
        ReadMeta meta = new ReadMeta("1", "2", "3");

        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder(
                (a, b, c, d) -> false,
                (a, b, c, d) -> {
                },
                (a, b) -> {
                }
        );

        AnnotatedReadTask task = new AnnotatedReadTask(target, null, holder, meta, null, null, null);

        assertSame(target, task.getTarget());
        assertSame(meta, task.getMeta());
        assertSame(holder.getReadEachPre(), task.getBefore());
        assertSame(holder.getReadEachPost(), task.getAfter());
        assertSame(holder.getValidator(), task.getValidator());
        assertEquals(meta, task.getMeta());
    }

    @Test
    void testAnnotatedReadTaskReadsFromNonNullHolderWhenMethodsAreNonNull() throws NoSuchMethodException {
        Object target = new Object();
        ReadMeta meta = new ReadMeta("1", "2", "3");

        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder(
                (a, b, c, d) -> false,
                (a, b, c, d) -> {
                },
                (a, b) -> {
                }
        );

        Method method1 = AnnotatedReadTaskTest.class.getDeclaredMethod("method1", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method2 = AnnotatedReadTaskTest.class.getDeclaredMethod("method2", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method3 = AnnotatedReadTaskTest.class.getDeclaredMethod("method3", Object.class, ReadMeta.class);

        AnnotatedReadTask task = new AnnotatedReadTask(target, null, holder, meta, method1, method2, method3);

        assertSame(target, task.getTarget());
        assertSame(meta, task.getMeta());
        assertSame(holder.getReadEachPre(), task.getBefore());
        assertSame(holder.getReadEachPost(), task.getAfter());
        assertSame(holder.getValidator(), task.getValidator());
        assertEquals(meta, task.getMeta());
    }

    @Test
    void testAnnotatedReadTaskReadsFromMethodsWhenNullHolder() throws NoSuchMethodException {
        Object target = new AnnotatedReadTaskTest();
        ReadMeta meta = new ReadMeta("1", "2", "3");

        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder();

        Method method1 = AnnotatedReadTaskTest.class.getDeclaredMethod("method1", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method2 = AnnotatedReadTaskTest.class.getDeclaredMethod("method2", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method3 = AnnotatedReadTaskTest.class.getDeclaredMethod("method3", Object.class, ReadMeta.class);

        AnnotatedReadTask task = new AnnotatedReadTask(target, null, holder, meta, method1, method2, method3);

        QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> readEachPre = task.getBefore();
        QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> readEachPost = task.getAfter();
        BiConsumer<Object, ReadMeta> validator = task.getValidator();

        assertSame(target, task.getTarget());
        assertSame(meta, task.getMeta());
        assertNotSame(readEachPre, task.getBefore());
        assertNotSame(readEachPost, task.getAfter());
        assertNotSame(validator, task.getValidator());
        assertEquals(meta, task.getMeta());

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(o);

        ByteArrayOutputStream e = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(e);

        readEachPre.apply(null, out, err, meta);
        assertEquals("1-1", o.toString());
        assertEquals("1-2", e.toString());

        o.reset();
        e.reset();

        readEachPost.accept(null, out, err, meta);
        assertEquals("2-1", o.toString());
        assertEquals("2-2", e.toString());


        o.reset();
        e.reset();

        validator.accept(target, meta);
        assertEquals("", o.toString());
        assertEquals("", e.toString());
        assertEquals("4", meta.getHint());
    }

    private boolean method1(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        out.print("1-1");
        err.print("1-2");

        return false;
    }

    private void method2(InputStream in, PrintStream out, PrintStream err, ReadMeta meta) {
        out.print("2-1");
        err.print("2-2");
    }

    private void method3(Object object, ReadMeta meta) {
        meta.setHint("4");
    }
}
