package me.ronygomes.anoread.util;

import me.ronygomes.anoread.model.*;
import me.ronygomes.anoread.processor.EngineComponentProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static me.ronygomes.anoread.util.AnnotationHelper.*;

public class ReadEngineHelper {

    public static ReadEngineCmd make(Object target, List<Field> fields,
                                     ReadLifeCycleHookHolder holder,
                                     EngineComponentProvider parentEngineComponentProvider) {

        Method[] methods = target.getClass().getDeclaredMethods();
        ReadEngineCmd cmd = new AnnotatedReadEngineCmd(target, holder,
                extractReadBegin(methods), extractReadEnd(methods));

        EngineComponentProvider classLevelEngineComponentProvider =
                createClassEngineComponentProvider(parentEngineComponentProvider, target.getClass().getAnnotations());

        List<ReadTask<?>> tasks = new ArrayList<>();

        for (Field field : fields) {
            tasks.add(createAnnotatedReadTask(target, field, holder, classLevelEngineComponentProvider));
        }

        cmd.setTasks(tasks);
        return cmd;
    }

    public static ReadTask<?> createAnnotatedReadTask(Object target, Field field,
                                                      ReadLifeCycleHookHolder holder,
                                                      EngineComponentProvider classLevelEngineComponentProvider) {

        Method[] methods = target.getClass().getDeclaredMethods();

        ReadTask<?> task = new AnnotatedReadTask<>(target, holder, extractReadEachPre(methods),
                extractReadEachPost(methods), extractValidator(methods));
        task.setMeta(createMeta(field));

        EngineComponentProvider fieldEngineComponentProvider =
                createFieldEngineComponentProvider(classLevelEngineComponentProvider, field.getDeclaredAnnotations());

        fieldEngineComponentProvider.updateTask(target, field, task);
        return task;
    }
}
