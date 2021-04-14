package me.ronygomes.anoread.model;

import me.ronygomes.anoread.util.function.TriConsumer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotatedReadEngineCmdTest {

    @Test
    void testAnnotatedReadEngineCmdReadsFromNonNullHolderWhenMethodsAreNull() {
        Object target = new Object();
        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder(
                (in, out, err) -> {
                },
                (in, out, err) -> {
                }
        );

        AnnotatedReadEngineCmd cmd = new AnnotatedReadEngineCmd(target, holder, null, null);

        assertSame(target, cmd.getTarget());
        assertSame(holder.getReadBegin(), cmd.getBeginConsumer());
        assertSame(holder.getReadEnd(), cmd.getEndConsumer());
    }

    @Test
    void testAnnotatedReadEngineCmdReadsFromNonNullHolderWhenMethodsAreNotNull() throws NoSuchMethodException {
        Object target = new Object();
        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder(
                (in, out, err) -> {
                },
                (in, out, err) -> {
                }
        );

        Method method = AnnotatedReadEngineCmdTest.class.getDeclaredMethod("method1",
                InputStream.class, PrintStream.class, PrintStream.class);

        AnnotatedReadEngineCmd cmd = new AnnotatedReadEngineCmd(target, holder, method, method);

        assertSame(target, cmd.getTarget());
        assertSame(holder.getReadBegin(), cmd.getBeginConsumer());
        assertSame(holder.getReadEnd(), cmd.getEndConsumer());
    }

    @Test
    void testAnnotatedReadEngineCmdReadsFromMethosWhenHolderIsNull() throws NoSuchMethodException {
        Object target = new AnnotatedReadEngineCmdTest();
        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder();

        Method method = AnnotatedReadEngineCmdTest.class.getDeclaredMethod("method1",
                InputStream.class, PrintStream.class, PrintStream.class);

        AnnotatedReadEngineCmd cmd = new AnnotatedReadEngineCmd(target, holder, method, method);

        assertSame(target, cmd.getTarget());

        TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer = cmd.getBeginConsumer();
        TriConsumer<InputStream, PrintStream, PrintStream> endConsumer = cmd.getEndConsumer();

        assertNotSame(beginConsumer, cmd.getBeginConsumer());
        assertNotSame(endConsumer, cmd.getEndConsumer());

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(o);

        ByteArrayOutputStream e = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(e);

        beginConsumer.accept(null, out, err);
        assertEquals("1-1", o.toString());
        assertEquals("1-2", e.toString());

        o.reset();
        e.reset();

        endConsumer.accept(null, out, err);
        assertEquals("1-1", o.toString());
        assertEquals("1-2", e.toString());

    }

    private void method1(InputStream in, PrintStream out, PrintStream err) {
        out.print("1-1");
        err.print("1-2");
    }
}
