package me.ronygomes.anoread;

import me.ronygomes.anoread.engine.ReadEngine;
import me.ronygomes.anoread.model.ReadEngineCmd;
import me.ronygomes.anoread.model.ReadLifeCycleHookHolder;
import me.ronygomes.anoread.processor.DefaultHookProvider;
import me.ronygomes.anoread.processor.HookProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.ronygomes.anoread.util.AnnotationHelper.getFields;
import static me.ronygomes.anoread.util.ReadEngineHelper.make;

public class Anoread {

    private static final ReadEngine DEFAULT_ENGINE = new ReadEngine();

    public static void read(ReadEngine engine, Object target,
                            ReadLifeCycleHookHolder holder,
                            HookProvider defaultHookProvider,
                            String... fieldNames) throws IOException {

        Objects.requireNonNull(target);
        Objects.requireNonNull(holder);

        List<Field> fields = getFields(target.getClass(), Arrays.asList(fieldNames));
        ReadEngineCmd engineCmd = make(target, fields, holder, defaultHookProvider);

        engine.execute(engineCmd);
    }

    public static void read(Object target, String... fields) throws IOException {
        read(DEFAULT_ENGINE, target, new ReadLifeCycleHookHolder(), DefaultHookProvider.getInstance(), fields);
    }
}
