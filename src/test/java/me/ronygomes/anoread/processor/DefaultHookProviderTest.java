package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.converter.impl.IntegerConverter;
import me.ronygomes.anoread.converter.impl.StringConverter;
import me.ronygomes.anoread.extractor.impl.SingleInputExtractor;
import me.ronygomes.anoread.formatter.impl.BasicErrorPromptFormatter;
import me.ronygomes.anoread.formatter.impl.BasicReadPromptFormatter;
import me.ronygomes.anoread.handler.impl.SingleLineReadHandler;
import org.junit.jupiter.api.Test;

import static me.ronygomes.anoread.processor.DefaultHookProvider.getInstance;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultHookProviderTest {

    @Test
    void testDefaultHookProviderIsSingleton() {
        HookProvider i1 = getInstance();
        HookProvider i2 = getInstance();

        assertSame(i1, i2);
    }

    @Test
    void testDefaultConverterAreSingleton() {
        HookProvider i1 = getInstance();

        InputConverter<?> c1 = i1.getConverter(Integer.class);
        InputConverter<?> c2 = i1.getConverter(Integer.class);

        assertSame(c1, c2);
    }

    @Test
    void testDefaultConverters() {
        HookProvider i1 = getInstance();

        assertTrue(i1.getConverter(Integer.class) instanceof IntegerConverter);
        assertTrue(i1.getConverter(String.class) instanceof StringConverter);
    }

    @Test
    void testReturnsSingleInputExtractorIfNothingMatched() {
        HookProvider i1 = getInstance();
        assertTrue(i1.getExtractor(DefaultHookProviderTest.class) instanceof SingleInputExtractor);
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
}
