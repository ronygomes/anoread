package me.ronygomes.anoread;

import me.ronygomes.anoread.engine.ReadEngine;
import me.ronygomes.anoread.model.ReadEngineCmd;
import me.ronygomes.anoread.model.ReadLifeCycleHookHolder;
import me.ronygomes.anoread.processor.DefaultEngineComponentProvider;
import me.ronygomes.anoread.processor.EngineComponentProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.ronygomes.anoread.util.AnnotationHelper.getFields;
import static me.ronygomes.anoread.util.AnnotationHelper.getOrderedReadFields;
import static me.ronygomes.anoread.util.ReadEngineHelper.make;

public class Anoread {

    private static final ReadEngine DEFAULT_ENGINE = new ReadEngine();

    public static void read(ReadEngine engine, Object target,
                            ReadLifeCycleHookHolder holder,
                            EngineComponentProvider defaultEngineComponentProvider,
                            String... fieldNames) {

        Objects.requireNonNull(target);
        Objects.requireNonNull(holder);

        List<Field> fields = getFields(target.getClass(), Arrays.asList(fieldNames));
        ReadEngineCmd engineCmd = make(target, fields, holder, defaultEngineComponentProvider);

        try {
            engine.execute(engineCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(Object target, String... fields) {
        read(DEFAULT_ENGINE, target, new ReadLifeCycleHookHolder(),
                DefaultEngineComponentProvider.getInstance(), fields);
    }

    public static <T> T readAndGet(ReadEngine engine, Class<T> clazz, ReadLifeCycleHookHolder holder,
                                   EngineComponentProvider defaultEngineComponentProvider) {

        try {
            T target = clazz.newInstance();

            List<Field> fields = getOrderedReadFields(clazz.getDeclaredFields());
            ReadEngineCmd engineCmd = make(target, fields, holder, defaultEngineComponentProvider);

            engine.execute(engineCmd);

            return target;

        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Unable to read class of type: " + clazz.getTypeName());
    }

    public static <T> T readAndGet(Class<T> clazz) {
        return readAndGet(DEFAULT_ENGINE, clazz, new ReadLifeCycleHookHolder(),
                DefaultEngineComponentProvider.getInstance());
    }
}
