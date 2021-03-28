package me.ronygomes.anoread.engine;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.exception.AnoReadException;
import me.ronygomes.anoread.exception.ConversionException;
import me.ronygomes.anoread.exception.ExtractionException;
import me.ronygomes.anoread.exception.ValidationError;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.model.ReadEngineCmd;
import me.ronygomes.anoread.model.ReadMeta;
import me.ronygomes.anoread.model.ReadTask;
import me.ronygomes.anoread.util.function.QuadConsumer;
import me.ronygomes.anoread.util.function.QuadFunction;
import me.ronygomes.anoread.util.function.TriConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.math.BigInteger.TEN;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReadEngineTest {

    private static final String DUMMY_READ_PROMPT_TEXT = "input";
    private static final String DUMMY_ERROR_PROMPT_TEXT = "error";

    @Mock
    private ReadEngineCmd engineCmd;

    @Mock
    private ReadTask<?> readTask;

    @Mock
    private TriConsumer<InputStream, PrintStream, PrintStream> beginConsumer;

    @Mock
    private TriConsumer<InputStream, PrintStream, PrintStream> endConsumer;

    @Mock
    private QuadFunction<InputStream, PrintStream, PrintStream, ReadMeta, Boolean> before;

    @Mock
    private QuadConsumer<InputStream, PrintStream, PrintStream, ReadMeta> after;

    @Mock
    private ReadHandler handler;

    @Mock
    private ReadPromptFormatter readPromptFormatter;

    @Mock
    private InputExtractor extractor;

    @Mock
    private InputConverter<?> converter;

    @Mock
    private Consumer<Object> assigner;

    @Mock
    private ErrorPromptFormatter errorPromptFormatter;

    @Spy
    private List<ReadTask<?>> tasks;

    private ByteArrayInputStream in;

    private PrintStream out;

    private PrintStream err;

    private ByteArrayOutputStream baosOut;

    private ByteArrayOutputStream baosErr;

    @BeforeEach
    public void setup() {
        this.in = new ByteArrayInputStream(new byte[TEN.intValue()]);

        this.baosOut = new ByteArrayOutputStream();
        this.out = new PrintStream(this.baosOut);

        this.baosErr = new ByteArrayOutputStream();
        this.err = new PrintStream(this.baosErr);

        this.tasks = new ArrayList<>();
        tasks.add(readTask);

    }

    @Test
    public void testEmptyTasksWillDoNothing() throws IOException {
        when(engineCmd.getTasks()).thenReturn(Collections.emptyList());

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(0)).accept(in, out, err);
        verify(endConsumer, times(0)).accept(in, out, err);
    }

    @Test
    public void testEmptyConstructorWillUseSystemIO() throws IOException {
        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();
        when(before.apply(any(), any(), any(), any())).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine();
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(System.in, System.out, System.err);
        verify(before, times(1)).apply(System.in, System.out, System.err, null);

        verify(readPromptFormatter, times(1)).format(any());
        verify(handler, times(1)).read(System.in, System.out, System.err);
        verify(extractor, times(1)).extract(any());
        verify(converter, times(1)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(errorPromptFormatter, times(0)).format(any(), any(), any());

        verify(after, times(1)).accept(System.in, System.out, System.err, null);
        verify(endConsumer, times(1)).accept(System.in, System.out, System.err);
    }

    @Test
    public void testTwoArgsConstructorWillUseSystemOutAsErr() throws IOException {
        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();
        when(before.apply(any(), any(), any(), any())).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine(in, out);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, out);
        verify(before, times(1)).apply(in, out, out, null);

        verify(readPromptFormatter, times(1)).format(any());
        verify(handler, times(1)).read(in, out, out);
        verify(extractor, times(1)).extract(any());
        verify(converter, times(1)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(errorPromptFormatter, times(0)).format(any(), any(), any());

        verify(after, times(1)).accept(in, out, out, null);
        verify(endConsumer, times(1)).accept(in, out, out);
    }

    @Test
    public void testReadTaskBeforeHandlerCanSkipTask() throws IOException {

        stubEngineCmdConsumers();

        when(engineCmd.getTasks()).thenReturn(tasks);
        when(readTask.getBefore()).thenReturn(before);
        when(before.apply(in, out, err, null)).thenReturn(false);

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, err);
        verify(before, times(1)).apply(in, out, err, null);
        verify(endConsumer, times(1)).accept(in, out, err);
    }

    @Test
    public void testEngineWithSingleValidTask() throws IOException {

        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();
        when(before.apply(in, out, err, null)).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, err);
        verify(before, times(1)).apply(in, out, err, null);

        verify(readPromptFormatter, times(1)).format(any());
        verify(handler, times(1)).read(in, out, err);
        verify(extractor, times(1)).extract(any());
        verify(converter, times(1)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(errorPromptFormatter, times(0)).format(any(), any(), any());

        verify(after, times(1)).accept(in, out, err, null);
        verify(endConsumer, times(1)).accept(in, out, err);

        assertArrayEquals(DUMMY_READ_PROMPT_TEXT.getBytes(UTF_8), baosOut.toByteArray());
        assertArrayEquals(new byte[0], baosErr.toByteArray());
    }

    @Test
    public void testEngineWithValidationError() throws IOException {

        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();

        AnoReadException e = new ValidationError();
        doThrow(e).doNothing().when(readTask).validate(any());
        when(readTask.getErrorPromptFormatter()).thenReturn(errorPromptFormatter);

        when(before.apply(in, out, err, null)).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);
        when(errorPromptFormatter.format(any(), any(), any())).thenReturn(DUMMY_ERROR_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, err);
        verify(before, times(1)).apply(in, out, err, null);

        verify(readPromptFormatter, times(2)).format(any());
        verify(handler, times(2)).read(in, out, err);
        verify(extractor, times(2)).extract(any());
        verify(converter, times(2)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(readTask, times(2)).validate(any());
        verify(errorPromptFormatter, times(1)).format(any(), any(), eq(e));

        verify(after, times(1)).accept(in, out, err, null);
        verify(endConsumer, times(1)).accept(in, out, err);

        assertArrayEquals((DUMMY_READ_PROMPT_TEXT + DUMMY_READ_PROMPT_TEXT).getBytes(UTF_8), baosOut.toByteArray());
        assertArrayEquals(DUMMY_ERROR_PROMPT_TEXT.getBytes(UTF_8), baosErr.toByteArray());
    }

    @Test
    public void testEngineWithExtractorException() throws IOException {

        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();

        AnoReadException e = new ExtractionException();
        when(extractor.extract(any())).thenThrow(e).thenReturn(null);
        when(readTask.getErrorPromptFormatter()).thenReturn(errorPromptFormatter);

        when(before.apply(in, out, err, null)).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);
        when(errorPromptFormatter.format(any(), any(), any())).thenReturn(DUMMY_ERROR_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, err);
        verify(before, times(1)).apply(in, out, err, null);

        verify(readPromptFormatter, times(2)).format(any());
        verify(handler, times(2)).read(in, out, err);
        verify(extractor, times(2)).extract(any());
        verify(converter, times(1)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(readTask, times(1)).validate(any());
        verify(errorPromptFormatter, times(1)).format(any(), any(), any());

        verify(after, times(1)).accept(in, out, err, null);
        verify(endConsumer, times(1)).accept(in, out, err);

        assertArrayEquals((DUMMY_READ_PROMPT_TEXT + DUMMY_READ_PROMPT_TEXT).getBytes(UTF_8), baosOut.toByteArray());
        assertArrayEquals(DUMMY_ERROR_PROMPT_TEXT.getBytes(UTF_8), baosErr.toByteArray());
    }

    @Test
    public void testEngineWithConversionException() throws IOException {

        stubEngineCmdConsumers();
        when(engineCmd.getTasks()).thenReturn(tasks);

        stubReadTaskCommonMethods();

        AnoReadException e = new ConversionException();
        when(converter.convert(any())).thenThrow(e).thenReturn(null);
        when(readTask.getErrorPromptFormatter()).thenReturn(errorPromptFormatter);

        when(before.apply(in, out, err, null)).thenReturn(true);
        when(readPromptFormatter.format(any())).thenReturn(DUMMY_READ_PROMPT_TEXT);
        when(errorPromptFormatter.format(any(), any(), any())).thenReturn(DUMMY_ERROR_PROMPT_TEXT);

        ReadEngine engine = new ReadEngine(in, out, err);
        engine.execute(engineCmd);

        verify(beginConsumer, times(1)).accept(in, out, err);
        verify(before, times(1)).apply(in, out, err, null);

        verify(readPromptFormatter, times(2)).format(any());
        verify(handler, times(2)).read(in, out, err);
        verify(extractor, times(2)).extract(any());
        verify(converter, times(2)).convert(any());
        verify(assigner, times(1)).accept(any());
        verify(readTask, times(1)).validate(any());
        verify(errorPromptFormatter, times(1)).format(any(), any(), any());

        verify(after, times(1)).accept(in, out, err, null);
        verify(endConsumer, times(1)).accept(in, out, err);

        assertArrayEquals((DUMMY_READ_PROMPT_TEXT + DUMMY_READ_PROMPT_TEXT).getBytes(UTF_8), baosOut.toByteArray());
        assertArrayEquals(DUMMY_ERROR_PROMPT_TEXT.getBytes(UTF_8), baosErr.toByteArray());
    }

    private void stubEngineCmdConsumers() {
        when(engineCmd.getBeginConsumer()).thenReturn(beginConsumer);
        when(engineCmd.getEndConsumer()).thenReturn(endConsumer);
    }

    private void stubReadTaskCommonMethods() {
        when(readTask.getBefore()).thenReturn(before);
        when(readTask.getReadPromptFormatter()).thenReturn(readPromptFormatter);
        when(readTask.getHandler()).thenReturn(handler);
        when(readTask.getExtractor()).thenReturn(extractor);
        doReturn(this.converter).when(readTask).getConverter();
        when(readTask.getAssigner()).thenReturn(assigner);
        when(readTask.getAfter()).thenReturn(after);
    }
}
