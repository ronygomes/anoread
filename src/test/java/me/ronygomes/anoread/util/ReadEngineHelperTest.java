package me.ronygomes.anoread.util;

import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.impl.DelimiterSeparatedInputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.handler.impl.FixedLineReadHandler;
import me.ronygomes.anoread.handler.impl.MultiLineReadHandler;
import me.ronygomes.anoread.model.*;
import me.ronygomes.anoread.processor.DefaultEngineComponentProvider;
import me.ronygomes.anoread.processor.EngineComponentProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static java.util.Arrays.asList;
import static me.ronygomes.anoread.util.ReadEngineHelper.createAnnotatedReadTask;
import static me.ronygomes.anoread.util.ReadEngineHelper.make;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReadEngineHelperTest {

    @Test
    void testCreateAnnotatedReadTask() throws NoSuchFieldException, NoSuchMethodException {

        Person p = new Person();
        Field field = Person.class.getDeclaredField("name");
        ReadLifeCycleHookHolder holder = new ReadLifeCycleHookHolder(
                (a, b, c, d) -> false,
                (a, b, c, d) -> {
                },
                (a, b) -> {
                }
        );
        ReadMeta meta = new ReadMeta("1", "2", "3");

        Method method1 = Person.class.getDeclaredMethod("method1", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method2 = Person.class.getDeclaredMethod("method2", InputStream.class, PrintStream.class,
                PrintStream.class, ReadMeta.class);

        Method method3 = Person.class.getDeclaredMethod("method3", Object.class, ReadMeta.class);

        EngineComponentProvider cp = mock(EngineComponentProvider.class);

        AnnotatedReadTask<?> task = (AnnotatedReadTask<?>) createAnnotatedReadTask(p, field, holder, meta,
                method1, method2, method3, cp);

        assertSame(p, task.getTarget());
        assertSame(meta, task.getMeta());
        assertSame(holder.getReadEachPre(), task.getBefore());
        assertSame(holder.getReadEachPost(), task.getAfter());
        assertSame(holder.getValidator(), task.getValidator());
        assertSame(method1, task.getReadEachPre());
        assertSame(method2, task.getReadEachPost());
        assertSame(method3, task.getValidationMethod());

        verify(cp).updateTask(p, field, task);
    }

    @Test
    void testMake() {
        Person p = new Person();

        ReadEngineCmd cmd = make(p, asList(Person.class.getDeclaredFields()),
                new ReadLifeCycleHookHolder(), DefaultEngineComponentProvider.getInstance());

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(o);

        ByteArrayOutputStream e = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(e);

        cmd.getBeginConsumer().accept(null, out, err);
        assertEquals("0-1", o.toString());
        assertEquals("0-2", e.toString());

        o.reset();
        e.reset();

        cmd.getEndConsumer().accept(null, out, err);
        assertEquals("0-1", o.toString());
        assertEquals("0-2", e.toString());

        o.reset();
        e.reset();

        AnnotatedReadTask<?> nameTask = (AnnotatedReadTask<?>) cmd.getTasks()
                .stream()
                .filter(t -> t.getMeta().getName().equals("name"))
                .findFirst().orElseThrow(IllegalStateException::new);

        assertNotNull(nameTask);
        assertSame(p, nameTask.getTarget());

        assertTrue(nameTask.getConverter() instanceof StringConverter);
        assertTrue(nameTask.getHandler() instanceof FixedLineReadHandler);
        assertTrue(nameTask.getExtractor() instanceof DelimiterSeparatedInputExtractor);

        assertEquals("method1", nameTask.getReadEachPre().getName());
        assertEquals("method2", nameTask.getReadEachPost().getName());
        assertEquals("method3", nameTask.getValidationMethod().getName());

        ReadMeta nameMeta = nameTask.getMeta();
        assertEquals("name", nameMeta.getName());
        assertEquals("Enter name", nameMeta.getPrompt());
        assertNull(nameMeta.getHint());

        nameTask.getBefore().apply(null, out, err, nameMeta);
        assertEquals("1-1", o.toString());
        assertEquals("1-2", e.toString());

        o.reset();
        e.reset();

        nameTask.getAfter().accept(null, out, err, nameMeta);
        assertEquals("2-1", o.toString());
        assertEquals("2-2", e.toString());

        o.reset();
        e.reset();

        nameTask.getValidator().accept(p, nameMeta);
        assertEquals("4", nameMeta.getHint());

        AnnotatedReadTask<?> ageTask = (AnnotatedReadTask<?>) cmd.getTasks()
                .stream()
                .filter(t -> t.getMeta().getName().equals("age"))
                .findFirst().orElseThrow(IllegalStateException::new);

        assertNotNull(ageTask);
        assertSame(p, ageTask.getTarget());

        assertTrue(ageTask.getConverter() instanceof IntegerConverter);
        assertTrue(ageTask.getHandler() instanceof MultiLineReadHandler);
        assertTrue(ageTask.getExtractor() instanceof SingleInputExtractor);

        assertEquals("method1", ageTask.getReadEachPre().getName());
        assertEquals("method2", ageTask.getReadEachPost().getName());
        assertEquals("method3", ageTask.getValidationMethod().getName());

        ReadMeta ageMeta = ageTask.getMeta();
        assertEquals("age", ageMeta.getName());
        assertEquals("Yap", ageMeta.getPrompt());
        assertEquals("dummy", ageMeta.getHint());

        ageTask.getBefore().apply(null, out, err, ageMeta);
        assertEquals("1-1", o.toString());
        assertEquals("1-2", e.toString());

        o.reset();
        e.reset();

        ageTask.getAfter().accept(null, out, err, ageMeta);
        assertEquals("2-1", o.toString());
        assertEquals("2-2", e.toString());

        o.reset();
        e.reset();

        ageTask.getValidator().accept(p, ageMeta);
        assertEquals("4", ageMeta.getHint());
    }
}
