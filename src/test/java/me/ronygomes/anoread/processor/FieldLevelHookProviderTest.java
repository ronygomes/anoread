package me.ronygomes.anoread.processor;

import me.ronygomes.anoread.converter.InputConverter;
import me.ronygomes.anoread.extractor.InputExtractor;
import me.ronygomes.anoread.formatter.ErrorPromptFormatter;
import me.ronygomes.anoread.formatter.ReadPromptFormatter;
import me.ronygomes.anoread.handler.ReadHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FieldLevelHookProviderTest {

    @Mock
    private HookProvider hookProvider;

    @Mock
    private ReadHandler readHandler;

    @Mock
    private InputConverter<?> inputConverter;

    @Mock
    private InputExtractor inputExtractor;

    @Mock
    private ReadPromptFormatter readPromptFormatter;

    @Mock
    private ErrorPromptFormatter errorPromptFormatter;

    @Test
    void testNullWillCallParentHooks() {
        HookProvider c = new FieldLevelHookProvider(hookProvider, null, null, null, null, null);

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
    void testNonNullHooksWillBeCalled() {
        HookProvider c = new FieldLevelHookProvider(hookProvider, readHandler,
                readPromptFormatter, inputExtractor, inputConverter, errorPromptFormatter);

        assertSame(readHandler, c.getHandler());
        verify(hookProvider, times(0)).getHandler();

        assertSame(readPromptFormatter, c.getReadPromptFormatter());
        verify(hookProvider, times(0)).getReadPromptFormatter();

        assertSame(errorPromptFormatter, c.getErrorPromptFormatter());
        verify(hookProvider, times(0)).getErrorPromptFormatter();

        c.getAssigner(null, null);
        verify(hookProvider, times(1)).getAssigner(null, null);

        assertSame(inputConverter, c.getConverter(Integer.class));
        verify(hookProvider, times(0)).getConverter(Integer.class);

        assertSame(inputExtractor, c.getExtractor(Integer.class));
        verify(hookProvider, times(0)).getExtractor(Integer.class);
    }
}