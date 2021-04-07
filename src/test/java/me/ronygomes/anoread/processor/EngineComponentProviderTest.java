package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.Person;
import me.ronygomes.anoread.model.ReadTask;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EngineComponentProviderTest {


    @Test
    void testUpdateTaskCopiesCorrectly() throws NoSuchFieldException {
        ReadTask<?> task = new ReadTask<>();
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
