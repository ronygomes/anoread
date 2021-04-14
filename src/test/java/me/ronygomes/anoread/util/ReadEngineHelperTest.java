package me.ronygomes.anoread.util;

import me.ronygomes.anoread.model.ReadLifeCycleHookHolder;
import me.ronygomes.anoread.model.ReadMeta;
import me.ronygomes.anoread.model.ReadTask;
import me.ronygomes.anoread.processor.EngineComponentProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static me.ronygomes.anoread.util.ReadEngineHelper.createAnnotatedReadTask;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ReadEngineHelperTest {

    @Test
    @Disabled
    void testCreateAnnotatedReadTask() {

        Object target = mock(Object.class);
        Field filed = mock(Field.class);
        ReadLifeCycleHookHolder holder = mock(ReadLifeCycleHookHolder.class);

        ReadMeta meta = mock(ReadMeta.class);
        Method readEachPre = mock(Method.class);
        Method readEachPost = mock(Method.class);
        Method validator = mock(Method.class);

        EngineComponentProvider cp = mock(EngineComponentProvider.class);

        ReadTask<?> task = createAnnotatedReadTask(target, filed, holder,
                meta, readEachPre, readEachPost, validator, cp);

        Assertions.assertSame(meta, task.getMeta());
    }
}
