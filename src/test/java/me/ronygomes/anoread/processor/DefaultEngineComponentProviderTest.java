package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import me.ronygomes.anoread.model.test.Person;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static me.ronygomes.anoread.processor.DefaultEngineComponentProvider.getInstance;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultEngineComponentProviderTest {

    @Test
    void testDefaultHookProviderIsSingleton() {
        EngineComponentProvider i1 = getInstance();
        EngineComponentProvider i2 = getInstance();

        assertSame(i1, i2);
    }

    @Test
    void testDefaultConverterAreSingleton() {
        EngineComponentProvider i1 = getInstance();

        InputConverter<?> c1 = i1.getConverter(Integer.class);
        InputConverter<?> c2 = i1.getConverter(Integer.class);

        assertSame(c1, c2);
    }

    @Test
    void testDefaultConverters() {
        EngineComponentProvider i1 = getInstance();

        assertTrue(i1.getConverter(Integer.class) instanceof IntegerConverter);
        assertTrue(i1.getConverter(String.class) instanceof StringConverter);
    }

    @Test
    void testReturnsSingleInputExtractorIfNothingMatched() {
        EngineComponentProvider i1 = getInstance();
        assertTrue(i1.getExtractor(DefaultEngineComponentProviderTest.class) instanceof SingleInputExtractor);
    }

    @Test
    void testDefaultHandlerIsSingleLineHandler() {
        assertTrue(getInstance().getHandler() instanceof SingleLineReadHandler);
    }

    @Test
    void testDefaultReadFormatterIsBasicReadPromptFormatter() {
        assertTrue(getInstance().getReadPromptFormatter() instanceof BasicReadPromptFormatter);
    }

    @Test
    void testDefaultErrorFormatterIsBasicErrorPromptFormatter() {
        assertTrue(getInstance().getErrorPromptFormatter() instanceof BasicErrorPromptFormatter);
    }

    @Test
    void testDefaultAssigner() throws NoSuchFieldException {
        Person p = new Person();

        Field name = Person.class.getDeclaredField("name");
        Field age = Person.class.getDeclaredField("age");

        EngineComponentProvider i1 = getInstance();

        Consumer<Object> nameAssigner = i1.getAssigner(name, p);
        nameAssigner.accept("John");

        Consumer<Object> ageAssigner = i1.getAssigner(age, p);
        ageAssigner.accept(27);

        assertEquals("John", p.getName());
        assertEquals(27, p.getAge());
    }
}
