package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.ReadTask;
import me.ronygomes.anoread.model.test.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EngineComponentProviderTest {

    @Mock
    private ReadHandler handler;

    @Mock
    private ReadPromptFormatter readPromptFormatter;

    @Mock
    private ErrorPromptFormatter errorPromptFormatter;

    @Mock
    private InputConverter<?> converter;

    @Mock
    private InputExtractor extractor;

    @Mock
    private Consumer<Object> assigner;

    @Test
    void testUpdateTaskUpdateReadTaskWithProviderData() throws NoSuchFieldException {
        EngineComponentProvider p = spy(EngineComponentProvider.class);
        ReadTask task = mock(ReadTask.class);

        when(p.getHandler()).thenReturn(handler);
        when(p.getReadPromptFormatter()).thenReturn(readPromptFormatter);
        when(p.getErrorPromptFormatter()).thenReturn(errorPromptFormatter);
        doReturn(converter).when(p).getConverter(any());
        when(p.getExtractor(any())).thenReturn(extractor);
        when(p.getAssigner(any(), any())).thenReturn(assigner);

        p.updateTask(null, Person.class.getDeclaredField("name"), task);

        verify(task, times(1)).setHandler(handler);
        verify(task, times(1)).setReadPromptFormatter(readPromptFormatter);
        verify(task, times(1)).setErrorPromptFormatter(errorPromptFormatter);
        verify(task, times(1)).setConverter(converter);
        verify(task, times(1)).setExtractor(extractor);
        verify(task, times(1)).setAssigner(assigner);

    }

    @Test
    void testUpdateTaskCopiesCorrectly() throws NoSuchFieldException {
        ReadTask task = new ReadTask();
        EngineComponentProvider p = DefaultEngineComponentProvider.getInstance();

        Person o = new Person();
        Field name = Person.class.getDeclaredField("name");
        p.updateTask(o, name, task);

        assertTrue(task.getHandler() instanceof SingleLineReadHandler);
        assertTrue(task.getReadPromptFormatter() instanceof BasicReadPromptFormatter);
        assertTrue(task.getErrorPromptFormatter() instanceof BasicErrorPromptFormatter);
        assertTrue(task.getConverter() instanceof StringConverter);
        assertTrue(task.getExtractor() instanceof SingleInputExtractor);
        assertNotNull(task.getAssigner());
    }
}
