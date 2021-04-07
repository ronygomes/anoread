package me.ronygomes.anoread.processor;

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
public class ClassLevelEngineComponentProviderTest {

    @Mock
    private EngineComponentProvider engineComponentProvider;

    @Mock
    private ReadHandler readHandler;

    @Mock
    private ReadPromptFormatter readPromptFormatter;

    @Mock
    private ErrorPromptFormatter errorPromptFormatter;

    @Test
    void testNullWillCallParentHooks() {
        EngineComponentProvider c = new ClassLevelEngineComponentProvider(engineComponentProvider, null, null, null);

        c.getHandler();
        verify(engineComponentProvider, times(1)).getHandler();

        c.getReadPromptFormatter();
        verify(engineComponentProvider, times(1)).getReadPromptFormatter();

        c.getErrorPromptFormatter();
        verify(engineComponentProvider, times(1)).getErrorPromptFormatter();

        c.getAssigner(null, null);
        verify(engineComponentProvider, times(1)).getAssigner(null, null);

        c.getConverter(Integer.class);
        verify(engineComponentProvider, times(1)).getConverter(Integer.class);

        c.getExtractor(Integer.class);
        verify(engineComponentProvider, times(1)).getExtractor(Integer.class);
    }

    @Test
    void testNonNullHooksWillBeCalled() {
        EngineComponentProvider c = new ClassLevelEngineComponentProvider(engineComponentProvider, readHandler,
                readPromptFormatter, errorPromptFormatter);

        assertSame(readHandler, c.getHandler());
        verify(engineComponentProvider, times(0)).getHandler();

        assertSame(readPromptFormatter, c.getReadPromptFormatter());
        verify(engineComponentProvider, times(0)).getReadPromptFormatter();

        assertSame(errorPromptFormatter, c.getErrorPromptFormatter());
        verify(engineComponentProvider, times(0)).getErrorPromptFormatter();

        c.getAssigner(null, null);
        verify(engineComponentProvider, times(1)).getAssigner(null, null);

        c.getConverter(Integer.class);
        verify(engineComponentProvider, times(1)).getConverter(Integer.class);

        c.getExtractor(Integer.class);
        verify(engineComponentProvider, times(1)).getExtractor(Integer.class);
    }
}
